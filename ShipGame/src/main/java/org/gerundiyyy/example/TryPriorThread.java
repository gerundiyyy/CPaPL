package org.gerundiyyy.example;

public class TryPriorThread extends Thread {
    public TryPriorThread(String threadName) {
        super(threadName);
        System.out.println("Thread '" + threadName + "' created!");
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Thread '" + getName() + "' " + i);
            try {
                sleep(1); // ожидать одну миллисекунду
            } catch (InterruptedException e) {
                System.out.print("Error:" + e);
            }
        }
    }

    public static void main(String[] args) {
        // создать три потока выполнения
        Thread min_thr = new TryPriorThread("ThreadMin");
        Thread max_thr = new TryPriorThread("ThreadMax");
        Thread norm_thr = new TryPriorThread("ThreadNorm");

        System.out.println("Starting threads...");

        min_thr.setPriority(Thread.MIN_PRIORITY);   // минимальный приоритет
        max_thr.setPriority(Thread.MAX_PRIORITY);   // максимальный приоритет
        norm_thr.setPriority(Thread.NORM_PRIORITY); // нормальный приоритет

        min_thr.start();  // запустить первый поток
        max_thr.start();  // запустить второй поток
        norm_thr.start(); // запустить третий поток
    }
}
