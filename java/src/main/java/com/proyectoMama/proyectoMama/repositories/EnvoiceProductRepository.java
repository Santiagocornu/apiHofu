package com.proyectoMama.proyectoMama.repositories;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvoiceProductRepository extends JpaRepository<EnvoiceProduct, Long> {
    @Query("SELECT ep FROM EnvoiceProduct ep WHERE ep.envoice.id_envoice = :envoiceId")
    List<EnvoiceProduct> findByEnvoiceId(@Param("envoiceId") Long envoiceId);
}


