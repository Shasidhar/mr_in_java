package com.shashidhar.mrinjava;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main program to run Map/Reduce for item wise count
 */
public class MapReduceMain {
    /**
     * Grouping utility for pair objects
     * @param list
     * @return map with grouped values
     */
    static Map<String,List<Long>> groupOperation (List<Pair> list) {
       Map<String,List<Long>> groupValues = new HashMap<String,List<Long>>();
       for (Pair pair : list) {
         if(!groupValues.containsKey(pair.getKey())) {
            List<Long> values = new ArrayList<Long>();
            values.add(pair.getValue());
            groupValues.put(pair.getKey(),values);
         }
         else {
           groupValues.get(pair.getKey()).add(pair.getValue());
         }
       }
       return groupValues;
   }

   public static void main(String[] args) throws IOException {
       String filePath = args[0];
       BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
       String line;
       long lineNo=0;
       List<Pair> mapResult = new ArrayList<Pair>();
       //create a mapper instance
       Mapper mapper = new Mapper();
       while ((line=bufferedReader.readLine())!=null) {
           //for every line call mapper
          mapper.map(lineNo,line,mapResult);
          lineNo+=1;
       }
       Map<String,List<Long>> groupedResult = groupOperation(mapResult);
       List<Pair> reduceResult = new ArrayList<Pair>();
       Reducer reducer = new Reducer();
       for ( Map.Entry<String,List<Long>> entry :  groupedResult.entrySet()){
           reducer.reduce(entry.getKey(), entry.getValue(), reduceResult);
       }
       System.out.println(reduceResult);

   }

}
