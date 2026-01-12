package org.gerundiyyy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortPoemLines {
    public static void main(String[] args) {
        // Poems by one author: each poem is a list of lines (now with rhyme)
        List<List<String>> poems = new ArrayList<>();

        List<String> poem1 = new ArrayList<>(Arrays.asList(
                "Night draped the town in gentle light,",
                "Stars leaned close to guard the night,",
                "The old oak hummed a secret tune,",
                "My heart replied beneath the moon."
        ));

        List<String> poem2 = new ArrayList<>(Arrays.asList(
                "Morning spilled its gold along the way,",
                "Dew bowed low to greet the day,",
                "A sparrow sang as if to play,",
                "Coffee warmed my hands and chased the gray."
        ));

        List<String> poem3 = new ArrayList<>(Arrays.asList(
                "The sea keeps letters written in the sand,",
                "Waves erase them with a gentle hand,",
                "A gull cries out and circles free,",
                "Horizon answers with a silent sea."
        ));

        poems.add(poem1);
        poems.add(poem2);
        poems.add(poem3);

        // Print and sort each poem
        for (int i = 0; i < poems.size(); i++) {
            List<String> poem = poems.get(i);
            System.out.println("Poem " + (i + 1) + " before sorting:");
            printLines(poem);
            System.out.println("--------------------------------------------------");
            // Sort lines by ascending length
            poem.sort(Comparator.comparingInt(String::length));

            System.out.println("Poem " + (i + 1) + " after sorting by line length:");
            printLines(poem);
            System.out.println("===================================================");
        }
    }

    private static void printLines(List<String> lines) {
        for (String line : lines) {
            System.out.println(line + "  " + "(len=" + line.length() + ")");
        }
    }
}
