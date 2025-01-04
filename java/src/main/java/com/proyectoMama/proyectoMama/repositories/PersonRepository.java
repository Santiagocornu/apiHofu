package com.proyectoMama.proyectoMama.repositories;

import com.proyectoMama.proyectoMama.entities.Person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
