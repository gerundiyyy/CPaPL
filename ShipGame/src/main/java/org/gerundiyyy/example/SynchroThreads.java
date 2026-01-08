package org.gerundiyyy.example;

import java.io.*;

public class SynchroThreads {
    public static void main(String[] args) {
        SynchroFile sf = new SynchroFile(); // объект класса SynchroFile
        FileThread ft1 = new FileThread("FisrtThread", sf); // первый поток
        FileThread ft2 = new FileThread("SecondThread", sf); // второй поток
        ft1.start(); // стартовать первый поток
        ft2.start(); // стартовать второй поток
    }
}

class FileThread extends Thread {
    String str;
    SynchroFile sf;

    public FileThread(String str, SynchroFile sf) {
        this.str = str;
        this.sf = sf;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            sf.writing(str, i);
        }
    }
}

class SynchroFile {
    File f = new File("file.txt");

    public SynchroFile() {
        System.out.println("Object SynchroFile creating...");
        try {
            f.delete(); // удалить файл, если он есть
            f.createNewFile(); // создать новый файл
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public synchronized void writing(String str, int i) {
        try {
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            raf.seek(raf.length()); // переместить указатель в конец
            System.out.print(str);
            raf.writeBytes(str); // записать в файл

            // на случайное значение приостановить поток
            Thread.sleep((long) (Math.random() * 15));

            raf.seek(raf.length()); // переместить указатель в конец
            System.out.print("->" + i + " \n");
            raf.writeBytes("->" + i + " \n"); // записать в файл
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        notify(); // известить об окончании работы с методом
    }
}
