package com.shashidhar.mrinjava;

import java.util.List;

/**
 * Mapper class for item wise count
 **/
public class Mapper {
    /**
     * @param lineNo
     * @param line
     * @param context output list
     */
    public void map(Long lineNo, String line,List<Pair> context) {
       String columns[] = line.split(",");
       String item = columns[2];
       context.add(new Pair(item,1));
    }
}
