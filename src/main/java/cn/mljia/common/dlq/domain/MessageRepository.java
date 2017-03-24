package cn.mljia.common.dlq.domain;

import java.util.List;

import cn.mljia.common.dlq.domain.exception.NegativeException;

/**
 * 
 * @ClassName: MessageRepository
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月28日 下午3:30:03
 */
public interface MessageRepository
{
    
    public void add(Message message)
        throws NegativeException;
    
    public void remove(Message message)
        throws NegativeException;
    
    public Message messageOfIdAndType(String messageId, String type)
        throws NegativeException;
    
    public List<Message> listMessageOfLimit(Integer pageNo, Integer pageSize);
    
    public List<Message> listStandByProcessOfLimit(Integer pageNo, Integer pageSize);
}
