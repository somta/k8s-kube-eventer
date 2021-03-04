package net.somta.eventer.sinks;

import io.fabric8.kubernetes.api.model.Event;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;
import net.somta.eventer.EventBody;

/**
 * @Description //TODO
 * @Author husong
 * @Date 2021/3/3
 * @Version 1.0
 **/
public class SinkManager {

    /**
     * 所有的sink
     */
    private List<ISink> sinks = new ArrayList<>();

    private static final SinkManager sinkManagerInstance = new SinkManager();

    private SinkManager(){ }

    public static SinkManager getSinkManagerInstance(){
        return sinkManagerInstance;
    }

    /**
     * export Event
     * @param eventBody
     */
    public void export(EventBody eventBody){
        for (ISink sink : sinks){
            sink.send(eventBody);
        }
    }

    public void addSink(ISink sink){
        sinks.add(sink);
    }


}
