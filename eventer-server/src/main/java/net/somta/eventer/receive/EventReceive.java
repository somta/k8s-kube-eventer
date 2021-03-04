package net.somta.eventer.receive;

import com.github.pagehelper.util.StringUtil;
import io.fabric8.kubernetes.api.model.Event;
import io.fabric8.kubernetes.api.model.EventList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import net.somta.eventer.EventBody;
import net.somta.eventer.config.EventerConfig;
import net.somta.eventer.sinks.SinkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Date: 2021/3/3
 * @Author: 明天的地平线
 * @Blog: https://www.somta.net/
 * @Version: 1.0.0
 */
@Component
public class EventReceive implements ApplicationRunner {

    @Autowired
    private KubernetesClient kubernetesClient;
    @Autowired
    private EventerConfig eventerConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SinkManager sinkManager =SinkManager.getSinkManagerInstance();
        EventBody eventBody = new EventBody();
        while (true){
            if(StringUtil.isNotEmpty(eventerConfig.getNamespace())){
                kubernetesClient.v1().events().inNamespace(eventerConfig.getNamespace()).watch(new Watcher<Event>() {
                    @Override
                    public void eventReceived(Action action, Event event) {
                        buildEvent(sinkManager,eventBody,event);
                    }
                    @Override
                    public void onClose() {

                    }
                    @Override
                    public void onClose(WatcherException e) {

                    }
                });
            }else{
                kubernetesClient.v1().events().inAnyNamespace().watch(new Watcher<Event>() {
                    @Override
                    public void eventReceived(Action action, Event event) {
                        buildEvent(sinkManager,eventBody,event);
                    }
                    @Override
                    public void onClose() {

                    }
                    @Override
                    public void onClose(WatcherException e) {

                    }
                });
            }
        }
    }

    private void buildEvent(SinkManager sinkManager,EventBody eventBody,Event event){
        //System.out.println("action" + action + "=event="+event);
        eventBody.setEventType(event.getType());
        eventBody.setEventKind(event.getInvolvedObject().getKind());
        eventBody.setEventReason(event.getReason());
        eventBody.setEventMessage(event.getMessage());
        eventBody.setEventCount(event.getCount());
        eventBody.setFirstTimestamp(event.getFirstTimestamp());
        eventBody.setLastTimestamp(event.getLastTimestamp());
        eventBody.setPodNamespace(event.getInvolvedObject().getNamespace());
        eventBody.setPodName(event.getInvolvedObject().getName());
        sinkManager.export(eventBody);
    }
}
