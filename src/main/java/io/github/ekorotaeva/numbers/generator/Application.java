package io.github.ekorotaeva.numbers.generator;


import io.github.ekorotaeva.files.reader.FileChannelReader;
import io.github.ekorotaeva.files.writer.BufferedFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j(topic = "Application")
public class Application {

    private static final int SIZE = 65_000_000;
    private static final long MAX_VALUE = 9_222_999_999_999_999_999L;
    private static final long MIN_VALUE = -9_222_999_999_999_999_999L;
    private static final String FILENAME = "./1.txt";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS", Locale.getDefault());
        log.info("start generation ");

        /* Generation */

        Generator<Long> generator = new LongRandomGenerator(SIZE, MIN_VALUE, MAX_VALUE);
        AtomicLong startTime = new AtomicLong(System.currentTimeMillis());
        List<Long> values = generator.generate();

        log.info("generating time: " + dateFormat.format(new Date(System.currentTimeMillis() - startTime.get())));

        String last = values.get(values.size() - 1).toString();
        log.info("last value:" + last);

        try {
            startTime.set(System.currentTimeMillis());
            BufferedFileWriter.write(FILENAME, values, '\n');

        } catch (Exception e) {
            log.error(String.format("Write File error %s", e.getLocalizedMessage()));
        }

        log.info("writing time: " + dateFormat.format(new Date(System.currentTimeMillis() - startTime.get())));

        /* Searching */

        byte[] asByteArray = last.getBytes(CHARSET);
        log.info(String.format("Searching %s (size: %d bytes)", new String(asByteArray, CHARSET), asByteArray.length));

        long searchTime = System.currentTimeMillis();

        FileChannelReader.read(Paths.get(FILENAME), asByteArray);

        log.info("search time: " + dateFormat.format(new Date(System.currentTimeMillis() - searchTime)));
        log.info("total time: " + dateFormat.format(new Date(System.currentTimeMillis() - startTime.get())));
    }

}


