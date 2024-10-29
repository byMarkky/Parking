package org.marco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(10);

        List<Coche> coches = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            coches.add(new Coche(semaphore, i, false));
        }

        coches.forEach((coche) -> {
            coche.start();
            try {
                coche.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }
}