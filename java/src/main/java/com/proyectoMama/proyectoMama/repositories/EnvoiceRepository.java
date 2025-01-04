package com.proyectoMama.proyectoMama.repositories;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Envoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EnvoiceRepository extends JpaRepository<Envoice, Long> {
}


