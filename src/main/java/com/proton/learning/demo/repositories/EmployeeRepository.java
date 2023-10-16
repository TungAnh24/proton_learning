package com.proton.learning.demo.repositories;

import com.proton.learning.demo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // Kiểm tra xem thông tin ( username, phone, email ) đã có trên hệ thống chưa
    @Query("select (count(epl) > 0) " +
            "from Employee epl where upper(epl.username) = upper(?1) " +
            "                    and upper(epl.phone) = upper(?2) and upper(epl.email) = upper(?3)")
    boolean checkExistEpl(String eplUserName, String eplEmail, String eplPhone);
}
