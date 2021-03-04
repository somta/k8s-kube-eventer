package net.somta.eventer.sinks.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.api.model.Event;
import net.somta.common.utils.HttpClientUtil;
import net.somta.common.utils.StringUtil;
import net.somta.eventer.EventBody;
import net.somta.eventer.config.WebhookConfig;
import net.somta.eventer.configuration.ApplicationConfiguration;
import net.somta.eventer.sinks.ISink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description //TODO
 * @Author husong
 * @Date 2021/3/3
 * @Version 1.0
 **/
public class WebHookSink implements ISink {

    private Logger logger = LoggerFactory.getLogger(WebHookSink.class);

    private final WebhookConfig webhookConfig;

    private ObjectMapper objectMapper = new ObjectMapper();

    public WebHookSink(WebhookConfig webhookConfig) {
        this.webhookConfig = webhookConfig;
    }

    @Override
    public void send(EventBody eventBody) {
        eventBody.setEventMessage(StringUtil.removeDoubleQuotes(StringUtil.removeEnterKey(eventBody.getEventMessage())));
        logger.debug("开始发送webhook事件");
        try {
            String jsonBody = objectMapper.writeValueAsString(eventBody);
            HttpClientUtil.doPost(webhookConfig.getUrl(),jsonBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
