package com.gitlab.korotaeva.number.generator;

import java.util.List;

public interface Generator<T> {
    List<T> generate();
}
