package com.cdc.inventorysystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@MapperScan("{com.cdc.inventorysystem.dao,com.cdc.inventorysystem.mapper}")
//@MapperScan("com.cdc.inventorysystem.mapper")
@MapperScan("com.cdc.inventorysystem.dao")
@SpringBootApplication
public class InventorySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorySystemApplication.class, args);
        System.out.println("finish");
    }

}
