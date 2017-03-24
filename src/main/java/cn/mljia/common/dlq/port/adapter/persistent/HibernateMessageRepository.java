//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
package cn.mljia.common.dlq.port.adapter.persistent;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import cn.mljia.common.dlq.domain.Message;
import cn.mljia.common.dlq.domain.MessageRepository;
import cn.mljia.common.dlq.domain.exception.NegativeException;
import cn.mljia.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;

/**
 * 
 * @ClassName: HibernateMessageRepository
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月28日 下午3:30:35
 */
@Repository
public class HibernateMessageRepository extends HibernateSupperRepository implements MessageRepository
{
    
    public HibernateMessageRepository()
    {
        super();
    }
    
    @Override
    public void add(Message message)
        throws NegativeException
    {
        try
        {
            this.session().merge(message);
        }
        catch (ConstraintViolationException e)
        {
            throw new IllegalStateException("User is not unique.", e);
        }
    }
    
    @Override
    public void remove(Message message)
        throws NegativeException
    {
        this.session().delete(message);
    }
    
    @Override
    public Message messageOfIdAndType(String messageId, String type)
        throws NegativeException
    {
        
        Message message =
            (Message)this.session()
                .createQuery(" from Message message where message.messageId = :messageId AND message.messageType= :messageType ")
                .setParameter("messageId", messageId)
                .setParameter("messageType", type)
                .uniqueResult();
        return message;
        
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Message> listMessageOfLimit(Integer pageNo, Integer pageSize)
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT t_r_d.* FROM tb_rabbitmq_dlq AS t_r_d WHERE t_r_d.`MESSAGE_STATUS`='AWAIT_RETRY' order by t_r_d.CREATE_TIME ASC");
        Query query =
            this.session()
                .createSQLQuery(sql.toString())
                .addEntity(Message.class)
                .setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize);
        return query.list();
    }
    
    @Override
    public List<Message> listStandByProcessOfLimit(Integer pageNo, Integer pageSize)
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT t_r_d.* FROM tb_rabbitmq_dlq AS t_r_d WHERE t_r_d.`MESSAGE_STATUS`='ARE_RETRY' AND t_r_d.CREATE_TIME <=DATE_SUB(NOW(),INTERVAL 10 MINUTE) order by t_r_d.CREATE_TIME ASC ");
        Query query =
            this.session()
                .createSQLQuery(sql.toString())
                .addEntity(Message.class)
                .setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize);
        return query.list();
    }
}
