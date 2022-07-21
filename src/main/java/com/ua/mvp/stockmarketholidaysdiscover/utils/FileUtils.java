package com.ua.mvp.stockmarketholidaysdiscover.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

@Component
public class FileUtils {//TODO cover by tests

    public boolean fileWasModified(File file, long fileLastModified) {
        long lastModified = file.lastModified();
        return fileLastModified != lastModified;
    }

    public File getFileWithUncheckedException(String path) {
        File file = null;
        try {
            file = getFile(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return file;
    }

    public File getFile(String path) throws IOException {
        File file = Path.of(path).toFile();
        boolean canRead = file.canRead();
        if (canRead) {
            return file;
        } else {
            throw new IOException("cannot read file");
        }
    }

}
