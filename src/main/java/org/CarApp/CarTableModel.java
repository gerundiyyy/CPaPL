
import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

public class CarTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Model", "Year", "Engine Volume", "Max Speed"};
    private final Class<?>[] columnClasses = { String.class, Integer.class, Double.class, Integer.class };

    private LinkedList<Car> cars = new LinkedList<>();

    public LinkedList<Car> getCars(){ return cars; }
    public void setCars(LinkedList<Car> cars){ this.cars = cars; }

    @Override
    public int getColumnCount() { return columnNames.length; }
    @Override
    public int getRowCount() { return cars.size(); }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Car c = cars.get(rowIndex);
        switch (columnIndex) {
            case 0: return c.getBrand();
            case 1: return c.getYear();
            case 2: return c.getEngineVolume();
            case 3: return c.getMaxSpeed();
            default: throw new IndexOutOfBoundsException("Invalid column: " + columnIndex);
        }
    }
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){
        Car c = cars.get(rowIndex);
        try {
            switch (columnIndex) {
                case 0: c.setBrand(String.valueOf(value)); break;
                case 1: c.setYear(Integer.parseInt(String.valueOf(value))); break;
                case 2: c.setEngineVolume(Double.parseDouble(String.valueOf(value))); break;
                case 3: c.setMaxSpeed(Integer.parseInt(String.valueOf(value))); break;
            }
            fireTableRowsUpdated(rowIndex, rowIndex);
        } catch (Exception ex){}
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }
    @Override
    public String getColumnName(int columnIndex) {
        if(columnIndex < 0 || columnIndex > columnNames.length){
            throw new IndexOutOfBoundsException("Invalid column: " + columnIndex);
        }
        return columnNames[columnIndex];
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void setAll(Collection<Car> list) {
        cars.clear();
        cars.addAll(list);
        fireTableDataChanged();
    }
    public LinkedList<Car> getAll() {
        return new LinkedList<>(cars);
    }
    public void addRow(Car car){
        cars.add(car);

        int idx = cars.size() - 1;
        fireTableRowsInserted(idx, idx);
    }
    public void deleteRow(String brand){
        sortRows();
        int key = findRow(brand);
        if(key == -1){
            throw new IllegalArgumentException("Invalid brand: " + brand);
        }
        cars.remove(key);
        fireTableRowsInserted(key, key);
    }
    public void sortRows(){
        cars.sort(Comparator.comparing(Car::getBrand));
    }
    public int findRow(String brand){
        for (int i = 0; i < cars.size(); i++) {
            if (brand.equals(cars.get(i).getBrand())) {
                return i;
            }
        }
        return -1;
    }

}
