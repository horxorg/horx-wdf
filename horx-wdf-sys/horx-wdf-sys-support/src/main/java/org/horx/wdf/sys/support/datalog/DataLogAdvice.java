package org.horx.wdf.sys.support.datalog;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.horx.common.utils.JsonUtils;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.tools.ServerEnvironment;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.enums.OperationTypeEnum;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.extension.datalog.DataLogHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据操作日志Advice。
 * @since 1.0
 */
public class DataLogAdvice {

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private MsgTool msgTool;

    @Autowired(required = false)
    private DataLogHandler dataLogHandler;

    @Autowired
    protected EntityExtension entityExtension;

    @Autowired
    private ServerEnvironment serverEnvironment;

    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        if (dataLogHandler == null) {
            return point.proceed();
        }
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        Method realMethod = point.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());

        DataLog dataLog = realMethod.getAnnotation(DataLog.class);
        if (dataLog == null) {
            return point.proceed();
        }

        Object[] args = point.getArgs();
        if (args == null || args.length == 0) {
            return point.proceed();
        }

        Date startTime = new Date();
        Object result = point.proceed();

        Object data = null;
        if (args.length == 1) {
            data = args[0];
        } else {
            Map<String, Object> map = new HashMap<>();
            Parameter[] parameters = realMethod.getParameters();
            int[] paramIndex = dataLog.paramIndex();
            if (paramIndex == null || paramIndex.length == 0) {
                for (int i = 0; i < args.length; i++) {
                    map.put(parameters[i].getName(), args[i]);
                }
            } else if (paramIndex.length == 1) {
                data = args[paramIndex[0]];
            } else {
                for (int i = 0; i < paramIndex.length; i++) {
                    if (i >= args.length) {
                        continue;
                    }
                    map.put(parameters[i].getName(), args[i]);
                }
            }
            if (!map.isEmpty()) {
                data = map;
            }
        }

        if (data == null) {
            return result;
        }

        DataOperationLogDTO dataOperationLogDTO = entityExtension.newEntity(DataOperationLogDTO.class);
        dataOperationLogDTO.setUserId(sysContextHolder.getUserId());
        dataOperationLogDTO.setOrgId(sysContextHolder.getUserOrgId());
        dataOperationLogDTO.setTraceId(sysContextHolder.getTraceId());
        dataOperationLogDTO.setStartTime(startTime);
        dataOperationLogDTO.setEndTime(new Date());
        dataOperationLogDTO.setDuration(dataOperationLogDTO.getEndTime().getTime() - dataOperationLogDTO.getStartTime().getTime());
        dataOperationLogDTO.setBizType(dataLog.bizType());

        String bizName = dataLog.bizName();
        if (StringUtils.isNotEmpty(bizName)) {
            dataOperationLogDTO.setBizName(msgTool.getPlaceholderMsg(bizName));
        }

        OperationTypeEnum operationType = parseOperationType(dataLog.operationType(), realMethod.getName());
        dataOperationLogDTO.setOperationType(operationType.toString());
        dataOperationLogDTO.setClassName(realMethod.getDeclaringClass().getName());
        dataOperationLogDTO.setMethodName(realMethod.getName());
        dataOperationLogDTO.setServerIp(serverEnvironment.getServerIp());

        dataOperationLogDTO.setData(JsonUtils.toJson(data));

        String desc = dataLog.desc();
        if (StringUtils.isNotEmpty(desc)) {
            dataOperationLogDTO.setDescription(msgTool.getPlaceholderMsg(desc));
        }

        processDataLog(dataOperationLogDTO);

        dataLogHandler.handle(dataOperationLogDTO);

        return result;
    }

    protected void processDataLog(DataOperationLogDTO dataOperationLogDTO) {

    }

    private OperationTypeEnum parseOperationType(OperationTypeEnum operationType, String methodName) {
        OperationTypeEnum result = operationType;
        if (operationType == OperationTypeEnum.OTHER) {
            methodName = methodName.toLowerCase();
            if (methodName.contains("create") || methodName.contains("new") || methodName.contains("insert")) {
                result = OperationTypeEnum.CREATE;
            } else if (methodName.contains("modify") || methodName.contains("update")) {
                result = OperationTypeEnum.MODIFY;
            } else if (methodName.contains("remove") || methodName.contains("del")) {
                result = OperationTypeEnum.LOGICAL_REMOVE;
            }
        }
        return result;
    }
}
