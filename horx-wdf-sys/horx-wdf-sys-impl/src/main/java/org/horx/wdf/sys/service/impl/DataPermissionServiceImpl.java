package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.converter.DataPermissionDefConverter;
import org.horx.wdf.sys.domain.DataPermissionDef;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.dto.query.DataPermissionQueryDTO;
import org.horx.wdf.sys.manager.DataPermissionManager;
import org.horx.wdf.sys.service.DataPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据权限Service实现。
 * @since 1.0
 */
@Service("dataPermissionService")
public class DataPermissionServiceImpl implements DataPermissionService {

    @Autowired
    private DataPermissionManager dataPermissionManager;

    @Autowired
    private DataPermissionDefConverter dataPermissionDefConverter;

    @Override
    public DataPermissionDefDTO getById(Long id) {
        DataPermissionDef dataPermissionDef = dataPermissionManager.getById(id);
        DataPermissionDefDTO  dataPermissionDefDTO = dataPermissionDefConverter.toDto(dataPermissionDef);
        return dataPermissionDefDTO;
    }

    @Override
    public DataPermissionDefDTO getByCode(String code) {
        DataPermissionDef dataPermissionDef = dataPermissionManager.getByCode(code);
        DataPermissionDefDTO  dataPermissionDefDTO = dataPermissionDefConverter.toDto(dataPermissionDef);
        return dataPermissionDefDTO;
    }

    @Override
    public PagingResult<DataPermissionDefDTO> pagingQuery(PagingQuery<DataPermissionQueryDTO> pagingQuery) {
        PagingResult<DataPermissionDef> pagingResult = dataPermissionManager.pagingQuery(pagingQuery.getQuery(), pagingQuery.getPagingParam());

        PagingResult<DataPermissionDefDTO> pagingResultDTO = PagingResult.copy(pagingResult);
        List<DataPermissionDefDTO> dtoList = dataPermissionDefConverter.toDtoList(pagingResult.getData());
        pagingResultDTO.setData(dtoList);
        return pagingResultDTO;
    }

    @Override
    public List<DataPermissionDefDTO> queryForAuthorityObj(String authorityObjType, Long authorityObjId) {
        List<DataPermissionDef> list = dataPermissionManager.queryForAuthorityObj(authorityObjType, authorityObjId);
        List<DataPermissionDefDTO> dtoList = dataPermissionDefConverter.toDtoList(list);
        return dtoList;
    }

    @Override
    public Long create(DataPermissionDefDTO dataPermissionDTO) {
        DataPermissionDef dataPermissionDef = dataPermissionDefConverter.fromDto(dataPermissionDTO);
        dataPermissionManager.create(dataPermissionDef);
        return dataPermissionDef.getId();
    }

    @Override
    public void modify(DataPermissionDefDTO dataPermissionDTO) {
        DataPermissionDef dataPermissionDef = dataPermissionDefConverter.fromDto(dataPermissionDTO);
        dataPermissionManager.modify(dataPermissionDef);
    }

    @Override
    public void remove(Long[] ids) {
        dataPermissionManager.remove(ids);
    }
}
