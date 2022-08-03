package com.yunki.entity;

import lombok.Data;

@Data
public class Yunki {
    private Long id;
    private Long userId;
    private Long secondUserId;
    private String transaction;
    private String payMode;
    private double amount;
    private double amountBootcoin;
    private String phone;
    private String email;
}