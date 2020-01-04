package spring.type;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;

public class Car {

    private long id;
    private double value;
//    private LocalDate date;
    private String description;


    public Car() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
//
//    public LocalDate getData() {
//        return date;
//    }

//    public void setData(LocalDate data) {
//        this.date = date;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", value=" + value +
//                ", data=" + date +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                Double.compare(car.value, value) == 0 &&
//                Objects.equals(date, car.date) &&
                Objects.equals(description, car.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, description);
    }
}