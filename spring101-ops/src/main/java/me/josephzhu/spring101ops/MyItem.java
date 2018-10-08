package me.josephzhu.spring101ops;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MyItem {
    private String name;
    private Integer price;
}
