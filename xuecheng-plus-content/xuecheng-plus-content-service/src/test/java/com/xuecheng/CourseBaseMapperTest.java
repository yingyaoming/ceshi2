package com.xuecheng;

import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.impl.CourseBaseInfoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
public class CourseBaseMapperTest {

    @Resource
    private CourseBaseMapper courseBaseMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testCourseBaseMapper(){

        redisTemplate.delete("不存在");
        System.out.println("成功了");
    }

    @Test
    public void test1(){
        CourseBaseInfoServiceImpl baseInfoService = new CourseBaseInfoServiceImpl();
        long begin = System.currentTimeMillis();

        String test1 = baseInfoService.test1();
        //String test2 = baseInfoService.test2();
        String test3 = baseInfoService.test3();
        long end = System.currentTimeMillis();
        System.out.println("程序执行花费了" + (end - begin));
        //System.out.println(test1 + test2 + test3);
    }

    @Test
    public void test2()  {
        CourseBaseInfoServiceImpl baseInfoService = new CourseBaseInfoServiceImpl();
        long begin = System.currentTimeMillis();

        CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> baseInfoService.test1());
        CompletableFuture<Void> runFuture2 = CompletableFuture.runAsync(() -> {
            baseInfoService.test2();
        }).whenComplete((result, ex) -> {
            if (ex != null) {
                // 记录异常信息
                System.err.println("test2出错了: " + ex.getMessage());
            } else {
                // 正常完成后的处理
                System.out.println("test2 completed successfully");
            }
        });
        CompletableFuture<String> stringCompletableFuture3 = CompletableFuture.supplyAsync(() -> baseInfoService.test3());

        CompletableFuture.runAsync(() -> baseInfoService.test1());
        CompletableFuture.allOf(stringCompletableFuture1, stringCompletableFuture3);
        try {

            System.out.println(stringCompletableFuture3.get());
            System.out.println(stringCompletableFuture1.get());

        } catch (Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());
            System.out.println("异常已经捕获");
            throw new RuntimeException("哈哈哈哈");
        }

        long end = System.currentTimeMillis();
        System.out.println("程序执行花费了" + (end - begin));
        //System.out.println(test1 + test2 + test3);
    }

    /*@Test
    public void test3() throws ExecutionException, InterruptedException {
        CourseBaseInfoServiceImpl baseInfoService = new CourseBaseInfoServiceImpl();
        long begin = System.currentTimeMillis();

        CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(() -> baseInfoService.test1());
        CompletableFuture<String> stringCompletableFuture2 = CompletableFuture.supplyAsync(() -> baseInfoService.test2());

        CompletableFuture<String> stringCompletableFuture = stringCompletableFuture2.thenApply((item) -> {
            System.out.println("方法2执行了");
            System.out.println(item);
            return baseInfoService.test3();
        });
        CompletableFuture.allOf(stringCompletableFuture1, stringCompletableFuture);

        System.out.println(stringCompletableFuture1.get());
        System.out.println(stringCompletableFuture.get());
        long end = System.currentTimeMillis();
        System.out.println("程序执行花费了" + (end - begin));
        //System.out.println(test1 + test2 + test3);
    }

    //利用事件机制实现对任务的复杂编排
    @Test
    public void test4() throws ExecutionException, InterruptedException {
        CourseBaseInfoServiceImpl baseInfoService = new CourseBaseInfoServiceImpl();
        long begin = System.currentTimeMillis();

        //阻塞的
        CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.completedFuture(baseInfoService.test1());
        System.out.println(stringCompletableFuture1.get());

        CompletableFuture<String> stringCompletableFuture2 = CompletableFuture.supplyAsync(() -> baseInfoService.test2());
        CompletableFuture<String> stringCompletableFuture3 = CompletableFuture.supplyAsync(() -> baseInfoService.test3());

        CompletableFuture.allOf(stringCompletableFuture2, stringCompletableFuture3);

        System.out.println(stringCompletableFuture2.get());
        System.out.println(stringCompletableFuture3.get());
        long end = System.currentTimeMillis();
        System.out.println("程序执行花费了" + (end - begin));
        //System.out.println(test1 + test2 + test3);
    }*/
}
