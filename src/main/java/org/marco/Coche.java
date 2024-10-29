package org.marco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Coche extends Thread {

    static final Logger logger = LoggerFactory.getLogger(Coche.class);

    private final Semaphore semaforo;
    private int numeroCoche;
    private boolean expulsado;

    public Coche(Semaphore semaforo, int numeroCoche, boolean expulsado) {
        this.semaforo = semaforo;
        this.numeroCoche = numeroCoche;
        this.expulsado = expulsado;
    }

    @Override
    public void run() {
        while(true) {
            Random rd = new Random();

            int colaCoches = this.semaforo.getQueueLength();
            int plazasLibres = this.semaforo.availablePermits();

            try {
                Thread.sleep(rd.nextInt(1000, 5000) + 1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            logger.info("{} va a intentar entrar al parking", this.getName());
            logger.info("Hay {} coches en la cola", colaCoches);
            logger.info("Hay {} plazas libres", plazasLibres);

            if (colaCoches <= 5) {
                try {
                    semaforo.acquire();
                    logger.info("Estoy dentro del parking, qudan {} plazas libres", plazasLibres);
                    Thread.sleep(rd.nextInt(1000, 5000) + 1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                semaforo.release();
            } else {
                logger.info("Hay muchos coches en la cola, mejor damos otra vuelta");
            }

        }
    }

    public int getNumeroCoche() {
        return numeroCoche;
    }

    public void setNumeroCoche(int numeroCoche) {
        this.numeroCoche = numeroCoche;
    }

    public boolean isExpulsado() {
        return expulsado;
    }

    public void isExpulsado(boolean expulsado) {
        this.expulsado = expulsado;
    }

}
