package com.yunki.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String document;
    private String phone;
    private String email;
    private String amount;
    private String purse;
}