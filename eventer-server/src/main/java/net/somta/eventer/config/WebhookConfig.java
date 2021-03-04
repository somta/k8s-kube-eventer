package net.somta.eventer.config;

import net.somta.eventer.sinks.ISink;
import net.somta.eventer.sinks.SinkManager;
import net.somta.eventer.sinks.webhook.WebHookSink;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Description: webhook 的配置类
 * @Date: 2020/1/20
 * @Author: 明天的地平线
 * @Blog: https://www.somta.net/
 * @Version: 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "event.sink.webhook")
public class WebhookConfig implements InitializingBean {

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

    boolean isActivate(){
        if(!StringUtils.isEmpty(url)){
            return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("类加载完成了" + url + "isActivate="+isActivate());
        if(isActivate()){
            ISink webhookSink = new WebHookSink(this);
            SinkManager.getSinkManagerInstance().addSink(webhookSink);
        }
    }

}
