package org.horx.wdf.sys.manager.impl;

import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.exception.ResultException;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;
import org.horx.wdf.sys.domain.Dict;
import org.horx.wdf.sys.domain.DictItem;
import org.horx.wdf.sys.dto.query.DictItemQueryDTO;
import org.horx.wdf.sys.dto.query.DictQueryDTO;
import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;
import org.horx.wdf.sys.dto.dataauth.DictItemAuthDTO;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.exception.PermissionDeniedException;
import org.horx.wdf.sys.extension.dataauth.DataValidationTool;
import org.horx.wdf.common.extension.level.LevelCodeGenerator;
import org.horx.wdf.sys.manager.DictManager;
import org.horx.wdf.sys.mapper.DictItemMapper;
import org.horx.wdf.sys.mapper.DictMapper;
import org.horx.wdf.sys.support.datalog.DataLog;
import org.horx.wdf.sys.consts.SysConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典Manager实现。
 * @since 1.0
 */
@Component("dictManager")
public class DictManagerImpl implements DictManager {
    private static final Long ITEM_ROOT_ID = -1L;

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private DictItemMapper dictItemMapper;

    @Autowired
    private LevelCodeGenerator levelCodeGenerator;

    @Autowired
    private DataValidationTool dataValidationTool;

    @Autowired
    private MsgTool msgTool;


    @Override
    @Cacheable(value = "sysCache", key = "'sys.dict.id.' + #id")
    public Dict getById(Long id) {
        return dictMapper.selectById(id);
    }

    @Override
    public Dict getByIdAuthorized(Long id, DictDataAuthDTO dictDataAuth) {
        Dict dict = getById(id);
        if (dict != null) {
            validate(dict, dictDataAuth);
        }

        return dict;
    }

