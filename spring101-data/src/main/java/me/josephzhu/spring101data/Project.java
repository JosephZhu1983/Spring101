package me.josephzhu.spring101data;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Project {
    @Id
    long id;
    String name;
    BigDecimal totalAmount;
    BigDecimal remainAmount;
    int status;
    @CreatedDate
    Date createdAt;
    @LastModifiedDate
    Date updatedAt;
}
