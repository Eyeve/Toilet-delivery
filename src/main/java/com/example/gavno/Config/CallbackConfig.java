package com.example.gavno.Config;

public final class CallbackConfig {

    public static final String TOILET_PAPER = "TOILET_PAPER";
    public static final String TOILET_PAPER_ADD = addPostfix(TOILET_PAPER);
    public static final String TOILET_PAPER_DELETE = deletePostfix(TOILET_PAPER);
    public static final String INSTANT_NOODLES = "INSTANT_NOODLES";
    public static final String INSTANT_NOODLES_ADD = addPostfix(INSTANT_NOODLES);
    public static final String INSTANT_NOODLES_DELETE = deletePostfix(INSTANT_NOODLES);
    public static final String MAKE_ORDER = "MAKE_ORDER";
    public static final String BACK = "BACK";
    public static final String NEXT = "NEXT";
    public static final String PREVIOUS = "PREVIOUS";

    private CallbackConfig() {

    }

    public static String addPostfix(final String callbackData) {
        return String.format("%s_ADD", callbackData);
    }

    public static String deletePostfix(final String callbackData) {
        return String.format("%s_DELETE", callbackData);
    }

}
