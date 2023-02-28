package com.example.iceserver.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 公共字段自动填充
 */
@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
    /**
     * 插入操作自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        // 从ThreadLocal 内获取当前登录用户id
//        Long empID = BaseContext.getCurrentId();
//        metaObject.setValue("createUser",new Long(empID));
//        metaObject.setValue("updateUser",new Long(empID));
    }

    /**
     * 更新操作自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",LocalDateTime.now());
        // 从ThreadLocal 内获取当前登录用户id
//        Long empID = BaseContext.getCurrentId();
//        metaObject.setValue("updateUser",new Long(empID));
    }
}
