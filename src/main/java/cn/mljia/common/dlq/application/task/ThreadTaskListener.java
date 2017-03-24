package cn.mljia.common.dlq.application.task;

/**
 * 
 * @ClassName: ThreadTaskListener
 * @Description: TODO
 * @author: mljia.cn-Marker-李云龙
 * @date: 2017年2月28日 下午3:29:51
 */
public interface ThreadTaskListener
{
    /**
     * @Title: success
     * @Description: TODO 执行成功后信息回调监听
     * @return
     * @throws Exception
     * @return: String
     */
    public void success(TaskResult result);
    
    /**
     * @Title: success
     * @Description: TODO 执行失败后信息回调监听
     * @return
     * @throws Exception
     * @return: String
     */
    public void error(TaskResult result);
}
