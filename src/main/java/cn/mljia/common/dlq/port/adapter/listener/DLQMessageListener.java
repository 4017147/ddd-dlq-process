package cn.mljia.common.dlq.port.adapter.listener;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;

import cn.mljia.common.dlq.application.MessageApplicatoin;
import cn.mljia.common.dlq.application.command.MessageCommand;
import cn.mljia.common.dlq.domain.exception.NegativeException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * 
 * @ClassName: DLQMessageListener
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月28日 下午3:30:28
 */
public class DLQMessageListener implements MessageListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageApplicatoin.class);
    
    @Resource
    private MessageApplicatoin messageApplicatoin;
    
    @Override
    public void onMessage(Message message)
    {
        LOGGER.info("LISTENER  MESSAGE ===>{}" + message.toString());
        
        Gson gson = new Gson();
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        Object object = headers.get("x-death");
        Integer messageCount = 1;
        if (headers.containsKey("messageCount"))
        {
            messageCount = (Integer)headers.get("messageCount");
        }
        JsonParser parser = new JsonParser();
        JsonArray el = parser.parse(gson.toJson(object)).getAsJsonArray();
        String messageQueue = el.get(0).getAsJsonObject().get("queue").getAsString();
        Date messageTime = new Date(el.get(0).getAsJsonObject().get("time").getAsString());
        String exchange = el.get(0).getAsJsonObject().get("exchange").getAsString();
        String routingKeys = el.get(0).getAsJsonObject().get("routing-keys").getAsString();
        String reason = el.get(0).getAsJsonObject().get("reason").getAsString();
        long deliveryTag = messageProperties.getDeliveryTag();
        String deliveryMode = messageProperties.getDeliveryMode().toString();
        String messageType = messageProperties.getType();
        String messageId = messageProperties.getMessageId();
        String messageBody = new String(message.getBody());
        Integer priority = messageProperties.getPriority();
        MessageCommand command =
            new MessageCommand(messageId, messageBody, messageType, exchange, messageCount, messageQueue, messageTime,
                deliveryTag, deliveryMode, reason, routingKeys, priority);
        try
        {
            messageApplicatoin.message(command);
        }
        catch (NegativeException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
