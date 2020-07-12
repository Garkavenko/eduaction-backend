package ru.vsueducation.server.utils;

import com.google.gson.Gson;

public class NetworkError {
    private static class Error {
        final String message;
        final int code;

        Error(final String message, final Integer code) {
            this.message = message;
            this.code = code;
        }
    }
    public static String toJson(final String message, final int code) {
        final Gson gson = new Gson();
        return gson.toJson(new Error(message, code));
    }
}
