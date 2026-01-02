package org.CarApp;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FrameManager extends JFrame{
    private final CarTableModel model = new CarTableModel();
    private final TableRowSorter<CarTableModel> sorter = new TableRowSorter<>(model);
    private final FieldManager fl = new FieldManager(model);

    private final JButton addBtn = new JButton("Add");
    private final JButton delBtn = new JButton("Delete");
    private final JButton readBtn = new JButton("Read from file");
    private final JButton writeBtn = new JButton("Write to file");

    public FrameManager(){
        initButtons();
        initTable();
        initActions();
        initFields();
        model.addRow(new Car("Toyota", 2015, 1.6, 190));
        model.addRow(new Car("BMW", 2018, 2.0, 240));
        model.addRow(new Car("Lada", 2012, 1.6, 170));
    }

    private void initTable(){
        JTable table = new JTable(model);
        table.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void initFields(){
        add(fl.getInpPanel(), BorderLayout.WEST);
        add(fl.getSearchPanel(), BorderLayout.EAST);
    }

    private void initButtons(){
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());

        btnPanel.add(addBtn);
        btnPanel.add(delBtn);
        btnPanel.add(readBtn);
        btnPanel.add(writeBtn);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void initActions(){
        addBtn.addActionListener(e -> onAdd());
        delBtn.addActionListener(e -> onDel());
        readBtn.addActionListener(e -> onRead());
        writeBtn.addActionListener(e -> onWrite());
    }

    private void onAdd() {
        try {
            Car car = fl.readFieldsAsCar();
            model.addRow(car);
            fl.clear();
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }
    private void onDel() {
        try {
            String brand = fl.parseSearch();
            model.deleteRow(brand);
            fl.clearSearch();
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    public void onRead() {
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(FrameManager.this, "Открыть файл для чтения" );
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            parseFile(file);
        }
    }

    public void onWrite() {
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(FrameManager.this, "Сохранить в файл" );
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            CarFileParser parser = new CarFileParser();
            try {
                // Получаем список автомобилей из модели. Предполагаем метод getAll()
                List<Car> cars = model.getAll();
                parser.writeToFile(file, cars);
                JOptionPane.showMessageDialog(this, "Файл сохранён", "Информация", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                showError("Ошибка записи файла: " + ex.getMessage());
            }
        }
    }

    public void parseFile(File file){
        CarFileParser parser = new CarFileParser();
        List<Car> list = parser.parseFile(file);
        if (!list.isEmpty()) {
            model.setAll(list); // заменяем содержимое модели (или используйте model.addRows если хотите добавлять)
        } else {
            showError("Файл прочитан, но записей не найдено");
        }
    }
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
