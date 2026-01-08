package org.gerundiyyy.example;

import java.awt.*;
import java.applet.*;

// класс апплета, который реализует интерфейс Runnable
public class AppletThreadSample extends Applet implements Runnable {
    private Thread T; // создать объект потока

    // объявление переменных
    private ShapeString m_ShapeString = null; // для строки
    private ShapeOval m_ShapeOval = null;     // для овала
    private ShapeRect m_ShapeRect = null;     // для квадрата

    @Override
    public void run() { // реализация метода run, точка входа в поток
        setBackground(Color.yellow); // фон апплета зарисовывается желтым
        while (true) { // бесконечный цикл
            repaint(); // перерисовка апплета или вызов метода paint
            try {
                Thread.sleep(10); // приостановка апплета на 10 миллисекунд
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public void init() { // метод инициализации апплета
        // создание объектов
        m_ShapeString = new ShapeString();
        m_ShapeOval = new ShapeOval();
        m_ShapeRect = new ShapeRect();

        T = new Thread(this); // создание потока и привязка его к текущему классу
        T.start(); // запуск потока (вызывается run)
    }

    @Override
    public void paint(Graphics g) { // метод прорисовки апплета
        // прорисовка строки
        if (m_ShapeString != null) {
            g.drawString("This is ShapeString", m_ShapeString.x_String, m_ShapeString.y_String);
        }

        // прорисовка квадрата
        if (m_ShapeRect != null) {
            g.setColor(Color.red);
            g.drawRect(m_ShapeRect.x_Rect, m_ShapeRect.y_Rect, m_ShapeRect.w_Rect, m_ShapeRect.h_Rect);
        }

        // прорисовка овала
        if (m_ShapeOval != null) {
            g.setColor(Color.CYAN);
            g.fillOval(m_ShapeOval.x_Oval, m_ShapeOval.y_Oval, m_ShapeOval.w_Oval, m_ShapeOval.h_Oval);
        }
    }

    @Override
    public void stop() {
        // попытка корректно прервать главный поток апплета
        if (T != null) {
            T.interrupt();
        }
    }
}

// класс ShapeString, реализующий интерфейс Runnable
class ShapeString implements Runnable {
    Thread T;
    int x_String, y_String; // координаты строки

    public ShapeString() { // конструктор
        T = new Thread(this); // создание объекта Thread
        // установление начальных координат строки
        x_String = 100;
        y_String = 100;
        T.start(); // запуск потока (вызов метода run)
    }

    @Override
    public void run() { // метод run
        for (;;) {
            x_String += 15; // изменение координаты строки
            try {
                Thread.sleep(1000); // приостановка работы потока на 1000 миллисекунд
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

// класс ShapeRect реализующий интерфейс Runnable
class ShapeRect implements Runnable {
    Thread T;
    int x_Rect, y_Rect, w_Rect, h_Rect; // координаты и размеры квадрата

    public ShapeRect() { // конструктор
        T = new Thread(this); // создание объекта Thread
        // установление начальных координат квадрата
        x_Rect = 350;
        y_Rect = 50;
        w_Rect = 100;
        h_Rect = 100;
        T.start(); // запуск потока (вызов метода run)
    }

    @Override
    public void run() { // метод run
        for (;;) {
            x_Rect -= 15; // изменение координаты квадрата
            try {
                Thread.sleep(500); // приостановка работы потока на 500 миллисекунд
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

// класс ShapeOval реализующий интерфейс Runnable
class ShapeOval implements Runnable {
    Thread T;
    int x_Oval, y_Oval, w_Oval, h_Oval; // координаты и размеры овала

    public ShapeOval() { // конструктор
        T = new Thread(this); // создание объекта Thread
        // установление начальных координат овала
        x_Oval = 30;
        y_Oval = 30;
        w_Oval = 100;
        h_Oval = 90;
        T.start(); // запуск потока (вызов метода run)
    }

    @Override
    public void run() { // метод run
        for (;;) {
            // изменение координат овала
            x_Oval += 8;
            y_Oval += 7;
            try {
                Thread.sleep(100); // приостановка работы потока на 100 миллисекунд
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
