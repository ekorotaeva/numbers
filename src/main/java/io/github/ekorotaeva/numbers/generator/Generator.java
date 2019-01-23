package io.github.ekorotaeva.numbers.generator;

import java.util.List;

public interface Generator<T> {
    List<T> generate();
}
