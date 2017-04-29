package com.freeman.codeoasistask;

/**
 * Created by Freeman on 28.04.2017.
 */

public class DatabaseHandlerSingleton {
    private static final DatabaseHandlerSingleton ourInstance = new DatabaseHandlerSingleton();

    public static DatabaseHandlerSingleton getInstance() {
        return ourInstance;
    }

    private DatabaseHandlerSingleton() {
    }
}
