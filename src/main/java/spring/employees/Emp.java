package spring.employees;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Emp {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private float salary;
    @NonNull
    private String designation;

    public Emp() {}

}
