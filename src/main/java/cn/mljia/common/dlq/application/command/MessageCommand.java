package cn.mljia.common.dlq.application.command;

import java.io.Serializable;
import java.util.Date;

import cn.mljia.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: MessageCommand 
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月28日 下午3:29:14
 */
public class MessageCommand extends AssertionConcern implements Serializable
{
    
    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */
    private static final long serialVersionUID = 1L;
    
    private String messageId;
    
    private String messageBody;
    
    private String messageType;
    
    private String messagePipeline;
    
    private Integer messageCount;
    
    private String messageQueue;
    
    private Date messageTime;
    
    private long deliveryTag;
    
    private String deliveryMode;
    
    private String reason;
    
    private String routingKeys;
    
    private Integer priority;
    
    public MessageCommand(String messageId, String messageBody, String messageType, String messagePipeline,
        Integer messageCount, String messageQueue, Date messageTime, long deliveryTag, String deliveryMode,
        String reason, String routingKeys, Integer priority)
    {
        super();
        this.setMessageId(messageId);
        this.setMessageBody(messageBody);
        this.setMessageType(messageType);
        this.setMessagePipeline(messagePipeline);
        this.setMessageCount(messageCount);
        this.setMessageQueue(messageQueue);
        this.setMessageTime(messageTime);
        this.setDeliveryMode(deliveryMode);
        this.setDeliveryTag(deliveryTag);
        this.setReason(reason);
        this.setRoutingKeys(routingKeys);
        this.setPriority(priority);
    }
    
    private void setReason(String reason)
    {
        this.reason = reason;
    }
    
    private void setRoutingKeys(String routingKeys)
    {
        this.routingKeys = routingKeys;
    }
    
    private void setPriority(Integer priority)
    {
        this.priority = priority;
    }
    
    private void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }
    
    private void setMessageBody(String messageBody)
    {
        this.messageBody = messageBody;
    }
    
    private void setMessageType(String messageType)
    {
        this.messageType = messageType;
    }
    
    private void setMessagePipeline(String messagePipeline)
    {
        this.messagePipeline = messagePipeline;
    }
    
    private void setMessageCount(Integer messageCount)
    {
        this.messageCount = messageCount;
    }
    
    private void setMessageQueue(String messageQueue)
    {
        this.messageQueue = messageQueue;
    }
    
    private void setMessageTime(Date messageTime)
    {
        this.messageTime = messageTime;
    }
    
    private void setDeliveryTag(long deliveryTag)
    {
        this.deliveryTag = deliveryTag;
    }
    
    private void setDeliveryMode(String deliveryMode)
    {
        this.deliveryMode = deliveryMode;
    }
    
    public String getMessageId()
    {
        return messageId;
    }
    
    public String getMessageBody()
    {
        return messageBody;
    }
    
    public String getMessageType()
    {
        return messageType;
    }
    
    public String getMessagePipeline()
    {
        return messagePipeline;
    }
    
    public Integer getMessageCount()
    {
        return messageCount;
    }
    
    public String getMessageQueue()
    {
        return messageQueue;
    }
    
    public Date getMessageTime()
    {
        return messageTime;
    }
    
    public long getDeliveryTag()
    {
        return deliveryTag;
    }
    
    public String getDeliveryMode()
    {
        return deliveryMode;
    }
    
    public String getReason()
    {
        return reason;
    }
    
    public String getRoutingKeys()
    {
        return routingKeys;
    }
    
    public Integer getPriority()
    {
        return priority;
    }
    
    @Override
    public String toString()
    {
        return "MessageCommand [messageId=" + messageId + ", messageBody=" + messageBody + ", messageType="
            + messageType + ", messagePipeline=" + messagePipeline + ", messageCount=" + messageCount
            + ", messageQueue=" + messageQueue + ", messageTime=" + messageTime + ", deliveryTag=" + deliveryTag
            + ", deliveryMode=" + deliveryMode + ", reason=" + reason + ", routingKeys=" + routingKeys + ", priority="
            + priority + "]";
    }
    
}
