package net.somta.eventer.receive;

import io.fabric8.kubernetes.api.model.Event;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SinkManager sinkManager =SinkManager.getSinkManagerInstance();
        while (true){
            kubernetesClient.v1().events().inAnyNamespace().watch(new Watcher<Event>() {
                @Override
                public void eventReceived(Action action, Event event) {
                    System.out.println("action" + action + "=event="+event);
                    sinkManager.export(event);
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
