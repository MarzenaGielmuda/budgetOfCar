package spring.employees;

import lombok.Data;
import lombok.NonNull;

import java.util.Objects;
@NonNull
@Data
public class Emp {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private float salary;
    @NonNull
    private String designation;
    @NonNull
    private String email;

    public Emp() {}

}
