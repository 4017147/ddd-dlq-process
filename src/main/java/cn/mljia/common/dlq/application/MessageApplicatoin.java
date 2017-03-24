package cn.mljia.common.dlq.application;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mljia.common.dlq.application.command.MessageCommand;
import cn.mljia.common.dlq.domain.Message;
import cn.mljia.common.dlq.domain.MessageRepository;
import cn.mljia.common.dlq.domain.exception.NegativeException;

/**
 * 
 * @ClassName: MessageApplicatoin
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月28日 下午3:29:28
 */
@Service
@Transactional
public class MessageApplicatoin
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageApplicatoin.class);
    
    @Resource
    private MessageRepository messageRepository;
    
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void message(MessageCommand command)
        throws NegativeException
    {
        
        LOGGER.info("RECEIVE MESSAGE COMMAND ===>{}" + command.toString());
        
        try
        {
            Message message = messageRepository.messageOfIdAndType(command.getMessageId(), command.getMessageType());
            if (message == null)
            {
                message = new Message(command.getMessageId(), command.getMessageType());
                message.create(command.getMessageBody(),
                    command.getMessagePipeline(),
                    command.getMessageQueue(),
                    command.getMessageTime(),
                    command.getDeliveryTag(),
                    command.getDeliveryMode(),
                    command.getMessageCount(),
                    command.getReason(),
                    command.getRoutingKeys(),
                    command.getPriority());
            }
            else
            {
                message.counting(command.getMessageCount());
            }
            messageRepository.add(message);
        }
        catch (NegativeException e)
        {
            // TODO Auto-generated catch block
            throw e;
        }
    }
    
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public List<Message> bachProcessAwaitRetryMessages(Integer pageNo, Integer pageSize)
        throws NegativeException
    {
        
        List<Message> messages = this.messageRepository.listMessageOfLimit(pageNo, pageSize);
        for (Message message : messages)
        {
            message.areRetry();
            messageRepository.add(message);
        }
        return messages;
    }
    
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void endOfTheCycle(String messageId, String messageType)
        throws NegativeException
    {
        Message message = messageRepository.messageOfIdAndType(messageId, messageType);
        if (message != null)
            messageRepository.remove(message);
        else
            throw new NegativeException("message is not be null.");
    }
    
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void restoreRetry(String messageId, String messageType)
        throws NegativeException
    {
        Message message = messageRepository.messageOfIdAndType(messageId, messageType);
        if (message != null)
            message.restoreRetry();
        else
            throw new NegativeException("message is not be null.");
    }
    
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void listStandByProcessOfLimit(Integer pageNo, Integer pageSize)
        throws NegativeException
    {
        List<Message> messages = this.messageRepository.listStandByProcessOfLimit(pageNo, pageSize);
        for (Message message : messages)
        {
            message.restoreRetry();
            messageRepository.add(message);
        }
    }
}
