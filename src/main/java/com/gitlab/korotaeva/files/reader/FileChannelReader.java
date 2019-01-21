package com.gitlab.korotaeva.files.reader;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FileChannelReader {

    private static final long BLOCK_SIZE = 2 * 1024 * 1024 * 1024L; // 2Gb
    private static final List<Character> separators = Arrays.asList('\r', '\n', ' ', ','); //

    private FileChannelReader() {
    }

    public static void read(Path path, byte[] searchable) {

        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            final long length = channel.size();
            log.info(String.format("size of %s: %d bytes", path, length));

            int pos = 0;

            while (pos < length) {
                int mapSize = (int) Math.min(BLOCK_SIZE + searchable.length, length - pos);
                // different limits depending on whether we are the last mapped segment.
                long limit = (mapSize - searchable.length + 1);

                MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, pos, mapSize);
                log.info(String.format("Searching in block %d-%d", pos, pos + mapSize));

                for (int i = 0; i < limit; i++) {
                    final char symbol = (char) buffer.get(i);

                    // check the first symbol of searchable data and match the content
                    if (symbol == searchable[0] && tryMatch(buffer, i, searchable)) {
                        log.info(String.format("Founded searchable at position: %d", i));
                        return;
                    }
//                    else {
//                        log.debug(String.format("Position %d. Skipped symbol : %s", i, symbol));
//                    }
                }
                pos += mapSize;
            }
        } catch (IOException e) {
            log.error(String.format("File reading error: %s", e.toString()));
        }
    }

    private static boolean tryMatch(MappedByteBuffer buffer, int pos, byte[] searchable) {
//        log.debug("Matching of: "+ new String(searchable));
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < searchable.length; i++) {
            byte symbol = buffer.get(pos + i);
            word.append((char) symbol);
//            log.debug("Symbols met (" + (i + 1) + "): " + word);
            if (separators.contains((char) symbol) || symbol != searchable[i]) {
//                log.debug(String.format("Word \"%s\" doesn't match for searchable content", word));
                return false;
            }
        }

        log.debug("Word matched: " + word);
        int capacity = pos + searchable.length;
        return capacity == buffer.capacity() || // Word matched in all buffer block.
                separators.contains((char) buffer.get(capacity)); // Symbol is a separator, so word matched.
    }

}

