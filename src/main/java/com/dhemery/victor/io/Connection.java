package com.dhemery.victor.io;

public interface Connection<R> {
    public void write(String body);
    public R response();
}
