package cn.mljia.common.dlq.domain;

import java.util.Date;

import cn.mljia.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 
 * @ClassName: Message
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月27日 下午12:42:57
 */
public class Message extends ConcurrencySafeEntity
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
    
    private String messageQueue;
    
    private Integer messageCount;
    
    private Date messageTime;
    
    private long deliveryTag;
    
    private String deliveryMode;
    
    private String reason;
    
    private String routingKeys;
    
    private Integer priority;
    
    private Date createTime;
    
    private Date modifyTime;
    
    private MessageStatus messageStatus;
    
    public Message()
    {
        super();
    }
    
    public Message(String messageId, String messageType)
    {
        super();
        this.setMessageId(messageId);
        this.setMessageType(messageType);
    }
    
    public void create(String messageBody, String messagePipeline, String messageQueue, Date messageTime,
        long deliveryTag, String deliveryMode, Integer messageCount, String reason, String routingKeys, Integer priority)
    {
        this.setMessageBody(messageBody);
        this.setMessagePipeline(messagePipeline);
        this.setMessageCount(messageCount + 1);
        this.setMessageTime(messageTime);
        this.setMessageQueue(messageQueue);
        this.setDeliveryMode(deliveryMode);
        this.setDeliveryTag(deliveryTag);
        this.setCreateTime(new Date());
        this.setModifyTime(new Date());
        if (this.messageCount() < 5)
        {
            this.setMessageStatus(MessageStatus.AWAIT_RETRY);
        }
        else
        {
            this.setMessageStatus(MessageStatus.RETRY_QUIT);
        }
        this.setReason(reason);
        this.setRoutingKeys(routingKeys);
        this.setPriority(priority);
        
    }
    
    public void counting(Integer messageCount)
    {
        this.setMessageCount(messageCount + 1);
        if (this.messageCount() < 5)
        {
            this.setMessageStatus(MessageStatus.AWAIT_RETRY);
        }
        else
        {
            this.setMessageStatus(MessageStatus.RETRY_QUIT);
        }
        this.setModifyTime(new Date());
    }
    
    public void areRetry()
    {
        this.setMessageStatus(MessageStatus.ARE_RETRY);
        this.setModifyTime(new Date());
    }
    
    public void retryQuit()
    {
        this.setMessageStatus(MessageStatus.RETRY_QUIT);
        this.setModifyTime(new Date());
    }
    
    public void restoreRetry()
    {
        this.setMessageStatus(MessageStatus.AWAIT_RETRY);
        this.setModifyTime(new Date());
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
    
    private void setMessageQueue(String messageQueue)
    {
        this.messageQueue = messageQueue;
    }
    
    private void setMessageCount(Integer messageCount)
    {
        this.messageCount = messageCount;
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
    
    private void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    private void setModifyTime(Date modifyTime)
    {
        this.modifyTime = modifyTime;
    }
    
    private void setMessageStatus(MessageStatus messageStatus)
    {
        this.messageStatus = messageStatus;
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
    
    public Integer messageCount()
    {
        return this.messageCount;
    }
    
    public Integer priority()
    {
        return this.priority;
    }
    
    public String messageId()
    {
        return this.messageId;
    }
    
    public Date messageTime()
    {
        return this.messageTime;
    }
    
    public String messageType()
    {
        return this.messageType;
    }
    
    public String messageBody()
    {
        return this.messageBody;
    }
    
    public String messagePipeline()
    {
        return this.messagePipeline;
    }
    
    public long deliveryTag()
    {
        
        return this.deliveryTag;
    }
    
    @Override
    public String toString()
    {
        return "Message [messageId=" + messageId + ", messageBody=" + messageBody + ", messageType=" + messageType
            + ", messagePipeline=" + messagePipeline + ", messageQueue=" + messageQueue + ", messageCount="
            + messageCount + ", messageTime=" + messageTime + ", deliveryTag=" + deliveryTag + ", deliveryMode="
            + deliveryMode + ", reason=" + reason + ", routingKeys=" + routingKeys + ", priority=" + priority
            + ", createTime=" + createTime + ", modifyTime=" + modifyTime + ", messageStatus=" + messageStatus + "]";
    }
    
}
