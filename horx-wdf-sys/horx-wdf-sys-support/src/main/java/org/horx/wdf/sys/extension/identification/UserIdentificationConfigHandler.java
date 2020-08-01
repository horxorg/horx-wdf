package org.horx.wdf.sys.extension.identification;

import org.horx.wdf.common.entity.Result;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.sys.dto.UserIdentifyResultDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户认证Handler配置。
 * @since 1.0.1
 */
public class UserIdentificationConfigHandler implements UserIdentificationHandler {

    private List<UserIdentificationHandler> handlerList;

    public List<UserIdentificationHandler> getHandlerList() {
        return handlerList;
    }

    public void setHandlerList(List<UserIdentificationHandler> handlerList) {
        this.handlerList = handlerList;
    }

    @Override
    public boolean beforeIdentify(UserIdentifyDTO userIdentifyDTO, Result<UserIdentifyResultDTO> result) {
        if (CollectionUtils.isEmpty(handlerList)) {
            return true;
        }

        for (UserIdentificationHandler handler : handlerList) {
            boolean isContinue = handler.beforeIdentify(userIdentifyDTO, result);
            if (!isContinue) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean afterIdentify(UserDTO user, Result<UserIdentifyResultDTO> result) {
        if (CollectionUtils.isEmpty(handlerList)) {
            return true;
        }

        for (UserIdentificationHandler handler : handlerList) {
            boolean isContinue = handler.afterIdentify(user, result);
            if (!isContinue) {
                return false;
            }
        }

        return true;
    }

}
