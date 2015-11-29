package com.shashidhar.mrinjava;

/**
 * A class to hold (key,value) pair
 */
public class Pair implements Comparable<String> {
    private String key;
    private long value;

    public Pair(String key, long value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
       return key+ "  "+value+"\n";
    }


    public int compareTo(String o) {
        return  this.key.compareTo(o);
    }
}
