package com.proyectoMama.proyectoMama.services;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Sale;
import com.proyectoMama.proyectoMama.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id).orElse(null);
    }

    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public Sale updateSale(Long id, Sale saleDetails) {
        return saleRepository.findById(id).map(sale -> {
            sale.setMonto_sale(saleDetails.getMonto_sale());
            sale.setEnvoice(saleDetails.getEnvoice());
            return saleRepository.save(sale);
        }).orElse(null);
    }

    public boolean deleteSale(Long id) {
        return saleRepository.findById(id).map(sale -> {
            saleRepository.delete(sale);
            return true;
        }).orElse(false);
    }
}


