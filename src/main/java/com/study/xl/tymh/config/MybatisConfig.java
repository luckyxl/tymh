package com.study.xl.tymh.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @ClassName MybatisConfig
 * @Description TODO
 * @Author xule
 * @Date 2019/8/7 11:00
 * @Version 1.0
 **/
@Configuration
@MapperScan("com.study.xl.tymh.dao")
public class MybatisConfig {

    @Resource
    private DataSource dataSource;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }


}
