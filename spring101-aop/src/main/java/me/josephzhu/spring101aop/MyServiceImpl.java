package me.josephzhu.spring101aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MyServiceImpl implements MyService {

    @Autowired
    private DbMapper dbMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    @Metrics(ignoreException = true)
    public void insertData(boolean success){
        dbMapper.personInsertWithoutId();
        if(!success)
            dbMapper.personInsertWithId();
    }

    @Override
    @Metrics
    public List<MyBean> getData(MyBean myBean, int count, Duration delay) {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return IntStream.rangeClosed(1,count)
                .mapToObj(i->new MyBean((long)i,myBean.getName() + i, myBean.getAge(), myBean.getBalance()))
                .collect(Collectors.toList());
    }
}
