package cn.mljia.common.dlq.domain;

/**
 * 
 * @ClassName: MessageStatus
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月28日 下午3:30:09
 */
public enum MessageStatus
{
    
    AWAIT_RETRY(1, "AWAIT_RETRY"), // 等待重试
    ARE_RETRY(2, "ARE_RETRY"), // 准备重试
    RETRY_QUIT(3, "RETRY_QUIT");// 重试退出
    
    private Integer id;
    
    private String name;
    
    private MessageStatus(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
}
