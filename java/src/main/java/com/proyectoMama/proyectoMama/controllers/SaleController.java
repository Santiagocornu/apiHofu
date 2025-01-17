package com.proyectoMama.proyectoMama.controllers;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Sale;
import com.proyectoMama.proyectoMama.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping("/sales")
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable(value = "id") Long saleId) {
        Sale sale = saleService.getSaleById(saleId).orElseThrow(() -> new RuntimeException("Sale not found for this id :: " + saleId));
        return ResponseEntity.ok().body(sale);
    }

    @PostMapping("/sales")
    public Sale createSale(@RequestBody Sale sale) {
        return saleService.createSale(sale);
    }

    @PutMapping("/sales/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable(value = "id") Long saleId, @RequestBody Sale saleDetails) {
        Sale updatedSale = saleService.updateSale(saleId, saleDetails);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable(value = "id") Long saleId) {
        saleService.deleteSale(saleId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/sales/{saleId}/associateEnvoice")
    public ResponseEntity<Sale> associateEnvoice(@PathVariable(value = "saleId") Long saleId, @RequestParam Long envoiceId) {
        Sale updatedSale = saleService.associateEnvoice(saleId, envoiceId);
        return ResponseEntity.ok(updatedSale);
    }
}





