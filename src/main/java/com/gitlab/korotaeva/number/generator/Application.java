package com.gitlab.korotaeva.number.generator;


import com.gitlab.korotaeva.files.writer.BufferedFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j(topic = "Application")
public class Application {

    private static final int SIZE = 65_000_000;
    private static final long LEFT = -9_222_999_999_999_999_999L;
    private static final long RIGHT = 9_222_999_999_999_999_999L;
    private static final String FILENAME = "./tmp/1.txt";

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ss.SSS", Locale.getDefault());
        log.info("start");

        Generator<Long> generator = new RandomGenerator(SIZE, LEFT, RIGHT);

        long startTime = System.currentTimeMillis();
        List<Long> values = generator.generate();
        log.info("generating time: " + dateFormat.format(new Date(System.currentTimeMillis() - startTime)));
        log.info(values.get(values.size() - 1).toString());

        try {
            startTime = System.currentTimeMillis();
            new BufferedFileWriter().writeLongs(FILENAME, values);

        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("writing time: " + dateFormat.format(new Date(System.currentTimeMillis() - startTime)));
        log.info("total time: " + dateFormat.format(new Date(System.currentTimeMillis() - startTime)));
    }

}


