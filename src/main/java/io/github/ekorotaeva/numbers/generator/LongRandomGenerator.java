package io.github.ekorotaeva.numbers.generator;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
public class LongRandomGenerator implements Generator<Long> {

    private long size;
    private long min;
    private long max;

    @Override
    public List<Long> generate() {
        return new Random().longs(this.size, this.min, this.max)
                .boxed()
                .collect(Collectors.toList());
    }
}
