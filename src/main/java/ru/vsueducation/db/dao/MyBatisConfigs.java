package ru.vsueducation.db.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class MyBatisConfigs {

    private static SqlSessionFactory sqlSessionFactory = null;

    public static SqlSessionFactory getSqlSessionFactory() {
        if(sqlSessionFactory == null) {
            try {
                sqlSessionFactory =  new SqlSessionFactoryBuilder()
                        .build(Resources.getResourceAsReader("mybatis-config.xml"));
                return sqlSessionFactory;
            } catch (IOException e) {
                throw new RuntimeException();
            }
        } else {
            return sqlSessionFactory;
        }
    }
}
