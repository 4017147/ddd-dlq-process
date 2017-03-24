package cn.mljia.common.dlq.application.scheduled;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.mljia.common.dlq.application.MessageApplicatoin;
import cn.mljia.common.dlq.application.task.TaskResult;
import cn.mljia.common.dlq.application.task.ThreadPoolTask;
import cn.mljia.common.dlq.application.task.ThreadTaskListener;
import cn.mljia.common.dlq.domain.exception.NegativeException;

/**
 * 
 * @ClassName: ActivityFinishScheduled
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2016年11月19日 上午9:52:24
 */
@Component
public class MessageProcessScheduled
{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessScheduled.class);
    
    @Autowired
    public MessageApplicatoin messageApplicatoin;
    
    @Resource
    public RabbitTemplate rabbitTemplate;
    
    @Resource
    public ThreadPoolTask threadPoolTask;
    
    // 每5分钟一次
    @Scheduled(cron = "0 0/5 * * * ?")
    public void messageProcess()
    {
        try
        {
            
            final MessageApplicatoin messageApplicatoin = this.getMessageApplicatoin();
            
            List<cn.mljia.common.dlq.domain.Message> messages = messageApplicatoin.bachProcessAwaitRetryMessages(0, 50);
            
            if (messages != null && messages.size() > 0)
            {
                for (cn.mljia.common.dlq.domain.Message item : messages)
                {
                    this.getThreadPoolTask().addTask(new ThreadTaskListener()
                    {
                        
                        @Override
                        public void success(TaskResult result)
                        {
                            try
                            {
                                messageApplicatoin.endOfTheCycle(result.getId(), result.getMessageType());
                            }
                            catch (NegativeException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                
                                LOGGER.error("message send success end of the cycle with info=====>{}"
                                    + result.toString(),
                                    e);
                            }
                        }
                        
                        @Override
                        public void error(TaskResult result)
                        {
                            try
                            {
                                messageApplicatoin.restoreRetry(result.getId(), result.getMessageType());
                            }
                            catch (NegativeException e)
                            {
                                
                                e.printStackTrace();
                                
                                LOGGER.error("message send error restore retry with info=====>{}" + result.toString(),
                                    e);
                            }
                        }
                    },
                        item,
                        this.getRabbitTemplate());
                }
            }
        }
        catch (NegativeException e)
        {
            e.printStackTrace();
            LOGGER.error("message processing with info=====>{}" + e.getMessage(), e);
        }
    }
    
    @Scheduled(cron = "0 0/10 * * * ?")
    // 每10分钟一次
    public void messageRestoreRetry()
    {
        try
        {
            this.getMessageApplicatoin().listStandByProcessOfLimit(0, 50);
        }
        catch (NegativeException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
            LOGGER.error("message restore retry with info=====>{}" + e.getMessage(), e);
        }
    }
    
    private MessageApplicatoin getMessageApplicatoin()
    {
        return messageApplicatoin;
    }
    
    private RabbitTemplate getRabbitTemplate()
    {
        return rabbitTemplate;
    }
    
    private ThreadPoolTask getThreadPoolTask()
    {
        return threadPoolTask;
    }
    
}
