package com.smartflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Document {
    private Long id;
    private String title;
    private String author;
    private String status;
    private boolean shared;
    private String content;

    public Document(Long id, String title, String author, String status, boolean shared, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.shared = shared;
        this.content = content;
    }
}
