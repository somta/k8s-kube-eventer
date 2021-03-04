package net.somta.eventer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 全局配置
 * @Author husong
 * @Date 2021/3/4
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "event.config")
public class EventerConfig {

    /**
     * 事件类型 Normal：正常事件  Warning：异常事件
     */
    private String type;

    /**
     * 资源类型: Pod，Node，HorizontalPodAutoscaler
     */
    private String kind;

    /**
     * 事件命名空间
     */
    private String namespace;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
