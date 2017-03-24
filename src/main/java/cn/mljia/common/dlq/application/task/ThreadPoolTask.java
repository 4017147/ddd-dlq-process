package cn.mljia.common.dlq.application.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: ThreadPoolTask
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月28日 下午3:29:37
 */
@Component
public class ThreadPoolTask
{
    private ThreadPoolExecutor taskExecutor;// 任务队列线程池
    
    @PostConstruct
    public void init()
    {
        int processNumber = Runtime.getRuntime().availableProcessors();// 根据处理器CPU核心数来定义
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(processNumber * 50);
        taskExecutor =
            new ThreadPoolExecutor(processNumber * 2, processNumber * 10, 60, TimeUnit.SECONDS, queue,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
    
    public void addTask(ThreadTaskListener listener, cn.mljia.common.dlq.domain.Message message,
        RabbitTemplate rabbitTemplate)
    {
        taskExecutor.execute(new ThreadSendMsgTask(listener, message, rabbitTemplate));
    }
}
