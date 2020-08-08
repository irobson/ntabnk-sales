package com.ntabnk.sales.application.converters;

public interface LineConverter<T> {
    T convert(String[] data);
}
