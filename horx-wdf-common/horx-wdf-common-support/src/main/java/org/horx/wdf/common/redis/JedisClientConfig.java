package org.horx.wdf.common.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.horx.common.utils.DurationUtils;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.time.Duration;
import java.util.Optional;

/**
 * Jedis客户端配置。
 * @since 1.0.3
 */
public class JedisClientConfig implements JedisClientConfiguration {
    private boolean useSsl;
    private Optional<SSLSocketFactory> sslSocketFactory;
    private Optional<SSLParameters> sslParameters;
    private Optional<HostnameVerifier> hostnameVerifier;
    private boolean usePooling;
    private Optional<GenericObjectPoolConfig> poolConfig;
    private Optional<String> clientName;
    private Duration readTimeout;
    private Duration connectTimeout;

    public JedisClientConfig() {
        sslSocketFactory = Optional.empty();
        sslParameters = Optional.empty();
        hostnameVerifier = Optional.empty();
        usePooling = false;
        poolConfig = Optional.empty();
        this.clientName = Optional.empty();
        this.readTimeout = Duration.ofMillis(2000L);
        this.connectTimeout = Duration.ofMillis(2000L);
    }

    @Override
    public boolean isUseSsl() {
        return this.useSsl;
    }

    @Override
    public Optional<SSLSocketFactory> getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    @Override
    public Optional<SSLParameters> getSslParameters() {
        return this.sslParameters;
    }

    @Override
    public Optional<HostnameVerifier> getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Override
    public boolean isUsePooling() {
        return this.usePooling;
    }

    @Override
    public Optional<GenericObjectPoolConfig> getPoolConfig() {
        return this.poolConfig;
    }

    @Override
    public Optional<String> getClientName() {
        return this.clientName;
    }

    @Override
    public Duration getReadTimeout() {
        return this.readTimeout;
    }

    @Override
    public Duration getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setUseSsl(boolean useSsl) {
        this.useSsl = useSsl;
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = Optional.ofNullable(sslSocketFactory);
    }

    public void setSslParameters(SSLParameters sslParameters) {
        this.sslParameters = Optional.ofNullable(sslParameters);
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = Optional.ofNullable(hostnameVerifier);
    }

    public void setUsePooling(boolean usePooling) {
        this.usePooling = usePooling;
    }

    public void setPoolConfig(GenericObjectPoolConfig poolConfig) {
        this.poolConfig = Optional.ofNullable(poolConfig);
    }

    public void setClientName(String clientName) {
        this.clientName = Optional.ofNullable(clientName);;
    }

    public void setReadTimeout(String readTimeout) {
        this.readTimeout = DurationUtils.parse(readTimeout);
    }

    public void setConnectTimeout(String connectTimeout) {
        this.connectTimeout = DurationUtils.parse(connectTimeout);
    }
}
