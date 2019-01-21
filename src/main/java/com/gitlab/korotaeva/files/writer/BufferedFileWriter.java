package com.gitlab.korotaeva.files.writer;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;

@Slf4j(topic = "BufferedFileWriter")
public class BufferedFileWriter {

    public void writeLongs(String filename, Iterable<Long> values) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        for (Iterator<Long> iterator = values.iterator(); iterator.hasNext(); ) {
            Long value = iterator.next();
            writer.append(value.toString());
            if (iterator.hasNext()) {
                writer.append("\n");
            }
        }

        writer.close();

    }
}
