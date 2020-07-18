package org.horx.wdf.sys.view;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.converter.DictItemVoConverter;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.service.DictService;
import org.horx.wdf.sys.vo.DictItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/user")
public class UserPageController {

    @Autowired
    private DictService dictService;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private DictItemVoConverter dictItemVoConverter;

    @AccessPermission("sys.user.query")
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", genDict());
        mav.setViewName("sys/user/list");
        return mav;
    }

    @AccessPermission("sys.user.create")
    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView();
        addModelAndViewData(mav);
        mav.addObject("dict", genDict());
        mav.setViewName("sys/user/edit");
        return mav;
    }

    @AccessPermission("sys.user.modify")
    @RequestMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        addModelAndViewData(mav);
        mav.addObject("id", id);
        mav.addObject("dict", genDict());
        mav.setViewName("sys/user/edit");
        return mav;
    }

    @AccessPermission("sys.user.modifyPwd")
    @RequestMapping("/modifyPwd/{id}")
    public ModelAndView modifyPwd(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", id);
        mav.setViewName("sys/user/modifyPwd");
        return mav;
    }

    @RequestMapping("/currInfo")
    public ModelAndView currInfo() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sys/user/currInfo");
        return mav;
    }

    @RequestMapping("/modifyCurrPwd")
    public ModelAndView modifyCurrPwd() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("sys/user/modifyCurrPwd");
        return mav;
    }

    private void addModelAndViewData(ModelAndView mav) {
        Long orgId = sysContextHolder.getUserOrgId();
        mav.addObject("orgId", orgId);
    }

    private String genDict() {
        List<DictItemDTO> list = dictService.queryEnabledItemList(SysConstants.DICT_USER_STATUS);
        List<DictItemVO> voList = dictItemVoConverter.toVoList(list);
        Map<String, List<DictItemVO>> dict = new HashMap<>() ;
        dict.put("userStatus", voList);
        return JsonUtils.toJson(dict);
    }
}
