package org.horx.wdf.sys.config;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.sys.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统管理配置。
 * @since 1.0
 */
public class SysConfig {

    @Value("${sys.admin.username}")
    private String adminUsername;

    @Value("${sys.admin.allMenu}")
    private boolean adminAllMenu;

    @Value("${sys.admin.dataPermission}")
    private String dataPermissionForAdmin;

    private Set<String> adminUsernameSet;

    private Set<String> dataPermissionCodeSet;

    @Value("${sys.accessLog.reserveDays}")
    private int accessLogReserveDays;

    @Value("${sys.dataLog.reserveDays}")
    private int dataLogReserveDays;

    @Value("${sys.vcode.timeout:300000}")
    private long vcodeTimeout;

    @Value("${sys.vcode.allowedErrorTimes:3}")
    private int vcodeAllowedErrorTimes;

    @Value("${sys.vcode.width:105}")
    private int vcodeWidth;

    @Value("${sys.vcode.height:35}")
    private int vcodeHeight;

    @Value("${sys.vcode.charLength:4}")
    private int vcodeCharLength;

    @Value("${sys.url.login}")
    private String loginUrl;

    @Value("${sys.url.vcode}")
    private String vcodeUrl;

    @Value("${sys.url.identify}")
    private String identifyUrl;

    @Value("${sys.url.logout}")
    private String logoutUrl;

    @Value("${sys.url.main}")
    private String mainUrl;

    @PostConstruct
    public void init() {
        adminUsernameSet = stringToSet(adminUsername);
        dataPermissionCodeSet = stringToSet(dataPermissionForAdmin);
    }

    /**
     * 用户是否管理员。
     * @param user
     * @return
     */
    public boolean isAdmin(UserDTO user) {
        return (user != null && adminUsernameSet.contains(user.getUsername()));
    }

    /**
     * 用户是否管理员。
     * @param username
     * @return
     */
    public boolean isAdmin(String username) {
        return (StringUtils.isNotEmpty(username) && adminUsernameSet.contains(username));
    }

    /**
     * 管理员是否对所有才有有访问权限。
     * @return
     */
    public boolean isAdminHasAllMenuPermission() {
        return adminAllMenu;
    }

    /**
     * 管理员对数据权限是否具有全部权限。
     * @param dataPermissionCode
     * @return
     */
    public boolean isAdminHasAllDataPermission(String dataPermissionCode) {
        return dataPermissionCodeSet.contains(dataPermissionCode);
    }

    /**
     * 用户访问日志保留的天数，正数表示保留的天数，0表示不保留，-1表示不删除历史数据。
     * @return
     */
    public int getAccessLogReserveDays() {
        return accessLogReserveDays;
    }

    /**
     * 数据操作日志保留的天数，正数表示保留的天数，0表示不保留，-1表示不删除历史数据。
     * @return
     */
    public int getDataLogReserveDays() {
        return dataLogReserveDays;
    }

    public long getVcodeTimeout() {
        return vcodeTimeout;
    }

    public int getVcodeAllowedErrorTimes() {
        return vcodeAllowedErrorTimes;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getVcodeUrl() {
        return vcodeUrl;
    }

    public String getIdentifyUrl() {
        return identifyUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public int getVcodeWidth() {
        return vcodeWidth;
    }

    public int getVcodeHeight() {
        return vcodeHeight;
    }

    public int getVcodeCharLength() {
        return vcodeCharLength;
    }

    private Set<String> stringToSet(String str) {
        Set<String> set = new HashSet<>();
        if (StringUtils.isNotEmpty(str)) {
            String[] arr = str.split(",");
            for (String s : arr) {
                if (StringUtils.isNotEmpty(s)) {
                    set.add(s.trim());
                }
            }
        }
        return set;
    }
}
