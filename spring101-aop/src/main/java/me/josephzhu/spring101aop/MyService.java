package me.josephzhu.spring101aop;

import java.time.Duration;
import java.util.List;

public interface MyService {
    void insertData(boolean success);
    List<MyBean> getData(MyBean myBean, int count, Duration delay);
}
