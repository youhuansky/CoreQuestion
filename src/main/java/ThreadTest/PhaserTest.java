package ThreadTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PhaserTest {

    public static void main(String[] args) {

//        Phaser phaser = new Phaser();
////        phaser.register();
////        phaser.register();
////        phaser.register();
////        for (int i = 0; i < 3; i++) {
////            final int num = i;
////            new Thread(() -> {
////                System.out.println("start" + num);
////                try {
////                    Thread.sleep(1000L);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                System.out.println("end" + num);
////                phaser.arrive();
////            }).start();
////        }
////        phaser.awaitAdvance(0);
////        System.out.println("走完了");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("2021-01-15 00:00:00", dateTimeFormatter).plusDays(-1);
        System.out.println(localDateTime);

    }
}
