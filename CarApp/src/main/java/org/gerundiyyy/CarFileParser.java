package org.gerundiyyy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CarFileParser {

    public List<Car> parseFile(File file) {
        List<String> lines = readAllLines(file);
        List<List<String>> blocks = splitToBlocksByEmptyLine(lines);
        return parseBlocks(blocks);
    }

    private List<String> readAllLines(File file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            showError("Error of reading file: " + ex.getMessage());
        }
        return lines;
    }

    private List<List<String>> splitToBlocksByEmptyLine(List<String> lines) {
        List<List<String>> blocks = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (String raw : lines) {
            String trimmed = raw == null ? "" : raw.trim();
            if (trimmed.isEmpty()) {
                if (!current.isEmpty()) {
                    blocks.add(new ArrayList<>(current));
                    current.clear();
                }
                continue;
            }
            if (isComment(trimmed)) continue;
            current.add(trimmed);
        }
        if (!current.isEmpty()) blocks.add(current);
        return blocks;
    }

    private List<Car> parseBlocks(List<List<String>> blocks) {
        List<Car> result = new ArrayList<>();
        for (int i = 0; i < blocks.size(); i++) {
            List<String> block = blocks.get(i);
            try {
                Car c = parseBlock(block);
                result.add(c);
            } catch (Exception ex) {
                showError("Error of parsing #" + (i + 1) + ": " + ex.getMessage());
            }
        }
        return result;
    }

    private Car parseBlock(List<String> block) {
        Car car = new Car();
        for (String line : block) {
            parseLineToCar(line, car);
        }
        return car;
    }


    private void parseLineToCar(String line, Car car) {
        if (line.startsWith("Brand:")) {
            car.setBrand(line.substring("Brand:".length()).trim());
        } else if (line.startsWith("Year:")) {
            String s = line.substring("Year:".length()).trim();
            if (!s.isEmpty()) car.setYear(Integer.parseInt(s));
        } else if (line.startsWith("Engine volume:")) {
            String s = line.substring("Engine volume:".length()).trim();
            if (!s.isEmpty()) car.setEngineVolume(Double.parseDouble(s));
        } else if (line.startsWith("Max speed:")) {
            String s = line.substring("Max speed:".length()).trim();
            if (!s.isEmpty()) car.setMaxSpeed(Double.parseDouble(s));
        }
    }

    private boolean isComment(String line) { return line.startsWith("#"); }

    private void showError(String msg) {
        System.err.println(msg);
    }

    public void writeToFile(File file, List<Car> cars) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (int i = 0; i < cars.size(); i++) {
                Car c = cars.get(i);
                // Записываем только те поля, которые непусты / не нулевые
                if (c.getBrand() != null && !c.getBrand().isEmpty()) {
                    bw.write("Brand: " + c.getBrand());
                    bw.newLine();
                }
                // для простоты предполагаем, что year, engineVolume, maxSpeed — объекты (Integer/Double)
                if (c.getYear() != 0) {
                    bw.write("Year: " + c.getYear());
                    bw.newLine();
                }
                if (c.getEngineVolume() != 0) {
                    bw.write("Engine volume: " + c.getEngineVolume());
                    bw.newLine();
                }
                if (c.getMaxSpeed() != 0) {
                    bw.write("Max speed: " + c.getMaxSpeed());
                    bw.newLine();
                }
                // разделитель блоков — пустая строка
                if (i < cars.size() - 1) bw.newLine();
            }
            bw.flush();
        }
    }
}
