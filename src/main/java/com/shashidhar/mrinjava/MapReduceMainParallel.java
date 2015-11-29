package com.shashidhar.mrinjava;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Parallised version of item wise count M/R
 */
public class MapReduceMainParallel {

    //same group function as MapReduceMain
    static Map<String, List<Long>> groupOperation(List<Pair> list) {
        Map<String, List<Long>> groupValues = new HashMap<String, List<Long>>();
        for (Pair pair : list) {
            if (!groupValues.containsKey(pair.getKey())) {
                List<Long> values = new ArrayList<Long>();
                values.add(pair.getValue());
                groupValues.put(pair.getKey(), values);
            } else {
                groupValues.get(pair.getKey()).add(pair.getValue());
            }
        }
        return groupValues;
    }

    public static void main(String[] args) throws IOException {

        String filePath = args[0];
        //read file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        ExecutorService executor = Executors.newFixedThreadPool(10);
        String line;
        long lineNo = 0;

        //let's have two mapper instances
        final List<Pair> firstMapResult = new ArrayList<Pair>();
        final Mapper firstMapper = new Mapper();
        final List<Pair> secondMapResult = new ArrayList<Pair>();
        final Mapper secondMapper = new Mapper();

        while ((line = bufferedReader.readLine()) != null) {
            final String currentLine = line;
            final long currentLineNo = lineNo;
            //split the data
            if (lineNo < 10) {
                executor.submit(new Runnable() {
                    public void run() {
                        firstMapper.map(currentLineNo, currentLine, firstMapResult);
                    }
                });
            } else {
                executor.submit(new Runnable() {
                    public void run() {
                        secondMapper.map(currentLineNo, currentLine, secondMapResult);
                    }
                });
            }
            lineNo += 1;
        }

        executor.shutdown();
        // wait till mapper  threads are done
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {

        }
        //merge results
        firstMapResult.addAll(secondMapResult);

        Map<String, List<Long>> groupedResult = groupOperation(firstMapResult);
        List<Pair> reduceResult = new ArrayList<Pair>();
        Reducer reducer = new Reducer();
        for (Map.Entry<String, List<Long>> entry : groupedResult.entrySet()) {
            reducer.reduce(entry.getKey(), entry.getValue(), reduceResult);
        }
        System.out.println(reduceResult);
    }

}
