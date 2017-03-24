package cn.mljia.common.dlq.application.task;

import java.io.Serializable;

/**
 * 
 * @ClassName: TaskResult
 * @Description: TODO
 * @author: Marker-李云龙
 * @date: 2017年2月28日 上午10:05:12
 */
public class TaskResult implements Serializable
{
    /**
     * @fieldName: serialVersionUID
     * @fieldType: long
     * @Description: TODO
     */
    private static final long serialVersionUID = 1L;
    
    private String id;// 消息ID
    
    private String messageType;// 消息类型
    
    private String result;// 同步结果需要返回处理的数据
    
    public TaskResult(String id, String messageType, String result)
    {
        super();
        this.id = id;
        this.messageType = messageType;
        this.result = result;
    }
    
    public String getId()
    {
        return id;
    }
    
    public String getMessageType()
    {
        return messageType;
    }
    
    public String getResult()
    {
        return result;
    }
    
}
