package com.gastonfournier.utils;

import java.util.Iterator;
import java.util.List;

public class CyclicIterator<T> implements Iterator<T> {
    private final List<T> list;
    private int index = 0;

    public CyclicIterator(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        T result = list.get(index);
        index = (index + 1) % list.size();
        return result;
    }
}
