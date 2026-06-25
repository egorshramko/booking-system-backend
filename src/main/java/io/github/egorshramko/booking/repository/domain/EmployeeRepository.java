package io.github.egorshramko.booking.repository.domain;

import io.github.egorshramko.booking.model.domain.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e JOIN e.profile p JOIN p.user u WHERE u.username = :username")
    Optional<Employee> findByUsername(@Param("username") String username);

}
