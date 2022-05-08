package com.naman.enterprise.integration.apcahe.camel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DatabaseService {

    List<String> dataList= Arrays.asList("one","two","three","four");

    public Object insertData(String data){
        try{
            return dataList.add(data);
        }catch (Exception ex){
            log.error("Exception caught while insertIng data:{}",ex);
        }
        return "Exception caught";
    }

    public List<String> getData(){
        return dataList;
    }
}
