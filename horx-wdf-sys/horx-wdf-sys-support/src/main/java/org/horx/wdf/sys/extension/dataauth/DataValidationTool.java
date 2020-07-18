package org.horx.wdf.sys.extension.dataauth;

import org.horx.common.utils.ReflectUtils;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.sys.annotation.DataPermission;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.enums.DataAuthorityObjTypeEnum;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.horx.wdf.sys.service.DataPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限验证工具。
 * @since 1.0
 */
@Component("dataValidationTool")
public class DataValidationTool {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataValidationTool.class);

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private DataPermissionService dataPermissionService;

    @Autowired
    private DataAuthorityService dataAuthorityService;

    @Autowired
    private EntityExtension entityExtension;

    @Autowired
    private DataAuthorityConfig dataAuthorityConfig;


    public <T> T genDataAuth(Class<T> dataAuthClass) {
        if (dataAuthClass == null) {
            return null;
        }

        T dataAuth = entityExtension.newEntity(dataAuthClass);
        setDataAuthValue(dataAuth);

        return dataAuth;
    }

    private void setDataAuthValue(Object dataAuth) {
        if (dataAuth == null) {
            return;
        }

        Class<?> clazz = dataAuth.getClass();
        Field[] beanFields = clazz.getDeclaredFields();
        Long userId = sysContextHolder.getUserId();
        Long[] roleIds = sysContextHolder.getRoleIdsByPermissionCode();
        for (Field field : beanFields) {
            if ((field.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }

            DataPermission anno = field.getAnnotation(DataPermission.class);
            if (anno == null) {
                continue;
            }

            Object value = genFieldValue(field, anno, userId, roleIds);
            if (value != null) {
                try {
                    ReflectUtils.setValue(dataAuth, field, value);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    public boolean validate(DataPermissionObj dataAuth, Object value) {
        if (dataAuth == null) {
            return true;
        }

        Assert.notNull(value, "value不能为null");

        DataValidator validator = dataAuthorityConfig.getValidatorByVO(dataAuth);
        return validator.validate(dataAuth, value);
    }

    private Object genFieldValue(Field field, DataPermission anno, Long userId, Long[] roleIds) {
        String dataPermissionCode = anno.value();
        DataPermissionDefDTO dataPermissionDef = dataPermissionService.getByCode(dataPermissionCode);
        if (dataPermissionDef == null) {
            LOGGER.warn("数据权限编码" + dataPermissionCode + "不存在");
            return null;
        }

        if (!dataPermissionDef.getEnabled()) {
            return null;
        }

        List<DataAuthorityDTO> list = dataAuthorityService.queryRequestDataAuthority(userId, roleIds,
                dataPermissionDef.getId());
        DataAuthorityDTO userAuthority = null;
        DataAuthorityDTO defaultAuthority = null;

        for (DataAuthorityDTO dataAuthority : list) {
            if (DataAuthorityObjTypeEnum.USER.getCode().equals(dataAuthority.getObjType())) {
                userAuthority = dataAuthority;
                if (defaultAuthority != null) {
                    break;
                }
            } else if (DataAuthorityObjTypeEnum.DEFAULT.getCode().equals(dataAuthority.getObjType())) {
                defaultAuthority = dataAuthority;
                if (userAuthority != null) {
                    break;
                }
            }
        }

        if (userAuthority != null) {
            list = new ArrayList<>(1);
            list.add(userAuthority);
        } else if (defaultAuthority != null && list.size() == 1) {
            list = new ArrayList<>(1);
            list.add(defaultAuthority);
        } else {
            if (userAuthority != null) {
                list.remove(userAuthority);
            }
            if (defaultAuthority != null) {
                list.remove(defaultAuthority);
            }
        }

        Object result = null;
        DataValidator converter = dataAuthorityConfig.getValidator(dataPermissionDef);
        if (converter != null) {
            result = converter.genDataAuth(dataPermissionDef, list);
        }

        return result;
    }


}
