package org.horx.wdf.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 服务器环境工具类。
 * @since 1.0
 */
public class ServerEnvironment {
    private static final Logger logger = LoggerFactory.getLogger(ServerEnvironment.class);

    @Value("${common.serverIpPrefix}")
    private String serverIpPrefix;

    private String serverIp;

    /**
     * 获取服务器ip。
     * @return 服务器ip。
     */
    public String getServerIp() {
        if (serverIp != null) {
            return serverIp;
        }

        String firstIp = null;

        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) {
                        String ip = inetAddress.getHostAddress();
                        if ("127.0.0.1".equals(ip)) {
                            continue;
                        }

                        if (ip != null && ip.startsWith(serverIpPrefix)) {
                            serverIp = ip;
                            break;
                        } else if (firstIp == null) {
                            firstIp = ip;
                        }
                    }
                }

                if (serverIp != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            logger.error("获取服务器网络接口异常", e);
        }

        if (serverIp == null) {
            serverIp = firstIp;
        }
        return serverIp;
    }

    /**
     * 设置服务器ip前缀。
     * @param serverIpPrefix 服务器ip前缀。
     */
    public void setServerIpPrefix(String serverIpPrefix) {
        this.serverIpPrefix = serverIpPrefix;
    }
}
