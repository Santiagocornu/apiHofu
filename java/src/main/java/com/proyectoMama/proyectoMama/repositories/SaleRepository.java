package com.proyectoMama.proyectoMama.repositories;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {
}