    @Override
    @Cacheable(value = "sysCache", key = "'sys.dict.code.' + #code")
    public Dict getByCode(String code) {
        return dictMapper.selectByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public PagingResult<Dict> pagingQuery(DictQueryDTO dictQuery, PagingParam pagingParam) {
        return dictMapper.pagingSelect(dictQuery, new PagingRowBounds(pagingParam));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dict}", paramIndex = {0})
    public void create(Dict dict, DictDataAuthDTO dictDataAuth) {
        validate(dict, dictDataAuth);

        Dict codeDict = dictMapper.selectByCode(dict.getCode());
        if (codeDict != null) {
            throw new ResultException(new Result(ErrorCodeEnum.A0430.getCode(),
                    msgTool.getMsg("sys.dict.codeExists")));
        }
        dictMapper.insert(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dict}", paramIndex = {0})
    public void modify(Dict dict, DictDataAuthDTO dictDataAuth) {
        validate(dict, dictDataAuth);

        Dict codeDict = dictMapper.selectByCode(dict.getCode());
        if (codeDict != null && !codeDict.getId().equals(dict.getId())) {
            throw new ResultException(new Result(ErrorCodeEnum.A0430.getCode(),
                    msgTool.getMsg("sys.dict.codeExists")));
        }

        if (codeDict != null && !dict.getBizType().equals(codeDict.getBizType())) {
            validate(codeDict, dictDataAuth);
        }

        dictMapper.update(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dict}", paramIndex = {0})
    public void remove(Long[] ids, DictDataAuthDTO dictDataAuth) {
        if (ids == null || ids.length == 0) {
            return;
        }

        for (Long id : ids) {
            Dict old = dictMapper.selectById(id);
            validate(old, dictDataAuth);

            dictMapper.logicalDelete(id);
        }
    }

    @Override
    public DictItem getItemById(Long id) {
        return dictItemMapper.selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PagingResult<DictItem> pagingQueryItem(DictItemQueryDTO dictItemQuery, PagingParam pagingParam) {

        return dictItemMapper.pagingSelect(dictItemQuery, new PagingRowBounds(pagingParam));
    }

    @Override
    public List<DictItem> queryItemForTree(DictItemQueryDTO dictItemQuery) {
        return dictItemMapper.selectForTree(dictItemQuery);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dict.item}")
    public void createItem(DictItem dictItem) {
        DictItem codeDictItem = dictItemMapper.selectByCode(dictItem.getDictId(), dictItem.getCode());
        if (codeDictItem != null) {
            throw new ResultException(new Result(ErrorCodeEnum.A0430.getCode(),
                    msgTool.getMsg("sys.dict.item.codeExists")));
        }

        Dict dict = dictMapper.selectById(dictItem.getDictId());
        boolean levelCodeAfterInsert = false;
        DictItem parentItem = null;
        if (dict.getTreeData()) {
            if (dictItem.getParentId() == null) {
                dictItem.setParentId(ITEM_ROOT_ID);
            }

            int levelNum = 1;
            if (!ITEM_ROOT_ID.equals(dictItem.getParentId())) {
                parentItem = dictItemMapper.selectById(dictItem.getParentId());
                if (parentItem == null) {
                    throw new RuntimeException("父节点" + dictItem.getParentId() + "不存在");
                }

                levelNum = parentItem.getLevelNum() + 1;
            }

            dictItem.setLevelNum(levelNum);
            levelCodeAfterInsert = levelCodeGenerator.generateAfterInsert(dict.getCode(), dictItem.getId());
            if (levelCodeAfterInsert) {
                dictItem.setLevelCode(levelCodeGenerator.getTempLevelCode(dict.getCode(), dictItem.getParentId(),
                        (parentItem == null) ? null : parentItem.getLevelCode()));
            } else {
                dictItem.setLevelCode(levelCodeGenerator.getLevelCode(dict.getCode(), dictItem.getParentId(),
                        (parentItem == null) ? null : parentItem.getLevelCode(), dictItem.getId()));
            }
        }

        dictItemMapper.insert(dictItem);

        if (levelCodeAfterInsert) {
            DictItem dictItemModify = new DictItem();
            dictItemModify.setId(dictItem.getId());
            dictItemModify.setLevelCode(levelCodeGenerator.getLevelCode(dict.getCode(), dictItem.getParentId(),
                    (parentItem == null) ? null : parentItem.getLevelCode(), dictItem.getId()));
            dictItemMapper.update(dictItemModify);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dict.item}")
    public void modifyItem(DictItem dictItem) {
        Long dictId = dictItem.getDictId();
        if (dictId == null) {
            DictItem idDictItem = dictItemMapper.selectById(dictItem.getId());
            dictId = idDictItem.getDictId();
        }
        DictItem codeDictItem = dictItemMapper.selectByCode(dictId, dictItem.getCode());
        if (codeDictItem != null && !codeDictItem.getId().equals(dictItem.getId())) {
            throw new ResultException(new Result(ErrorCodeEnum.A0430.getCode(),
                    msgTool.getMsg("sys.dict.item.codeExists")));
        }
        dictItemMapper.update(dictItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.dict.item}")
    public void removeItem(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }

        for (Long id : ids) {
            dictItemMapper.logicalDelete(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "sysCache", key = "'sys.dict.item.' + #dictCode")
    public List<DictItem> queryEnabledItemList(String dictCode) {
        return queryEnabledItemListAuthorized(dictCode, null);
    }

    @Override
    @Cacheable(value = "sysCache", key = "'sys.dict.item.id.' + #dictId")
    public List<DictItem> queryEnabledItemListByDictId(Long dictId) {
        List<DictItem> list = dictItemMapper.selectEnabledByDictId(dictId);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DictItem> queryEnabledItemListAuthorized(String dictCode, DictItemAuthDTO dictItemAuth) {
        if (dictItemAuth != null && dictItemAuth.getScope() == DataValidationScopeEnum.FORBIDDEN.getCode()) {
            return new ArrayList<>();
        }

        Dict dict = dictMapper.selectByCode(dictCode);
        List<DictItem> list = null;
        if (dict != null) {
            list = dictItemMapper.selectEnabledByDictIdWithAuth(dict.getId(), dictItemAuth);
        } else {
            list = new ArrayList<>(0);
        }
        return list;
    }

    private void validate(Dict dict, DictDataAuthDTO dictDataAuth) {
        boolean authorized = dataValidationTool.validate(dictDataAuth.getBizTypeAuth(), dict.getBizType());
        if (!authorized) {
            throw new PermissionDeniedException();
        }
    }
}
