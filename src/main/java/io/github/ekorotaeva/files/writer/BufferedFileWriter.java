package io.github.ekorotaeva.files.writer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

@AllArgsConstructor
@Slf4j(topic = "BufferedFileWriter")
public class BufferedFileWriter {

    public static <T> void write(String filename, Iterable<T> values, char separator) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Iterator<T> iterator = values.iterator(); iterator.hasNext(); ) {
                T value = iterator.next();
                writer.append(value.toString());
                if (iterator.hasNext()) {
                    writer.append(separator);
                }
            }
        } catch (IOException e) {
            log.error("Write file error:" + e.getLocalizedMessage());
        }

    }
}
