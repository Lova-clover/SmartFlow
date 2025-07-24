package com.smartflow.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String status;
    @Setter
    private String authorUsername;
    private String approvedBy;
    private String rejectedBy;
    private boolean shared;

}
