package ru.li.java.basic.http.sever;

import java.util.Arrays;

public class MainApp {
    // Домашнее задание:
    // - Реализуйте возможность указания статус кода ответа (404)
    // - Реализуйте возможность добавления в тело http ответа JSON объекта
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        HttpServer httpServer = new HttpServer(Integer.parseInt(String.valueOf(System.getProperties().getOrDefault("port", 8189))));
        httpServer.start();
    }
}
