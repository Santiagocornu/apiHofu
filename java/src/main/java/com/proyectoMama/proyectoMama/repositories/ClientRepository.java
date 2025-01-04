package com.proyectoMama.proyectoMama.repositories;

import com.proyectoMama.proyectoMama.entities.Person.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ClientRepository extends JpaRepository<Client, Long> {
}
