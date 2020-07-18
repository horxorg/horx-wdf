package org.horx.wdf.sys.rest;

import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.converter.AccessLogVoConverter;
import org.horx.wdf.sys.converter.query.AccessLogQueryVoConverter;
import org.horx.wdf.sys.vo.AccessLogVO;
import org.horx.wdf.sys.vo.query.AccessLogQueryVO;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;
import org.horx.wdf.sys.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问日志API控制器。
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sys/accessLog")
public class AccessLogApiController {

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private AccessLogVoConverter accessLogVoConverter;

    @Autowired
    private AccessLogQueryVoConverter accessLogQueryVoConverter;

    @AccessPermission("sys.accessLog.query")
    @GetMapping("/{id}")
    public Result<AccessLogVO> getById(@PathVariable("id") Long id) {
        AccessLogDTO dto = accessLogService.getById(id);
        AccessLogVO vo = accessLogVoConverter.toVo(dto);
        return new Result<>(vo);
    }

    @AccessPermission("sys.accessLog.query")
    @PostMapping("/pagingQuery")
    public PagingResult<AccessLogVO> pagingQuery(@ArgEntity AccessLogQueryVO query, PagingParam pagingParam) {
        AccessLogQueryDTO accessLogQueryDTO = accessLogQueryVoConverter.fromVo(query);
        PagingQuery<AccessLogQueryDTO> pagingQuery = new PagingQuery<>(accessLogQueryDTO, pagingParam);
        PagingResult<AccessLogDTO> pagingResult = accessLogService.pagingQuery(pagingQuery);
        PagingResult<AccessLogVO> voPagingResult = PagingResult.copy(pagingResult);
        voPagingResult.setData(accessLogVoConverter.toVoList(pagingResult.getData()));
        return voPagingResult;
    }
}
