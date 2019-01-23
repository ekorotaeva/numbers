package io.github.ekorotaeva.numbers.search.service;


import io.github.ekorotaeva.files.reader.FileChannelReader;
import io.github.ekorotaeva.files.writer.BufferedFileWriter;
import io.github.ekorotaeva.numbers.generator.Generator;
import io.github.ekorotaeva.numbers.generator.IntegerRandomGenerator;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class NumberSearchService {

    private static final int SIZE = 100_000_000;
    private static final int MIN_VALUE = -2147483648;
    private static final int MAX_VALUE = 2147483647;
    private static final String FILENAME = "./1.txt";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    @PostConstruct
    public void initData() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS", Locale.getDefault());
        for (int i=1; i<22; i++) {
            log.info(String.format("start generation # %d.", i));

            /* Generation */

            Generator<Integer> generator = new IntegerRandomGenerator(SIZE, MIN_VALUE, MAX_VALUE);
            AtomicLong startTime = new AtomicLong(System.currentTimeMillis());
            List<Integer> values = generator.generate();

            log.info("generating time: " + dateFormat.format(new Date(System.currentTimeMillis() - startTime.get())));

            String last = values.get(values.size() - 1).toString();
            log.info("last value:" + last);

            try {
                startTime.set(System.currentTimeMillis());
                BufferedFileWriter.write(String.format("./%d.txt", i), values, '\n');

            } catch (Exception e) {
                log.error(String.format("Write File error %s", e.getLocalizedMessage()));
            }

            log.info("writing time: " + dateFormat.format(new Date(System.currentTimeMillis() - startTime.get())));

            /* Searching */

            byte[] asByteArray = last.getBytes(CHARSET);
            log.info(String.format("Searching %s (size: %d bytes)", new String(asByteArray, CHARSET), asByteArray.length));

            long searchTime = System.currentTimeMillis();

            FileChannelReader.read(Paths.get(String.format("./%d.txt", i)), asByteArray);

            log.info("search time: " + dateFormat.format(new Date(System.currentTimeMillis() - searchTime)));
            log.info("total time: " + dateFormat.format(new Date(System.currentTimeMillis() - startTime.get())));
        }
    }

    public boolean search(int number) {

        try {
            final byte[] bytes = String.valueOf(number).getBytes(Charset.forName("UTF-8"));
            return FileChannelReader.read(Paths.get("./1.txt"), bytes);
        }
        catch (Exception err) {
            log.error("Error "+err+" has been thrown at "+ Arrays.stream(err.getStackTrace()).map(Object::toString).collect(Collectors.joining(" in \n")));
        }

        return false;
    }
}