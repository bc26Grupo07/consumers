package com.yunki.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "bootcoin")
public class Bootcoin {
    private Long id;
    private double purchase;
    private double sale;
}