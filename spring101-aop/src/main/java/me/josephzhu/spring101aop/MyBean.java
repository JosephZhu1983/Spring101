package me.josephzhu.spring101aop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBean {
    private Long id;
    private String name;
    private Integer age;
    private BigDecimal balance;
}
