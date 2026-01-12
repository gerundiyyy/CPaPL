package org.gerundiyyy.example;

import java.util.HashMap;
import java.util.Map;

public class MapClass {
    public static void main(String[] args) {
        String txt = " gena rudskiy ";
        HashMap<Character, Integer> map = new HashMap<Character, Integer>(40);

        for (int i = 0; i < txt.length(); ++i) {
            char c = txt.charAt(i);
            // проверяем, является ли символ буквой
            if (Character.isLetter(c)) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }
            }
        }

        // вывод на экран букв с частотой их появления
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            System.out.println("letter: " + entry.getKey() + " count: " + entry.getValue());
        }
    }
}
