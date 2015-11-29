package com.shashidhar.mrinjava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *  Java code for calculating item wise count
 */
public class SimpleJava {
    public static void main(String[] args) throws IOException {
        String filePath = args[0];
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line;
        Map<String,Long> countValues = new HashMap<String,Long>();
        while ((line=bufferedReader.readLine())!=null) {
            String columns[] = line.split(",");
            String item = columns[2];
            if(!countValues.containsKey(item)){
              countValues.put(item,1L);
            } else {
               long currentCount = countValues.get(item);
               countValues.put(item,currentCount+1);
            }
        }
        System.out.println(countValues);
    }
}
