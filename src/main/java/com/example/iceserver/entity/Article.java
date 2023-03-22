package com.example.iceserver.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String articleName;

    private String articleContent;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    //    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    //    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
