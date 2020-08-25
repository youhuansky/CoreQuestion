package RocketMQTest;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class RocketMQConTest {


    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("rmq-group");
        consumer.setNamesrvAddr("192.168.1.102:9876;192.168.1.103:9876");
        consumer.setInstanceName("consumer");
        consumer.subscribe("my-topic", "TagA");

        //观察者设计模式,监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {
                    long offset = msgs.get(0).getQueueOffset();
                    String maxOffset = msgs.get(0).getProperty(MessageConst.PROPERTY_MAX_OFFSET);
                    long diff = Long. parseLong(maxOffset) - offset;

                    System.out.println("----------------------------");
                    System.out.println("+++++++++++"+diff);
                    System.out.println(msg.getQueueId());
                    System.out.println(msg.getQueueOffset());
                    System.out.println(msg.getCommitLogOffset());
                    System.out.println(msg.getMsgId() + "---" + new String(msg.getBody()));
                    System.out.println("%%%%%%%%%%%%%%%%%%%%"+msg.getKeys());
                    System.out.println("----------------------------");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //返回值表示消费状态   1.消费成功   2.消费失败
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer Started.");

    }


}
