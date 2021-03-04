package net.somta.eventer.config;

import net.somta.eventer.sinks.ISink;
import net.somta.eventer.sinks.SinkManager;
import net.somta.eventer.sinks.elasticsearch.ElasticSearchSink;
import net.somta.eventer.sinks.webhook.WebHookSink;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Description Elasticsearch 配置
 * @Author husong
 * @Date 2021/3/4
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "event.sink.elasticsearch")
public class ElasticSearchConfig implements InitializingBean {

    /**
     * ES服务器地址
     */
    private String serverUrl;

    /**
     * ES端口
     */
    private Integer serverPort;

    /**
     * ES用户名
     */
    private String username;

    /**
     * ES密码
     */
    private String password;

    /**
     * ES版本
     */
    private String version;

    /**
     * 存储索引
     */
    private String index = "kube-eventer";

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    private boolean isActivate(){
        if(!StringUtils.isEmpty(serverUrl)){
            return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(isActivate()){
            ISink elasticSearchSink = new ElasticSearchSink(this);
            SinkManager.getSinkManagerInstance().addSink(elasticSearchSink);
        }
    }
}
