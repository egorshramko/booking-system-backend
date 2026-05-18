package io.github.egorshramko.booking.repository.domain;

import io.github.egorshramko.booking.model.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
