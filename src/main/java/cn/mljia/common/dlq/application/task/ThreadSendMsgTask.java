package cn.mljia.common.dlq.application.task;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 
 * @ClassName: ThreadSendMsgTask
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月28日 下午3:29:44
 */
public class ThreadSendMsgTask implements Runnable, Serializable
{
    
    private static Logger logger = Logger.getLogger(ThreadSendMsgTask.class);
    
    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */
    private static final long serialVersionUID = 1L;
    
    private ThreadTaskListener listener;
    
    private RabbitTemplate rabbitTemplate;
    
    private cn.mljia.common.dlq.domain.Message item;
    
    public ThreadSendMsgTask(ThreadTaskListener listener, cn.mljia.common.dlq.domain.Message message,
        RabbitTemplate rabbitTemplate)
    {
        this.listener = listener;
        this.item = message;
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void run()
    {
        try
        {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType("text/plain");
            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            messageProperties.setPriority(item.priority());
            messageProperties.setMessageId(item.messageId());
            messageProperties.setTimestamp(item.messageTime());
            messageProperties.setType(item.messageType());
            messageProperties.setHeader("messageCount", item.messageCount());
            messageProperties.setDeliveryTag(item.deliveryTag());
            Message message = new Message(item.messageBody().getBytes(), messageProperties);
            rabbitTemplate.send(item.messagePipeline(), "", message);
            TaskResult result = new TaskResult(item.messageId(), item.messageType(), null);
            listener.success(result);
            logger.info("process message success M==={}" + message.toString());
        }
        catch (AmqpException e)
        {
            logger.error("AmqpException e:" + e.getMessage(), e);
            TaskResult result = new TaskResult(item.messageId(), item.messageType(), e.getMessage());
            listener.error(result);
        }
        
    }
}
