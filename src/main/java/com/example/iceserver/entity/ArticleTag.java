package com.example.iceserver.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleTag  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long articleId;

    private String tagsId;
}
