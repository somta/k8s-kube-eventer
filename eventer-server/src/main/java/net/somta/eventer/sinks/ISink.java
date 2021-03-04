package net.somta.eventer.sinks;

import net.somta.eventer.EventBody;

public interface ISink {

    /**
     * 发送事件
     */
    void send(EventBody eventBody);

}
