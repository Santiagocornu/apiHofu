package com.proyectoMama.proyectoMama.repositories;

import com.proyectoMama.proyectoMama.entities.Person.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
