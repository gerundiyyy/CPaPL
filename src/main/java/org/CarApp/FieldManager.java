package org.CarApp;

import javax.swing.*;
import java.awt.*;

public class FieldManager {
    private final JTextField brandField = new JTextField(12);
    private final JTextField yearField = new JTextField(6);
    private final JTextField volumeField = new JTextField(8);
    private final JTextField speedField = new JTextField(8);
    private final JTextField searchField = new JTextField(8);

    private final JPanel inpFieldPanel = new JPanel();
    private final JPanel searchFieldPanel = new JPanel();

    public FieldManager(CarTableModel model) {
        inpFieldPanel.setLayout(new GridLayout(4,2));
        addBrand(model.getColumnName(0));
        addYear(model.getColumnName(1));
        addVolume(model.getColumnName(2));
        addSpeed(model.getColumnName(3));
        addSearch("Search/Delete");
    }

    private void addBrand(String label) {
        inpFieldPanel.add(brandField);
        inpFieldPanel.add(new JLabel(label));
    }
    private void addYear(String label) {
        inpFieldPanel.add(yearField);
        inpFieldPanel.add(new JLabel(label));
    }
    private void addVolume(String label) {
        inpFieldPanel.add(volumeField);
        inpFieldPanel.add(new JLabel(label));
    }
    private void addSpeed(String label) {
        inpFieldPanel.add(speedField);
        inpFieldPanel.add(new JLabel(label));
    }
    private void addSearch(String label) {
        searchFieldPanel.add(searchField);
        searchFieldPanel.add(new JLabel(label));
    }

    public JPanel getInpPanel() { return inpFieldPanel; }
    public JPanel getSearchPanel() { return searchFieldPanel; }

    // raw access (без валидации)
    public String getRawBrand() { return brandField.getText(); }
    public String getRawYear() { return yearField.getText(); }
    public String getRawVolume() { return volumeField.getText(); }
    public String getRawSpeed() { return speedField.getText(); }
    public String getRawSearch() { return searchField.getText(); }

    // парсинг / валидация — явные имена и единый контракт: или возвращают значение, или бросают IllegalArgumentException
    public String parseSearch() {
        String s = getRawSearch().trim();
        return s;
    }

    public String parseBrand() {
        String s = getRawBrand().trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Brand: поле обязательно");
        return s;
    }

    public int parseYear() {
        String s = getRawYear().trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Year: поле обязательно");
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Year: введите целое число");
        }
    }

    public double parseVolume() {
        String s = getRawVolume().trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Volume: поле обязательно");
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Volume: введите число (например 1.6)");
        }
    }

    public double parseSpeed() {
        String s = getRawSpeed().trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Speed: поле обязательно");
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Speed: введите число (например 120)");
        }
    }

    // clear / setters
    public void setBrand(String brand) { brandField.setText(brand == null ? "" : brand); }
    public void setYear(Integer value) { yearField.setText(value == null ? "" : value.toString()); }
    public void setVolume(Double value) { volumeField.setText(value == null ? "" : value.toString()); }
    public void setSpeed(Double value) { speedField.setText(value == null ? "" : value.toString()); }
    public void setSearch(String search) { brandField.setText(search == null ? "" : search); }

    public void clear() {
        setBrand(null);
        setYear(null);
        setVolume(null);
        setSpeed(null);
    }
    public void clearSearch() {
        setSearch(null);
    }

    // агрегирующий метод — собирает все сообщения об ошибках и бросает одно исключение
    public Car readFieldsAsCar() {
        StringBuilder errors = new StringBuilder();

        String brand = null;
        try { brand = parseBrand(); }
        catch (IllegalArgumentException ex) { errors.append(ex.getMessage()).append("\n"); }

        Integer year = null;
        try { year = parseYear(); }
        catch (IllegalArgumentException ex) { errors.append(ex.getMessage()).append("\n"); }

        Double volume = null;
        try { volume = parseVolume(); }
        catch (IllegalArgumentException ex) { errors.append(ex.getMessage()).append("\n"); }

        Double speed = null;
        try { speed = parseSpeed(); }
        catch (IllegalArgumentException ex) { errors.append(ex.getMessage()).append("\n"); }

        if (errors.length() > 0) {
            throw new IllegalArgumentException(errors.toString().trim());
        }

        return new Car(brand, year, volume, speed);
    }
}
