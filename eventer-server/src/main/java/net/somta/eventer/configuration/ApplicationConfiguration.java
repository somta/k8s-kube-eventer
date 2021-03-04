package net.somta.eventer.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

/**
 * @Description: 应用全局配置类
 * @Date: 2020/12/16
 * @Author: 明天的地平线
 * @Blog: https://somta.net/
 * @Version: 1.0.0
 */
@Configuration
public class ApplicationConfiguration {

    private Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Value("${k8s.cluster.config.url}")
    private String clusterUrl;
    @Value("${k8s.cluster.config.token}")
    private String clusterToken;


    @Bean
    public KubernetesClient getKubernetesClient() {
        Config config = new ConfigBuilder()
                .withMasterUrl(clusterUrl)
                .withOauthToken(clusterToken)
                .build();
        return new DefaultKubernetesClient(config);
    }

  /*  @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //允许使用未带引号的字段名
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //允许使用单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return objectMapper;
    }*/

}
