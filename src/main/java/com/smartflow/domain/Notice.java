package com.smartflow.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String content;

    private String createdBy;

    private LocalDateTime createdAt;
}
