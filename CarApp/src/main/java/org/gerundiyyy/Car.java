package org.gerundiyyy;

class Car {
    private String brand;
    private int year;
    private double engineVolume;
    private double maxSpeed;

    public Car() {
        this.brand = "Null";
        this.year = 0;
        this.engineVolume = 0;
        this.maxSpeed = 0;
    }
    public Car(String brand, int year, double engineVolume, double maxSpeed) {
        this.brand = brand;
        this.year = year;
        this.engineVolume = engineVolume;
        this.maxSpeed = maxSpeed;
    }

    // Getters / Setters
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getEngineVolume() { return engineVolume; }
    public void setEngineVolume(double engineVolume) { this.engineVolume = engineVolume; }

    public double getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }

    void parseLine(String line) {
        if (line.indexOf("Brand: ") == 0)
            setBrand(line.substring(7));
        else if (line.indexOf("Year: ") == 0)
            setYear(Integer.parseInt(line.substring(6)));
        else if (line.indexOf("Engine volume: ") == 0)
            setEngineVolume(Double.parseDouble(line.substring(15)));
        else if (line.indexOf("Max speed: ") == 0)
            setMaxSpeed(Double.parseDouble(line.substring(11)));
    }
    @Override
    public String toString() {
        return "Car{" + "brand='" + brand + '\'' + ", year="
                + year + ", engineVolume=" + engineVolume
                + ", maxSpeed=" + maxSpeed + '}';
    }
}
