package net.somta.eventer;

/**
 * @Description //TODO
 * @Author husong
 * @Date 2021/3/4
 * @Version 1.0
 **/
public class EventBody {


    /**
     * 事件类型 Normal：正常事件  Warning：异常事件
     */
    private String eventType;

    /**
     * 资源类型: Pod，Node，HorizontalPodAutoscaler
     */
    private String eventKind;

    /**
     * 发送事件的原因 Unhealthy，BackOff
     */
    private String eventReason;

    /**
     * 事件详细信息
     */
    private String eventMessage;

    /**
     * 事件发生次数
     */
    private Integer eventCount;

    /**
     * 首次发生时间
     */
    private String firstTimestamp;

    /**
     * 最近发生时间
     */
    private String lastTimestamp;

    /**
     * pod命名空间
     */
    private String podNamespace;

    /**
     * pod名称
     */
    private String podName;


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventKind() {
        return eventKind;
    }

    public void setEventKind(String eventKind) {
        this.eventKind = eventKind;
    }

    public String getEventReason() {
        return eventReason;
    }

    public void setEventReason(String eventReason) {
        this.eventReason = eventReason;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public String getFirstTimestamp() {
        return firstTimestamp;
    }

    public void setFirstTimestamp(String firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public String getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(String lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public String getPodNamespace() {
        return podNamespace;
    }

    public void setPodNamespace(String podNamespace) {
        this.podNamespace = podNamespace;
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }
}
