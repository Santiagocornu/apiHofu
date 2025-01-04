package com.proyectoMama.proyectoMama.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

