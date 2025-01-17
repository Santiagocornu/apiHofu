package com.proyectoMama.proyectoMama.services;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Envoice;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Sale;
import com.proyectoMama.proyectoMama.repositories.EnvoiceRepository;
import com.proyectoMama.proyectoMama.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private EnvoiceRepository envoiceRepository;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public Sale updateSale(Long id, Sale saleDetails) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found for this id :: " + id));

        sale.setMonto_sale(saleDetails.getMonto_sale());
        sale.setFecha_sale(saleDetails.getFecha_sale());
        sale.setEnvoice(saleDetails.getEnvoice());

        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found for this id :: " + id));
        saleRepository.delete(sale);
    }

    public Sale associateEnvoice(Long saleId, Long envoiceId) {
        Sale sale = saleRepository.findById(saleId).orElseThrow(() -> new RuntimeException("Sale not found for this id :: " + saleId));
        Envoice envoice = envoiceRepository.findById(envoiceId).orElseThrow(() -> new RuntimeException("Envoice not found for this id :: " + envoiceId));

        sale.setEnvoice(envoice);
        return saleRepository.save(sale);
    }
}





