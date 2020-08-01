package org.horx.wdf.sys.extension.identification;

import org.horx.wdf.common.entity.Result;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.sys.dto.UserIdentifyResultDTO;

/**
 * 用户认证Handler。
 * @since 1.0.1
 */
public interface UserIdentificationHandler {

    /**
     * 用户认证前。
     * @param userIdentifyDTO
     * @param result
     * @return 是否执行后续逻辑。
     */
    boolean beforeIdentify(UserIdentifyDTO userIdentifyDTO, Result<UserIdentifyResultDTO> result);

    /**
     * 用户认证后。
     * @param user
     * @param result
     * @return 是否执行后续逻辑。
     */
    boolean afterIdentify(UserDTO user, Result<UserIdentifyResultDTO> result);
}
