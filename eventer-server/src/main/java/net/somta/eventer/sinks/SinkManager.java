package net.somta.eventer.sinks;

import io.fabric8.kubernetes.api.model.Event;

/**
 * @Description //TODO
 * @Author husong
 * @Date 2021/3/3
 * @Version 1.0
 **/
public class SinkManager {

    private static final SinkManager sinkManagerInstance = new SinkManager();

    private SinkManager(){ }

    public static SinkManager getSinkManagerInstance(){
        return sinkManagerInstance;
    }

    /**
     * export Event
     * @param event
     */
    public void export(Event event){

    }



}
