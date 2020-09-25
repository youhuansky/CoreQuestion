package RocketMQTest;


//import com.alibaba.rocketmq.client.exception.MQClientException;
//import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
//import com.alibaba.rocketmq.client.producer.SendResult;
//import com.alibaba.rocketmq.common.message.Message;
//
//
//
//public class RocketMQProTest {
//
//
//
//
//    public static void main(String[] args) throws MQClientException {
//        doRun();
//
//
//
//    }
//
//    private static void doRun() throws MQClientException {
//        //1、设置分组
//        DefaultMQProducer producer = new DefaultMQProducer("rmq-group");
//        //2、服务器集群的ip地址及端口号
//        producer.setNamesrvAddr("192.168.1.102:9876;192.168.1.103:9876");
//        //3、设置接口名称
//        producer.setInstanceName("producer");
//        //4、启动
//        producer.start();
//        try {
//
//            for (int i = 0; i < 1000000; i++) {
//                //new Message(String topic,String tags,byte[] body)
//                Message msg = new Message("my-topic", // topic 主题名称
//                        "TagA", // tag 临时值
//                        ("mytopic-"+System.currentTimeMillis()).getBytes()// body 内容
//                );
//                //投递给broker
//                SendResult sendResult = producer.send(msg);
//                System.out.println(sendResult.toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        producer.shutdown();
//    }
//
//
//}
