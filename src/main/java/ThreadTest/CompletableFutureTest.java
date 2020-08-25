package ThreadTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 场景：100个线程，每个线程打印个数字
        List<String> messages = Arrays.asList("asdf", "adsf2", "1234f");
        List<CompletableFuture<String>> collects = messages.stream().map(msg -> CompletableFuture.completedFuture(msg)
                .thenApply(msg2 -> {
                    System.out.println(msg2);
                    return msg2.toUpperCase();
                })).collect(Collectors.toList());
        System.out.println("---------------------------");
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(collects.toArray(new CompletableFuture[collects.size()]));

        voidCompletableFuture.thenApply(s -> {
            System.out.println(s);
            return collects.stream().map(CompletableFuture::join).collect(Collectors.toList());
        }).whenComplete((s, v) -> {
            System.out.println(s);
        });
//        voidCompletableFuture.join();
//        m7_thenApply();

//        m1();
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private static void m7_thenApply() {
        System.out.println(CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("----1");
            return 1;
        }).thenApply(f -> {
            System.out.println(Thread.currentThread().getName());
//            int age = 10 / 0;
            System.out.println("----2");
            return f + 2;
        }).thenApply(f -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("----3");
            return f + 3;
        }).join());
    }

    /**
     * 获得结果和触发计算
     */
    public static void m1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " youhuan");
            return 533;
        });
        System.out.println(Thread.currentThread().getName() + " youhuan2");
        //1 不见不散
        //System.out.println(future.get());

        //2 保护，过时不候---暴躁版，出异常
        //System.out.println(future.get(1, TimeUnit.SECONDS));

        //暂停几秒钟线程
        //try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        //3 立刻就要，有这种值，后续请按照约定的折中值去MQ里面消息重试
        //System.out.println(future.getNow(9999));

        //4  不见不散2--join，无checked exception
        //future.join();

        //5 不见不散3---优化，可中断get，立刻脱身切没有异常，只有true、false
        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(future.complete(9999) + "\t" + future.get());
    }

    public static void m6_thenCompose() {
        System.out.println(CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("1111");
            return 11;
        }).thenCompose(f -> {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2222");
                return f + 3;
            });
        }).thenCompose(f -> {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("3333");
                return f + 1;
            });
        }).join());

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(9);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m5_applyToEither() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in ");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in ");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        });

        CompletableFuture<Integer> thenCombineResult = completableFuture1.applyToEither(completableFuture2, f -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in ");
            return f + 1;
        });

        System.out.println(Thread.currentThread().getName() + "\t" + thenCombineResult.get());
    }

    public static void m4_thenCombine() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 3;
        });

        CompletableFuture<Integer> result = future1.thenCombine(future2, (r1, r2) -> {
            return r1 + r2;
        });

        System.out.println(result.get());

        //======================================
        CompletableFuture<Integer> thenCombineResult = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in 1");
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in 2");
            return 20;
        }), (x, y) -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in 3");
            return x + y;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in 4");
            return 30;
        }), (a, b) -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in 5");
            return a + b;
        });
        System.out.println("-----主线程结束，END");
        System.out.println(thenCombineResult.get());
    }

    public static void m4_thenAccept() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return 1;
        }).thenApply(f -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return f + 2;
        }).thenApply(f -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return f + 3;
        }).thenApply(f -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "---come in");
            return f + 4;
        }).thenAccept(r -> System.out.println(r));
    }

    public static void m3_apply() {
        //当一个线程依赖另一个线程时用 handle 方法来把这两个线程串行化,
        // 异常情况：有异常也可以往下一步走，根据带的异常参数可以进一步处理
        CompletableFuture.supplyAsync(() -> {
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("111");
            return 1024;
        }).handle((f, e) -> {
            int age = 10 / 0;
            System.out.println("222");
            return f + 1;
        }).handle((f, e) -> {
            System.out.println("333");
            return f + 1;
        }).whenCompleteAsync((v, e) -> {
            System.out.println("*****v: " + v);
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        System.out.println("-----主线程结束，END");
    }

    public static void m2_whenComplete() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 533;
        }).whenComplete((v, e) -> {
            System.out.println("whenComplete: " + Thread.currentThread().getName());
            if (e == null) {
                System.out.println("******result: " + v + "\t" + Thread.currentThread().getName());
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }
}