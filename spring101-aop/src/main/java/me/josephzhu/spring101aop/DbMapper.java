package me.josephzhu.spring101aop;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DbMapper {
    @Select("SELECT COUNT(0) FROM PERSON")
    int personCount();

    @Insert("INSERT INTO PERSON (NAME, AGE, BALANCE) VALUES ('zhuye', 35, 1000)")
    void personInsertWithoutId();

    @Insert("INSERT INTO PERSON (ID, NAME, AGE, BALANCE) VALUES (1,'zhuye', 35, 1000)")
    void personInsertWithId();

    @Select("SELECT * FROM PERSON")
    List<MyBean> getPersonList();

}
