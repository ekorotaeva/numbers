package com.gitlab.korotaeva.research;

import com.gitlab.korotaeva.files.reader.FileChannelReader;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class Applcation {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public static void main(String[] args) {

        Path path = Paths.get("./tmp/1.txt");

        String string = "9095147463233753086";

        byte[] asByteArray = string.getBytes(CHARSET);
        log.info(String.format("Searching %s (size: %d bytes)", new String(asByteArray, CHARSET), asByteArray.length));

        FileChannelReader.read(path, asByteArray);

    }
}
