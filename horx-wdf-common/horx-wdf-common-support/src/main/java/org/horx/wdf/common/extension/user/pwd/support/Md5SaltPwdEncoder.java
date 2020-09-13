package org.horx.wdf.common.extension.user.pwd.support;

import org.horx.wdf.common.extension.user.CommonUser;
import org.horx.wdf.common.extension.user.pwd.PwdEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

/**
 * MD5加盐的密码Encoder。
 * @since 1.0
 */
public class Md5SaltPwdEncoder implements PwdEncoder {
    private static final Logger logger = LoggerFactory.getLogger(Md5SaltPwdEncoder.class);

    @Override
    public void modifyPwd(CommonUser user) {
        String uuid = UUID.randomUUID().toString();
        user.setPwdSalt(uuid);
        user.setPwdModifyTime(new Date());
        String encodedPwd = pwdMd5(user.getPassword(), uuid);
        user.setPassword(encodedPwd);
    }

    @Override
    public String encodePwd(CommonUser user) {
        String encodedPwd = pwdMd5(user.getPassword(), user.getPwdSalt());
        return encodedPwd;
    }

    private String pwdMd5(String pwd, String salt) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(salt);
            builder.append(pwd);
            byte[] md5Bytes =  MessageDigest.getInstance("md5").digest(builder.toString().getBytes());
            String encodedPwd = Base64Utils.encodeToString(md5Bytes);
            return encodedPwd;
        } catch (Exception e) {
            throw new RuntimeException("密码加密异常", e);
        }
    }
}
