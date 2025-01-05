package com.xuecheng.media;

import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Mr.M
 * @version 1.0
 * @description 测试minio上传文件、删除文件、查询文件
 * @date 2022/10/13 14:42
 */
public class MinIOTest {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.65:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();


    @Test
    public void upload() {
        try {
            boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket("helloworld").build());
            System.out.println("开始创建");
            if (!b) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("helloworld").build());
            }
            System.out.println("开始创建");
        } catch (Exception e) {
            throw new RuntimeException("检查桶是否存在失败!", e);
        }


        try {
            String fileName = "C:\\Users\\Administrator.DESKTOP-JQIN1EP\\Desktop\\test.xlsx";
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket("helloworld")
                    .object("1.xlsx")//同一个桶内对象名不能重复
                    .filename(fileName)
                    .build();
            //上传
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传成功了");
        } catch (Exception e) {
            System.out.println("上传失败");
        }


    }

    //删除文件
    @Test
    public void delete() {

        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket("testbucket").object("1.xlsx").build();
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
        }

    }

    //查询文件
    @Test
    public void getFile() {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("1.xlsx").build();
        File file = new File("D:\\develop\\ceshi23");
        file.mkdirs();
        String filePath = file.getAbsolutePath() + "\\" + "1.xlsx";
        try (
                FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
                FileOutputStream outputStream = new FileOutputStream(filePath);
        ) {

            if (inputStream != null) {
                System.out.println("开始了");
                IOUtils.copy(inputStream, outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace(); // 打印详细的异常信息
            throw new RuntimeException("下载文件失败!", e);
        }

    }

}