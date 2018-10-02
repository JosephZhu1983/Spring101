package me.josephzhu.spring101webmvc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse<T> {
    T data;
    boolean success;
    String message;
    String sign;
}
