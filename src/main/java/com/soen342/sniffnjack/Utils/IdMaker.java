package com.soen342.sniffnjack.Utils;

public class IdMaker {
    private static Long id = 0L;

    public static Long getId() {
        return ++id;
    }
}
