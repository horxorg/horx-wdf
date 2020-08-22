package org.horx.wdf.sys.rest;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.annotation.ArgDataAuth;
import org.horx.wdf.sys.converter.query.UserQueryVoConverter;
import org.horx.wdf.sys.converter.UserVoConverter;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.UserQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.UserWithAuthDTO;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.service.UserService;
import org.horx.wdf.sys.vo.UserVO;
import org.horx.wdf.sys.vo.query.UserQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户API控制器。
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sys/user")
public class  UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private UserVoConverter userVoConverter;

    @Autowired
    private UserQueryVoConverter userQueryVoConverter;

    @AccessPermission("sys.user.query")
    @PostMapping("/paginationQuery")
    public PaginationResult<UserVO> paginationQuery(@ArgEntity UserQueryVO query, PaginationParam paginationParam) {
        UserQueryDTO userQueryDTO = userQueryVoConverter.fromVo(query);
        PaginationQuery<UserQueryDTO> paginationQuery = new PaginationQuery<>(userQueryDTO, paginationParam);
        PaginationResult<UserDTO> paginationResult = userService.paginationQuery(paginationQuery);
        PaginationResult<UserVO> voPaginationResult = PaginationResult.copy(paginationResult);
        voPaginationResult.setData(userVoConverter.toVoList(paginationResult.getData()));
        return voPaginationResult;
    }

    @AccessPermission("sys.user.query")
    @GetMapping("/{id}")
    public Result<UserVO> getById(@PathVariable("id") Long id, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        UserDTO dto = userService.getByIdWithRoleAuthorized(id, sysDataAuth);
        UserVO vo = userVoConverter.toVo(dto);
        return new Result<>(vo);
    }

    @AccessPermission("sys.user.create")
    @PostMapping()
    public Result<Long> create(@ArgEntity(create = true) UserVO userVO, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        Result<Long> result = checkOrgId(userVO);
        if (result != null) {
            return result;
        }

        UserWithAuthDTO userWithAuthDTO = new UserWithAuthDTO();
        userWithAuthDTO.setUser(userVoConverter.fromVo(userVO));
        userWithAuthDTO.setSysDataAuth(sysDataAuth);

        Long id = userService.create(userWithAuthDTO);
        return new Result(id);
    }

    @AccessPermission("sys.user.modify")
    @PutMapping("/{id}")
    public Result modify(@ArgEntity(modify = true) UserVO userVO, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        Result<Long> result = checkOrgId(userVO);
        if (result != null) {
            return result;
        }

        UserWithAuthDTO userWithAuthDTO = new UserWithAuthDTO();
        userWithAuthDTO.setUser(userVoConverter.fromVo(userVO));
        userWithAuthDTO.setSysDataAuth(sysDataAuth);

        userService.modify(userWithAuthDTO);
        return new Result();
    }

    @AccessPermission("sys.user.modifyPwd")
    @PostMapping("/modifyPwd/{id}")
    public Result modifyPwd(@PathVariable("id") Long id, @RequestParam("password") String password, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        userService.modifyPwd(id, password, sysDataAuth);
        return new Result();
    }

    @AccessPermission("sys.user.remove")
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[]  ids, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        BatchWithSysAuthDTO batchWithAuthDTO = new BatchWithSysAuthDTO();
        batchWithAuthDTO.setIds(ids);
        batchWithAuthDTO.setSysDataAuth(sysDataAuth);
        userService.remove(batchWithAuthDTO);
        return new Result();
    }

    @AccessPermission("sys.user.unlock")
    @PostMapping("/unlock")
    public Result unlock(@RequestParam("id[]") Long[] ids, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        BatchWithSysAuthDTO batchWithAuthDTO = new BatchWithSysAuthDTO();
        batchWithAuthDTO.setIds(ids);
        batchWithAuthDTO.setSysDataAuth(sysDataAuth);
        userService.unlock(batchWithAuthDTO);
        return new Result();
    }

    @GetMapping("/getCurrUser")
    public Result<UserVO> getCurrUser() {
        Long id = sysContextHolder.getUserId();
        UserDTO userDTO = userService.getById(id);
        return new Result<>(userVoConverter.toVo(userDTO));
    }

    @PostMapping("/modifyCurrPwd")
    public Result modifyCurrPwd(@RequestParam("oldPwd") String oldPwd, @RequestParam("password") String password) {
        Long id = sysContextHolder.getUserId();
        userService.modifyPwdWithCheck(id, oldPwd, password);
        return new Result();
    }

    private Result<Long> checkOrgId(UserVO user) {
        Long orgId = sysContextHolder.getUserOrgId();
        if (orgId != null && user.getOrgId() == null) {
            return new Result<>(ErrorCodeEnum.A0430.getCode(), msgTool.getMsg("sys.user.org") + ":" + msgTool.getMsg("common.err.param.notEmpty", ""));
        }

        return null;
    }
}
