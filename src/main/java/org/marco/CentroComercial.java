package org.marco;

import java.util.concurrent.Semaphore;

public class CentroComercial {
    private static final int NUM_COCHES = 3;
    private static final int PLAZAS_PARKING = 2;

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(PLAZAS_PARKING);

        for (int i = 0; i < NUM_COCHES; i++) {
            Coche coche = new Coche(semaphore, 1, false);
            coche.start();
            try {
                coche.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
