package RocketMQTest.Transaction;

//import com.alibaba.rocketmq.client.producer.LocalTransactionState;
//import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
//import com.alibaba.rocketmq.common.message.MessageExt;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @Author youhuan
 * @Description 生产者事务监听器，用于本地执行器执行超时的时候，查询本地事务是否执行成功
 * @Date 18:10  2019/12/3
 * @Param
 * @return
 **/
public class TransactionCheckListenerImpl implements TransactionCheckListener {
    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt messageExt) {

        System.out.println("MQ服务器端回查事务消息： " + messageExt.toString());
        //由于RocketMQ迟迟没有收到消息的确认消息，因此主动询问这条prepare消息，是否正常？
        //可以查询数据库看这条数据是否已经处理

        // Order order = new Order();
        // 初始化订单数据
        // orderService.selectOrder(order);

        //        if (订单创建成功) {
        //            return LocalTransactionState.COMMIT_MESSAGE;
        //        } else if (订单创建失败) {
        //            return LocalTransactionState.ROLLBACK_MESSAGE;
        //        } else {
        //            return LocalTransactionState.UNKNOW;
        //        }

        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
