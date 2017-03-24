package cn.mljia.common.dlq.application.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mljia.common.dlq.domain.MessageRepository;
import cn.mljia.common.dlq.domain.exception.NegativeException;
import cn.mljia.ddd.common.AssertionConcern;

@Service
@Transactional(rollbackFor = {Exception.class, NegativeException.class, RuntimeException.class})
public class MessageQueryService extends AssertionConcern
{
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Transactional(readOnly = true)
    public List<?> listStandByProcessOfLimit(Integer pageNo, Integer pageSize)
        throws NegativeException
    {
        return this.getMessageRepository().listStandByProcessOfLimit(pageNo, pageSize);
    }
    
    private MessageRepository getMessageRepository()
    {
        return messageRepository;
    }
    
}
