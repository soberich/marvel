package com.example.marvel.api;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new File(URI.create("file:///Users/a:file:///Users/b")).listFiles()));
        System.out.println(Paths.get("time-service", "api").normalize().toAbsolutePath());
    }
}
