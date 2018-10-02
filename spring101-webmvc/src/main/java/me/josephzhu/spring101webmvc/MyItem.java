package me.josephzhu.spring101webmvc;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MyItem {
    private String name;
    private Integer price;
}
