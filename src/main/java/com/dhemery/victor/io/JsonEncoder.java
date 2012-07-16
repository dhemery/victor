package com.dhemery.victor.io;

public interface JsonEncoder {
    <T> T fromJson(String json, Class<T> resultType);
    String toJson(Object object);
}
