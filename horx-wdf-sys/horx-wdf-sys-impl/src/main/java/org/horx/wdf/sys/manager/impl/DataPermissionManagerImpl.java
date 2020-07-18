package org.horx.wdf.sys.manager.impl;

import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.exception.ResultException;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.domain.DataPermissionDef;
import org.horx.wdf.sys.dto.query.DataPermissionQueryDTO;
import org.horx.wdf.sys.manager.DataPermissionManager;
import org.horx.wdf.sys.mapper.DataPermissionDefMapper;
import org.horx.wdf.sys.support.datalog.DataLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据权限Manager实现。
 * @since 1.0
 */
@Component("dataPermissionManager")
public class DataPermissionManagerImpl implements DataPermissionManager {

    @Autowired
    private DataPermissionDefMapper dataPermissionDefMapper;

    @Autowired
    private MsgTool msgTool;


    @Override
    @Cacheable(value = "sysCache", key = "'sys.dataperm.id.' + #id")
    public DataPermissionDef getById(Long id) {
        return dataPermissionDefMapper.selectById(id);
    }


    @Override
    @Cacheable(value = "sysCache", key = "'sys.dataperm.code.' + #code")
    public DataPermissionDef getByCode(String code) {
        return dataPermissionDefMapper.selectByCode(code);
    }


    @Override
    @Transactional(readOnly = true)
    public PagingResult<DataPermissionDef> pagingQuery(DataPermissionQueryDTO dataPermissionQuery,
                                                       PagingParam pagingParam) {
        return dataPermissionDefMapper.pagingSelect(dataPermissionQuery, new PagingRowBounds(pagingParam));
    }

    @Override
    public List<DataPermissionDef> queryForAuthorityObj(String authorityObjType, Long authorityObjId) {
        DataPermissionQueryDTO dataPermissionQuery = new DataPermissionQueryDTO();
        dataPermissionQuery.setEnabled(true);
        return dataPermissionDefMapper.select(dataPermissionQuery);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dataPermission}", paramIndex = {0})
    public void create(DataPermissionDef dataPermission) {
        DataPermissionDef codePerm = dataPermissionDefMapper.selectByCode(dataPermission.getCode());
        if (codePerm != null) {
            throw new ResultException(new Result(ErrorCodeEnum.A0430.getCode(),
                    msgTool.getMsg("sys.dataPermission.codeExists")));
        }
        dataPermissionDefMapper.insert(dataPermission);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dataPermission}", paramIndex = {0})
    public void modify(DataPermissionDef dataPermission) {
        DataPermissionDef codePerm = dataPermissionDefMapper.selectByCode(dataPermission.getCode());
        if (codePerm != null && !codePerm.getId().equals(dataPermission.getId())) {
            throw new ResultException(new Result(ErrorCodeEnum.A0430.getCode(),
                    msgTool.getMsg("sys.dataPermission.codeExists")));
        }
        dataPermissionDefMapper.update(dataPermission);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dataPermission}", paramIndex = {0})
    public void remove(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }

        for (Long id : ids) {
            dataPermissionDefMapper.logicalDelete(id);
        }
    }
}
