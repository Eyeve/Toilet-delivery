package com.example.gavno;

import lombok.AllArgsConstructor;

import java.io.PrintStream;

@AllArgsConstructor
public class BotLogger {

    private static final String INFO_FORMAT = "[i] %s\n";
    private static final String ERROR_FORMAT = "[!] ERROR: %s\n";
    private static final String CRITICAL_FORMAT = "[CRITICAL] %s [CRITICAL]\n";
    private final PrintStream out;

    public void info(final String message) {
        printlnLog(INFO_FORMAT, message);
    }
    public void error(final String message) {
        printlnLog(ERROR_FORMAT, message);
    }
    public void critical(final String message) {
        printlnLog(CRITICAL_FORMAT, message);
    }
    public void printlnLog(final String format, final String message) {
        out.printf(format, message);
    }
}
