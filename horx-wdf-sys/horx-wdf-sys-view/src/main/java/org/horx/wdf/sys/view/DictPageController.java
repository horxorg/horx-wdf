package org.horx.wdf.sys.view;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.annotation.ArgDataAuth;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.converter.DictItemVoConverter;
import org.horx.wdf.sys.dto.DictDTO;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;
import org.horx.wdf.sys.dto.dataauth.DictItemAuthDTO;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.service.DictService;
import org.horx.wdf.sys.vo.DictItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/dict")
public class DictPageController {

    @Autowired
    private DictService dictService;

    @Autowired
    private DictItemVoConverter dictItemVoConverter;

    @AccessPermission("sys.dict.query")
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", genDict(null));
        mav.setViewName("sys/dict/list");
        return mav;
    }

    @AccessPermission("sys.dict.query")
    @RequestMapping("/tab")
    public ModelAndView createTab(@RequestParam String currTab, @RequestParam(required = false) Long dictId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("currTab", currTab);
        mav.addObject("dictId", dictId);
        mav.setViewName("sys/dict/tab");
        return mav;
    }

    @AccessPermission("sys.dict.create")
    @RequestMapping("/create")
    public ModelAndView create(@ArgDataAuth DictDataAuthDTO dictDataAuth) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", genDict(dictDataAuth));
        mav.setViewName("sys/dict/edit");
        return mav;
    }

    @AccessPermission("sys.dict.modify")
    @RequestMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, @ArgDataAuth DictDataAuthDTO dictDataAuth) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", id);
        mav.addObject("dict", genDict(dictDataAuth));
        mav.setViewName("sys/dict/edit");
        return mav;
    }

    @AccessPermission("sys.dict.query")
    @RequestMapping("/detail/{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", id);
        mav.addObject("dict", genDict(null));
        mav.setViewName("sys/dict/detail");
        return mav;
    }

    @AccessPermission("sys.dict.query")
    @RequestMapping("/{dictId}/item/list")
    public ModelAndView itemList(@PathVariable("dictId") Long dictId) {
        ModelAndView mav = new ModelAndView();
        DictDTO dict = dictService.getById(dictId);
        if (dict.getTreeData()) {
            mav.setViewName("sys/dict/itemTree");
        } else {
            mav.setViewName("sys/dict/itemList");
        }

        return mav;
    }

    @AccessPermission("sys.dict.item")
    @RequestMapping("/{dictId}/item/create")
    public ModelAndView createItem(@PathVariable("dictId") Long dictId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dictId", dictId);
        mav.addObject("parentId", request.getParameter("parentId"));
        mav.addObject("treeData", request.getParameter("treeData"));
        mav.setViewName("sys/dict/itemEdit");
        return mav;
    }

    @AccessPermission("sys.dict.item")
    @RequestMapping("/{dictId}/item/modify/{id}")
    public ModelAndView modifyItem(@PathVariable("dictId") Long dictId, @PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dictId", dictId);
        mav.addObject("id", id);
        mav.addObject("treeData", request.getParameter("treeData"));
        mav.setViewName("sys/dict/itemEdit");
        return mav;
    }

    private String genDict(DictDataAuthDTO dictDataAuth) {
        DictItemAuthDTO dictItemAuthDTO = (dictDataAuth != null) ? dictDataAuth.getBizTypeAuth() : null;
        if (dictItemAuthDTO == null) {
            dictItemAuthDTO = new DictItemAuthDTO();
            dictItemAuthDTO.setScope(DataValidationScopeEnum.ALL.getCode());
        }
        List<DictItemDTO> list = dictService.queryEnabledItemListAuthorized(SysConstants.DICT_BIZ_TYPE, dictItemAuthDTO);
        List<DictItemVO> voList = dictItemVoConverter.toVoList(list);
        Map<String, List<DictItemVO>> dict = new HashMap<>() ;
        dict.put("bizType", voList);
        return JsonUtils.toJson(dict);
    }
}
