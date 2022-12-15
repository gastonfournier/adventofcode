package com.gastonfournier.utils;

import java.util.List;

public interface Solution<T, U> {
    T part1(List<String> input);
    U part2(List<String> input);
}
