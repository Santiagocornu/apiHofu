package com.proyectoMama.proyectoMama.repositories;

import com.proyectoMama.proyectoMama.entities.Person.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
