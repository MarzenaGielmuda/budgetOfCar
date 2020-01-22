package spring.type;

import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Car {

    private long id;
    private double value;
    private String description;
    private Date date;

    public Car(long id, double value, String description, Date date) {
        this.id = id;
        this.value = value;
        this.description = description;
        this.date = date;
    }

    public Car() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                Double.compare(car.value, value) == 0 &&
                Objects.equals(description, car.description) &&
                Objects.equals(date, car.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, description, date);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //
//    public Car() {
//}
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public double getValue() {
//        return value;
//    }
//
//    public void setValue(double value) {
//        this.value = value;
//    }
////
////    public LocalDate getData() {
////        return date;
////    }
//
////    public void setData(LocalDate data) {
////        this.date = date;
////    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "id=" + id +
//                ", value=" + value +
////                ", data=" + date +
//                ", description='" + description + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Car car = (Car) o;
//        return id == car.id &&
//                Double.compare(car.value, value) == 0 &&
////                Objects.equals(date, car.date) &&
//                Objects.equals(description, car.description);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, value, description);
//    }
}