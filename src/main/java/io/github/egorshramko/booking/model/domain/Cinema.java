package io.github.egorshramko.booking.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {

    @Id
    @SequenceGenerator(name = "cinema_id_gen", sequenceName = "cinema_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cinema_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    private String name;

    private String address;

    private String city;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "cinema_id")
    private Set<Employee> employees = new HashSet<>();

    //JSON схемы мест
    private String seatingChartJson;

    //имя файла с фотографией кинотеатра в S3
    private String cinemaPhotoFilename;

    public Employee getManager() {
        return employees.stream()
                .filter(Employee::isManager)
                .findFirst()
                .orElse(null);
    }

    public void setManager(Employee employee) {
        Optional<Employee> managerEmployeeOptional = employees.stream()
                .filter(emp -> emp.equals(employee))
                .findAny();

        if (managerEmployeeOptional.isEmpty()) {
            employee.setManager(true);
            this.addEmployee(employee);
        }
        else {
            Employee managerEmployee = managerEmployeeOptional.get();
            managerEmployee.setManager(true);
        }

    }

    public void addEmployee(Employee employee) {
        boolean addingSuccessfully = employees.add(employee);
        if (!addingSuccessfully) {
            throw new RuntimeException("Unknown error while adding employee to cinema");
        }
    }

    public boolean removeEmployee(Employee employee) {
        return employees.remove(employee);
    }

}
