package org.horx.wdf.sys.view;

import org.horx.common.utils.JsonUtils;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.converter.DictItemVoConverter;
import org.horx.wdf.sys.dto.DictItemDTO;
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
 * 数据权限定义页面控制器。
 * @since 1.0
 */
@Controller
@RequestMapping("/page/sys/dataPermission")
public class DataPermissionPageController {

    @Autowired
    private DictService dictService;

    @Autowired
    private DictItemVoConverter dictItemVoConverter;

    /**
     * 列表页面。
     * @return
     */
    @AccessPermission("sys.dataPermission.query")
    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", genDict());
        mav.setViewName("sys/dataPermission/list");
        return mav;
    }

    /**
     * 详情页面。
     * @param id 数据权限定义ID。
     * @return
     */
    @AccessPermission("sys.dataPermission.query")
    @RequestMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", genDict());
        mav.addObject("id", id);
        mav.setViewName("sys/dataPermission/detail");
        return mav;
    }

    /**
     * 新建页面。
     * @return
     */
    @AccessPermission("sys.dataPermission.create")
    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", genDict());
        mav.setViewName("sys/dataPermission/edit");
        return mav;
    }

    /**
     * 修改页面。
     * @param id
     * @return
     */
    @AccessPermission("sys.dataPermission.modify")
    @RequestMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("dict", genDict());
        mav.addObject("id", id);
        mav.setViewName("sys/dataPermission/edit");
        return mav;
    }

    /**
     * 生成所需的字典项。
     * @return 字典Json。
     */
    private String genDict() {
        List<DictItemDTO> list = dictService.queryEnabledItemList(SysConstants.DICT_DATA_PERMISSION_TYPE);
        List<DictItemVO> voList = dictItemVoConverter.toVoList(list);
        Map<String, List<DictItemVO>> dict = new HashMap<>() ;
        dict.put("dataPermissionType", voList);
        return JsonUtils.toJson(dict);
    }
}
