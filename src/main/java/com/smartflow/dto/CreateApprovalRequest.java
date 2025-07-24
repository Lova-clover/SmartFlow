package com.smartflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateApprovalRequest {
    private String title;
    private String content;
    private String authorUsername;
    private boolean shared;
}
