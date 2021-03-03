package net.somta.eventer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: webhook 的配置类
 * @Date: 2020/1/20
 * @Author: 明天的地平线
 * @Blog: https://www.somta.net/
 * @Version: 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "event.sink.webhook")
public class WebhookConfig {

    /**
     * 请求地址
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
