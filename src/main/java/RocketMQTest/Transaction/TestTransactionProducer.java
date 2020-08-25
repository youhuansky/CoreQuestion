package RocketMQTest.Transaction;

//import com.alibaba.rocketmq.client.exception.MQClientException;
//import com.alibaba.rocketmq.client.producer.SendResult;
//import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
//import com.alibaba.rocketmq.common.message.Message;

/**
 * @Author youhuan
 * @Description 消息事务生产者
 * @Date 18:12  2019/12/3
 * @Param
 * @return
 **/
public class TestTransactionProducer {
    public static void main(String[] args) {

//        //事务消息生产者
//        TransactionMQProducer producer = new TransactionMQProducer("transactionProducerGroup");
//        //MQ服务器地址
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        //本地事务执行器
//        TransactionExecuterimpl executerimpl = null;
//        try {
//            //启动生产者
//            producer.start();
//
//            // 创建一个业务执行器，用于执行本地业务逻辑，可以传入业务信息
//            executerimpl = new TransactionExecuterimpl();
//            // executerimpl.setOrder();
//
//            // 事务回查监听器
//            TransactionCheckListenerImpl checkListener = new TransactionCheckListenerImpl();
//            // 注册事务回查监听
//            producer.setTransactionCheckListener(checkListener);
//
//
//            Message msg1 =
//                    new Message("TransactionTopic", "test3", "KEY1",
//                            ("orderId"+ System.currentTimeMillis()).getBytes());
//
//
//            SendResult sendResult = producer.sendMessageInTransaction(msg1, executerimpl, null);
//            System.out.println(new Date() + "msg1"+sendResult);
//
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//        producer.shutdown();
//    }
    }
}
