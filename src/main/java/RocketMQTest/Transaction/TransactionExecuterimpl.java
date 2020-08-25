package RocketMQTest.Transaction;

import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;

import java.util.Date;

//import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
//import com.alibaba.rocketmq.client.producer.LocalTransactionState;
//import com.alibaba.rocketmq.common.message.Message;

/**
 * @Author youhuan
 * @Description 执行本地的业务逻辑，如DB操作
 * @Date 17:13  2019/12/3
 * @Param
 * @return
 **/
public class TransactionExecuterimpl implements LocalTransactionExecuter {


     /**
      * @Author youhuan
      * @Description 执行本地业务
      * @Date 18:16  2019/12/3
      * @Param [message, o]
      * @return com.alibaba.rocketmq.client.producer.LocalTransactionState
      **/
    @Override
    public LocalTransactionState executeLocalTransactionBranch(final Message message, final Object o) {
        try{

            // Order order = new Order();
            // 初始化订单数据
            // orderService.insertOrder(order);

            // 手动抛出异常
//             int a = 1 / 0;

            // 模拟执行器执行超时
//            Thread.sleep(1000000);
            System.out.println(new Date()+"===> 本地事务执行成功，发送确认消息");
        }catch (Exception e){
            System.out.println(new Date()+"===> 本地事务执行失败！！！");
            return LocalTransactionState.ROLLBACK_MESSAGE;

        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
