package com.gitlab.korotaeva.number.generator;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RandomGenerator implements Generator<Long> {

    private long size;
    private long left;
    private long right;

    @Override
    public List<Long> generate() {
        return new Random().longs(this.size, this.left, this.right)
                .boxed()
                .collect(Collectors.toList());
    }
}
