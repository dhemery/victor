package com.dhemery.victor.io;

import com.dhemery.victor.HttpException;

import java.io.*;

public class StreamReader {
    public String read(InputStream stream, String description) {
        StringBuilder builder = new StringBuilder();
        readIntoBuilder(description, readerFor(stream), builder);
        return builder.toString();
    }

    private BufferedReader readerFor(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream));
    }

    private void readIntoBuilder(String description, BufferedReader reader, StringBuilder builder) {
        String line;
        while ((line = nextLineFrom(reader, description)) != null) {
            builder.append(line);
        }
        close(reader, description);
    }

    private String nextLineFrom(BufferedReader reader, String description) {
        try {
            return reader.readLine();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot read next line from %s", description), cause);
        }
    }

    private void close(Reader reader, String description) {
        try {
            reader.close();
        } catch (IOException cause) {
            throw new HttpException(String.format("Cannot close reader for %s", description), cause);
        }
    }
}
