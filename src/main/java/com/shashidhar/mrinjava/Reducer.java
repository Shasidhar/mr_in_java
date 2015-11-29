package com.shashidhar.mrinjava;

import java.util.List;

/**
 * Reducer class for item wise counting
 */
public class Reducer {
    /**
     *
     * @param item
     * @param values
     * @param context output list
     */
   public void reduce(String item, List<Long> values, List<Pair> context) {
       long sum = 0;
       for (long value : values) {
           sum  += value;
       }
       context.add(new Pair(item,sum));
   }
}
