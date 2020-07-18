package org.horx.wdf.common.enums;

import org.horx.wdf.common.entity.ErrorCode;

/**
 * 错误码枚举。
 * @since 1.0
 */
public enum ErrorCodeEnum implements ErrorCode {
    OK("00000", "common.error.00000", "成功", 0, ""),
    A0001("A0001", "common.error.A0001", "用户端错误", 1, ""),
    A0100("A0100", "common.error.A0100", "用户注册错误", 2, "A0001"),
    A0101("A0101", "common.error.A0101", "用户未同意隐私协议", 3, "A0100"),
    A0102("A0102", "common.error.A0102", "注册国家或地区受限", 3, "A0100"),
    A0110("A0110", "common.error.A0110", "用户名校验失败", 3, "A0100"),
    A0111("A0111", "common.error.A0111", "用户名已存在", 4, "A0110"),
    A0112("A0112", "common.error.A0112", "用户名包含敏感词", 4, "A0110"),
    A0113("A0113", "common.error.A0113", "用户名包含特殊字符", 4, "A0110"),
    A0120("A0120", "common.error.A0120", "密码校验失败", 3, "A0100"),
    A0121("A0121", "common.error.A0121", "密码长度不够", 4, "A0120"),
    A0122("A0122", "common.error.A0122", "密码强度不够", 4, "A0120"),
    A0130("A0130", "common.error.A0130", "校验码输入错误", 3, "A0100"),
    A0131("A0131", "common.error.A0131", "短信校验码输入错误", 4, "A0130"),
    A0132("A0132", "common.error.A0132", "邮件校验码输入错误", 4, "A0130"),
    A0133("A0133", "common.error.A0133", "语音校验码输入错误", 4, "A0130"),
    A0140("A0140", "common.error.A0140", "用户证件异常", 3, "A0100"),
    A0141("A0141", "common.error.A0141", "用户证件类型未选择", 4, "A0140"),
    A0142("A0142", "common.error.A0142", "大陆身份证编号校验非法", 4, "A0140"),
    A0143("A0143", "common.error.A0143", "护照编号校验非法", 4, "A0140"),
    A0144("A0144", "common.error.A0144", "军官证编号校验非法", 4, "A0140"),
    A0150("A0150", "common.error.A0150", "用户基本信息校验失败", 3, "A0100"),
    A0151("A0151", "common.error.A0151", "手机格式校验失败", 4, "A0150"),
    A0152("A0152", "common.error.A0152", "地址格式校验失败", 4, "A0150"),
    A0153("A0153", "common.error.A0153", "邮箱格式校验失败", 4, "A0150"),
    A0200("A0200", "common.error.A0200", "用户登录异常 ", 2, "A0001"),
    A0201("A0201", "common.error.A0201", "用户账户不存在", 3, "A0200"),
    A0202("A0202", "common.error.A0202", "用户账户被冻结", 3, "A0200"),
    A0203("A0203", "common.error.A0203", "用户账户已作废", 3, "A0200"),
    A0210("A0210", "common.error.A0210", "用户密码错误", 3, "A0200"),
    A0211("A0211", "common.error.A0211", "用户输入密码次数超限", 3, "A0200"),
    A0220("A0220", "common.error.A0220", "用户身份校验失败", 3, "A0200"),
    A0221("A0221", "common.error.A0221", "用户指纹识别失败", 4, "A0220"),
    A0222("A0222", "common.error.A0222", "用户面容识别失败", 4, "A0220"),
    A0223("A0223", "common.error.A0223", "用户未获得第三方登录授权", 4, "A0220"),
    A0230("A0230", "common.error.A0230", "用户未登录或登录已过期", 3, "A0200"),
    A0240("A0240", "common.error.A0240", "用户验证码错误", 3, "A0200"),
    A0241("A0241", "common.error.A0241", "用户验证码尝试次数超限", 3, "A0200"),
    A0300("A0300", "common.error.A0300", "访问权限异常", 2, "A0001"),
    A0301("A0301", "common.error.A0301", "访问未授权", 3, "A0300"),
    A0302("A0302", "common.error.A0302", "正在授权中", 3, "A0300"),
    A0303("A0303", "common.error.A0303", "用户授权申请被拒绝", 3, "A0300"),
    A0310("A0310", "common.error.A0310", "因访问对象隐私设置被拦截", 3, "A0300"),
    A0311("A0311", "common.error.A0311", "授权已过期", 3, "A0300"),
    A0312("A0312", "common.error.A0312", "无权限使用API", 3, "A0300"),
    A0320("A0320", "common.error.A0320", "用户访问被拦截", 3, "A0300"),
    A0321("A0321", "common.error.A0321", "黑名单用户", 4, "A0320"),
    A0322("A0322", "common.error.A0322", "账号被冻结", 4, "A0320"),
    A0323("A0323", "common.error.A0323", "非法IP地址", 4, "A0320"),
    A0324("A0324", "common.error.A0324", "网关访问受限", 4, "A0320"),
    A0325("A0325", "common.error.A0325", "地域黑名单", 4, "A0320"),
    A0330("A0330", "common.error.A0330", "服务已欠费", 3, "A0300"),
    A0340("A0340", "common.error.A0340", "用户签名异常", 3, "A0300"),
    A0341("A0341", "common.error.A0341", "RSA签名错误", 4, "A0340"),
    A0400("A0400", "common.error.A0400", "用户请求参数错误", 2, "A0001"),
    A0401("A0401", "common.error.A0401", "包含非法恶意跳转链接", 3, "A0400"),
    A0402("A0402", "common.error.A0402", "无效的用户输入", 3, "A0400"),
    A0410("A0410", "common.error.A0410", "请求必填参数为空", 3, "A0400"),
    A0411("A0411", "common.error.A0411", "用户订单号为空", 4, "A0410"),
    A0412("A0412", "common.error.A0412", "订购数量为空", 4, "A0410"),
    A0413("A0413", "common.error.A0413", "缺少时间戳参数", 4, "A0410"),
    A0414("A0414", "common.error.A0414", "非法的时间戳参数", 4, "A0410"),
    A0420("A0420", "common.error.A0420", "请求参数值超出允许的范围", 3, "A0400"),
    A0421("A0421", "common.error.A0421", "参数格式不匹配", 4, "A0420"),
    A0422("A0422", "common.error.A0422", "地址不在服务范围", 4, "A0420"),
    A0423("A0423", "common.error.A0423", "时间不在服务范围", 4, "A0420"),
    A0424("A0424", "common.error.A0424", "金额超出限制", 4, "A0420"),
    A0425("A0425", "common.error.A0425", "数量超出限制", 4, "A0420"),
    A0426("A0426", "common.error.A0426", "请求批量处理总个数超出限制", 4, "A0420"),
    A0427("A0427", "common.error.A0427", "请求JSON解析失败", 4, "A0420"),
    A0430("A0430", "common.error.A0430", "用户输入内容非法", 3, "A0400"),
    A0431("A0431", "common.error.A0431", "包含违禁敏感词", 4, "A0431"),
    A0432("A0432", "common.error.A0432", "图片包含违禁信息", 4, "A0431"),
    A0433("A0433", "common.error.A0433", "文件侵犯版权", 4, "A0431"),
    A0440("A0440", "common.error.A0440", "用户操作异常", 3, "A0400"),
    A0441("A0441", "common.error.A0441", "用户支付超时", 4, "A0440"),
    A0442("A0442", "common.error.A0442", "确认订单超时", 4, "A0440"),
    A0443("A0443", "common.error.A0443", "订单已关闭", 4, "A0440"),
    A0500("A0500", "common.error.A0500", "用户请求服务异常", 2, "A0001"),
    A0501("A0501", "common.error.A0501", "请求次数超出限制", 3, "A0500"),
    A0502("A0502", "common.error.A0502", "请求并发数超出限制", 3, "A0500"),
    A0503("A0503", "common.error.A0503", "用户操作请等待", 3, "A0500"),
    A0504("A0504", "common.error.A0504", "WebSocket连接异常", 3, "A0500"),
    A0505("A0505", "common.error.A0505", "WebSocket连接断开", 3, "A0500"),
    A0506("A0506", "common.error.A0506", "用户重复请求", 3, "A0500"),
    A0600("A0600", "common.error.A0600", "用户资源异常", 2, "A0001"),
    A0601("A0601", "common.error.A0601", "账户余额不足", 3, "A0600"),
    A0602("A0602", "common.error.A0602", "用户磁盘空间不足", 3, "A0600"),
    A0603("A0603", "common.error.A0603", "用户内存空间不足", 3, "A0600"),
    A0604("A0604", "common.error.A0604", "用户OSS容量不足", 3, "A0600"),
    A0605("A0605", "common.error.A0605", "用户配额已用光", 3, "A0600"),
    A0700("A0700", "common.error.A0700", "用户上传文件异常", 2, "A0001"),
    A0701("A0701", "common.error.A0701", "用户上传文件类型不匹配", 3, "A0700"),
    A0702("A0702", "common.error.A0702", "用户上传文件太大", 3, "A0700"),
    A0703("A0703", "common.error.A0703", "用户上传图片太大", 3, "A0700"),
    A0704("A0704", "common.error.A0704", "用户上传视频太大", 3, "A0700"),
    A0705("A0705", "common.error.A0705", "用户上传压缩文件太大", 3, "A0700"),
    A0800("A0800", "common.error.A0800", "用户当前版本异常", 2, "A0001"),
    A0801("A0801", "common.error.A0801", "用户安装版本与系统不匹配", 3, "A0800"),
    A0802("A0802", "common.error.A0802", "用户安装版本过低", 3, "A0800"),
    A0803("A0803", "common.error.A0803", "用户安装版本过高", 3, "A0800"),
    A0804("A0804", "common.error.A0804", "用户安装版本已过期", 3, "A0800"),
    A0805("A0805", "common.error.A0805", "用户API请求版本不匹配", 3, "A0800"),
    A0806("A0806", "common.error.A0806", "用户API请求版本过高", 3, "A0800"),
    A0807("A0807", "common.error.A0807", "用户API请求版本过低", 3, "A0800"),
    A0900("A0900", "common.error.A0900", "用户隐私未授权", 2, "A0001"),
    A0901("A0901", "common.error.A0901", "用户隐私未签署", 3, "A0900"),
    A0902("A0902", "common.error.A0902", "用户摄像头未授权", 3, "A0900"),
    A0903("A0903", "common.error.A0903", "用户相机未授权", 3, "A0900"),
    A0904("A0904", "common.error.A0904", "用户图片库未授权", 3, "A0900"),
    A0905("A0905", "common.error.A0905", "用户文件未授权", 3, "A0900"),
    A0906("A0906", "common.error.A0906", "用户位置信息未授权", 3, "A0900"),
    A0907("A0907", "common.error.A0907", "用户通讯录未授权", 3, "A0900"),
    A1000("A1000", "common.error.A1000", "用户设备异常", 2, "A0001"),
    A1001("A1001", "common.error.A1001", "用户相机异常", 3, "A1000"),
    A1002("A1002", "common.error.A1002", "用户麦克风异常", 3, "A1000"),
    A1003("A1003", "common.error.A1003", "用户听筒异常", 3, "A1000"),
    A1004("A1004", "common.error.A1004", "用户扬声器异常", 3, "A1000"),
    A1005("A1005", "common.error.A1005", "用户GPS定位异常", 3, "A1000"),
    B0001("B0001", "common.error.B0001", "系统执行出错", 1, ""),
    B0100("B0100", "common.error.B0100", "系统执行超时", 2, "B0001"),
    B0101("B0101", "common.error.B0101", "系统订单处理超时", 3, "B0100"),
    B0200("B0200", "common.error.B0200", "系统容灾功能被触发", 2, "B0001"),
    B0210("B0210", "common.error.B0210", "系统限流", 3, "B0200"),
    B0220("B0220", "common.error.B0220", "系统功能降级", 3, "B0200"),
    B0300("B0300", "common.error.B0300", "系统资源异常", 2, "B0001"),
    B0310("B0310", "common.error.B0310", "系统资源耗尽", 3, "B0310"),
    B0311("B0311", "common.error.B0311", "系统磁盘空间耗尽", 4, "B0310"),
    B0312("B0312", "common.error.B0312", "系统内存耗尽", 4, "B0310"),
    B0313("B0313", "common.error.B0313", "文件句柄耗尽", 4, "B0310"),
    B0314("B0314", "common.error.B0314", "系统连接池耗尽", 4, "B0310"),
    B0315("B0315", "common.error.B0315", "系统线程池耗尽", 4, "B0310"),
    B0320("B0320", "common.error.B0320", "系统资源访问异常", 3, "B0310"),
    B0321("B0321", "common.error.B0321", "系统读取磁盘文件失败", 4, "B0320"),
    C0001("C0001", "common.error.C0001", "调用第三方服务出错", 1, ""),
    C0100("C0100", "common.error.C0100", "中间件服务出错", 2, "C0001"),
    C0110("C0110", "common.error.C0110", "RPC服务出错", 3, "C0100"),
    C0111("C0111", "common.error.C0111", "RPC服务未找到", 4, "C0110"),
    C0112("C0112", "common.error.C0112", "RPC服务未注册", 4, "C0110"),
    C0113("C0113", "common.error.C0113", "接口不存在", 4, "C0110"),
    C0120("C0120", "common.error.C0120", "消息服务出错", 3, "C0100"),
    C0121("C0121", "common.error.C0121", "消息投递出错", 4, "C0120"),
    C0122("C0122", "common.error.C0122", "消息消费出错", 4, "C0120"),
    C0123("C0123", "common.error.C0123", "消息订阅出错", 4, "C0120"),
    C0124("C0124", "common.error.C0124", "消息分组未查到", 4, "C0120"),
    C0130("C0130", "common.error.C0130", "缓存服务出错", 3, "C0100"),
    C0131("C0131", "common.error.C0131", "key长度超过限制", 4, "C0130"),
    C0132("C0132", "common.error.C0132", "value长度超过限制", 4, "C0130"),
    C0133("C0133", "common.error.C0133", "存储容量已满", 4, "C0130"),
    C0134("C0134", "common.error.C0134", "不支持的数据格式", 4, "C0130"),
    C0140("C0140", "common.error.C0140", "配置服务出错", 3, "C0100"),
    C0150("C0150", "common.error.C0150", "网络资源服务出错", 3, "C0100"),
    C0151("C0151", "common.error.C0151", "VPN服务出错", 4, "C0150"),
    C0152("C0152", "common.error.C0152", "CDN服务出错", 4, "C0150"),
    C0153("C0153", "common.error.C0153", "域名解析服务出错", 4, "C0150"),
    C0154("C0154", "common.error.C0154", "网关服务出错", 4, "C0150"),
    C0200("C0200", "common.error.C0200", "第三方系统执行超时", 2, "C0001"),
    C0210("C0210", "common.error.C0210", "RPC执行超时", 3, "C0200"),
    C0220("C0220", "common.error.C0220", "消息投递超时", 3, "C0200"),
    C0230("C0230", "common.error.C0230", "缓存服务超时", 3, "C0200"),
    C0240("C0240", "common.error.C0240", "配置服务超时", 3, "C0200"),
    C0250("C0250", "common.error.C0250", "数据库服务超时", 3, "C0200"),
    C0300("C0300", "common.error.C0300", "数据库服务出错", 2, "C0001"),
    C0311("C0311", "common.error.C0311", "表不存在", 3, "C0300"),
    C0312("C0312", "common.error.C0312", "列不存在", 3, "C0300"),
    C0321("C0321", "common.error.C0321", "多表关联中存在多个相同名称的列", 3, "C0300"),
    C0331("C0331", "common.error.C0331", "数据库死锁", 3, "C0300"),
    C0341("C0341", "common.error.C0341", "主键冲突", 3, "C0300"),
    C0400("C0400", "common.error.C0400", "第三方容灾系统被触发", 2, "C0001"),
    C0401("C0401", "common.error.C0401", "第三方系统限流", 3, "C0400"),
    C0402("C0402", "common.error.C0402", "第三方功能降级", 3, "C0400"),
    C0500("C0500", "common.error.C0500", "通知服务出错", 2, "C0001"),
    C0501("C0501", "common.error.C0501", "短信提醒服务失败", 3, "C0500"),
    C0502("C0502", "common.error.C0502", "语音提醒服务失败", 3, "C0500"),
    C0503("C0503", "common.error.C0503", "邮件提醒服务失败", 3, "C0500");

    private ErrorCodeEnum(String code, String msgKey, String comment, Integer level, String parentCode) {
        this.code = code;
        this.msgKey = msgKey;
        this.comment = comment;
        this.level = level;
        this.parentCode = parentCode;
    }

    private final String code;

    private final String msgKey;

    private final String comment;

    private final Integer level;

    private final String parentCode;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsgKey() {
        return msgKey;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 通过编码获取枚举。
     * @param code
     * @return
     */
    public static ErrorCodeEnum getByCode(String code) {
        ErrorCodeEnum[] arr = ErrorCodeEnum.values();
        for (ErrorCodeEnum errorCodeEnum : arr) {
            if (errorCodeEnum.getCode().equals(code)) {
                return errorCodeEnum;
            }
        }
        return null;
    }
}
