package com.example.iceserver.utils;

import cn.hutool.core.lang.UUID;

public class StringUtils {
    /**
     * @Description: 生成唯一图片名称
     * @Param: fileName
     * @return: 云服务器fileName
     */
    public static String getRandomImgName(String fileName) {

        int index = fileName.lastIndexOf(".");

        if ((fileName == null || fileName.isEmpty()) || index == -1){
            throw new IllegalArgumentException();
        }
        // 获取文件后缀
        String suffix = fileName.substring(index);
        // 生成UUID
        String uuid = UUID.randomUUID().toString().replace("-","").substring(5,17);
        // 生成上传至云服务器的路径
        String path = uuid + suffix;  //code/duck/ 就是你七牛云上面新建的
        return path;
    }
}
