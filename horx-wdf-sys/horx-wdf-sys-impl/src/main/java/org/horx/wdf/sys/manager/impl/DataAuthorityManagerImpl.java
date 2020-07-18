package org.horx.wdf.sys.manager.impl;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.enums.CheckedTypeEnum;
import org.horx.wdf.sys.domain.DataAuthority;
import org.horx.wdf.sys.domain.DataAuthorityDetail;
import org.horx.wdf.sys.manager.DataAuthorityManager;
import org.horx.wdf.sys.mapper.DataAuthorityDetailMapper;
import org.horx.wdf.sys.mapper.DataAuthorityMapper;
import org.horx.wdf.sys.support.datalog.DataLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据授权Manager实现。
 * @since 1.0
 */
@Component("dataAuthorityManager")
public class DataAuthorityManagerImpl implements DataAuthorityManager {

    @Autowired
    private DataAuthorityMapper dataAuthorityMapper;

    @Autowired
    private DataAuthorityDetailMapper dataAuthorityDetailMapper;


    @Override
    @Transactional(readOnly = true)
    public List<DataAuthority> queryRequestDataAuthority(Long userId, Long[] roleIds, Long dataPermissionId) {
        List<DataAuthority> list = dataAuthorityMapper.selectUserEnabledData(userId, roleIds, dataPermissionId);
        return list;
    }


    @Override
    @Transactional(readOnly = true)
    public List<DataAuthority> queryAdminRoleDataAuthority(Long[] roleIds, Long dataPermissionId) {
        List<DataAuthority> list = dataAuthorityMapper.selectAdminRoleEnabledData(roleIds, dataPermissionId);
        return list;
    }

    @Override
    public DataAuthority getById(Long id) {
        DataAuthority dataAuthority = dataAuthorityMapper.selectById(id);
        return dataAuthority;
    }


    @Override
    @Transactional(readOnly = true)
    public DataAuthority getByObj(Long dataPermissionId, String objType, Long objId) {
        DataAuthority dataAuthority = dataAuthorityMapper.selectByObj(dataPermissionId, objType, objId);
        if (dataAuthority != null && dataAuthority.getDetailCount() != null && dataAuthority.getDetailCount() > 0) {
            List<DataAuthorityDetail> detailList = queryDetailByAuthorityIds(new Long[]{dataAuthority.getId()});
            dataAuthority.setDetailList(detailList);
        }
        return dataAuthority;
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dataPermission.dataAuth}")
    public void create(DataAuthority dataAuthority) {
        List<DataAuthorityDetail> detailList = processDetail(dataAuthority.getDetailList());
        dataAuthority.setDetailCount(detailList.size());

        dataAuthorityMapper.insert(dataAuthority);

        for (DataAuthorityDetail detail : detailList) {
            detail.setAuthorityId(dataAuthority.getId());
            detail.setCreateTime(dataAuthority.getCreateTime());
            detail.setCreateUserId(dataAuthority.getCreateUserId());
            dataAuthorityDetailMapper.insert(detail);
        }
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dataPermission.dataAuth}")
    public void modify(DataAuthority dataAuthority) {
        List<DataAuthorityDetail> detailList = processDetail(dataAuthority.getDetailList());
        dataAuthority.setDetailCount(detailList.size());

        List<DataAuthorityDetail> oldDetailList = dataAuthorityDetailMapper.selectByAuthorityId(dataAuthority.getId());

        dataAuthorityMapper.update(dataAuthority);

        for (DataAuthorityDetail oldDetail : oldDetailList) {
            boolean exist = false;
            for (DataAuthorityDetail detail : detailList) {
                if (oldDetail.getAuthorityValue().equals(detail.getAuthorityValue()) &&
                        oldDetail.getCheckedType().equals(detail.getCheckedType())) {
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                dataAuthorityDetailMapper.logicalDelete(oldDetail.getId());
            }
        }


        for (DataAuthorityDetail detail : detailList) {
            boolean exist = false;
            for (DataAuthorityDetail oldDetail : oldDetailList) {
                if (oldDetail.getAuthorityValue().equals(detail.getAuthorityValue()) &&
                        oldDetail.getCheckedType().equals(detail.getCheckedType())) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                detail.setAuthorityId(dataAuthority.getId());
                detail.setCreateTime(dataAuthority.getModifyTime());
                detail.setCreateUserId(dataAuthority.getModifyUserId());
                dataAuthorityDetailMapper.insert(detail);;
            }
        }
    }

    @Override
    public List<DataAuthorityDetail> queryDetailByAuthorityIds(Long[] authorityIds) {
        return dataAuthorityDetailMapper.selectByAuthorityIds(authorityIds);
    }

    @Override
    public boolean validate(Long[] authorityIds, String value) {
        int count = dataAuthorityDetailMapper.countByAuthorityIds(authorityIds, value);
        return count > 0;
    }

    private List<DataAuthorityDetail> processDetail(List<DataAuthorityDetail> detailList) {
        if (detailList == null) {
            return new ArrayList<>(0);
        }

        List<DataAuthorityDetail> list = new ArrayList<>(detailList.size());
        for (DataAuthorityDetail detail : detailList) {
            if (StringUtils.isEmpty(detail.getAuthorityValue())) {
                continue;
            }
            if (detail.getCheckedType() == null) {
                detail.setCheckedType(CheckedTypeEnum.CHECKED.getCode());
            }

            boolean exist = false;
            for (DataAuthorityDetail item : list) {
                if (detail.getAuthorityValue().equals(item.getAuthorityValue())) {
                    if (item.getCheckedType() == CheckedTypeEnum.CHECKED.getCode() && detail.getCheckedType() == CheckedTypeEnum.CHECKED_ALL.getCode()) {
                        item.setCheckedType(CheckedTypeEnum.CHECKED_ALL.getCode());
                    }
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                list.add(detail);
            }
        }

        return list;
    }
}
