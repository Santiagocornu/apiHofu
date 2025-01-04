package com.proyectoMama.proyectoMama.repositories;

import com.proyectoMama.proyectoMama.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
