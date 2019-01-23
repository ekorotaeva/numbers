package io.github.ekorotaeva.numbers.generator;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@AllArgsConstructor
public class IntegerRandomGenerator implements Generator<Integer> {

    private int size;
    private int min;
    private int max;

    @Override
    public List<Integer> generate() {
        return new Random().ints(this.size, this.min, this.max)
                .boxed()
                .collect(Collectors.toList());
    }
}
