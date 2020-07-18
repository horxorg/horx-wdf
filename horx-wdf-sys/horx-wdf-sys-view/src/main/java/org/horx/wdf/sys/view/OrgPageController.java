package org.horx.wdf.sys.view;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.annotation.ArgDataAuth;
import org.horx.wdf.sys.converter.DictItemVoConverter;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.service.DictService;
import org.horx.wdf.sys.service.OrgService;
import org.horx.wdf.sys.vo.DictItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/org")
public class OrgPageController {
    @Autowired
    private OrgService orgService;

    @Autowired
    private DictService dictService;

    @Autowired
    private DictItemVoConverter dictItemVoConverter;

    @AccessPermission("sys.org.query")
    @RequestMapping("/list")
    public ModelAndView list(@ArgDataAuth SysDataAuthDTO sysDataAuth) {
        ModelAndView mav = new ModelAndView();

        if (sysDataAuth != null && sysDataAuth.getOrgAuth().getScope() != DataValidationScopeEnum.ALL.getCode()) {
            mav.addObject("parentIdRequired", true);
        } else {
            mav.addObject("parentIdRequired", false);
        }

        mav.addObject("dict", genDict());
        mav.setViewName("sys/org/list");
        return mav;
    }

    @AccessPermission("sys.org.create")
    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("parentId") Long parentId) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", genDict());
        mav.setViewName("sys/org/edit");

        String parentName = "";
        if (parentId != null) {
            OrgDTO org = orgService.getById(parentId);
            if (org == null) {
                throw new RuntimeException("父菜单不存在，parentId=" + parentId);
            }
            parentName = org.getName();
        }

        mav.addObject("parentId", parentId);
        mav.addObject("parentName", parentName);

        return mav;
    }

    @AccessPermission("sys.org.modify")
    @RequestMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", genDict());
        mav.addObject("id", id);
        mav.setViewName("sys/org/edit");
        return mav;
    }

    private String genDict() {
        List<DictItemDTO> list = dictService.queryEnabledItemList(SysConstants.DICT_ORG_TYPE);
        List<DictItemVO> voList = dictItemVoConverter.toVoList(list);
        Map<String, List<DictItemVO>> dict = new HashMap<>() ;
        dict.put("orgType", voList);
        return JsonUtils.toJson(dict);
    }
}
