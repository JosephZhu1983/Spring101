package me.josephzhu.spring101data;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvestOrder {
    @Id
    long id;
    long userId;
    String userName;
    long projectId;
    String projectName;
    BigDecimal amount;
    int status;
    @CreatedDate
    Date createdAt;
    @LastModifiedDate
    Date updatedAt;
}
