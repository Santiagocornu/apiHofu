package com.proyectoMama.proyectoMama.controllers;


import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Envoice;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Sale;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.SaleDTO;
import com.proyectoMama.proyectoMama.repositories.EnvoiceRepository;
import com.proyectoMama.proyectoMama.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = {"http://localhost:3000", "https://front-hofu.vercel.app"})
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private EnvoiceRepository envoiceRepository;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        List<SaleDTO> saleDTOs = sales.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(saleDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSaleById(@PathVariable Long id) {
        Optional<Sale> sale = saleService.getSaleById(id);
        return sale.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
        Sale sale = convertToEntity(saleDTO);
        Sale createdSale = saleService.createSale(sale);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdSale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> updateSale(@PathVariable Long id, @RequestBody SaleDTO saleDTO) {
        Sale saleDetails = convertToEntity(saleDTO);
        Sale updatedSale = saleService.updateSale(id, saleDetails);
        return ResponseEntity.ok(convertToDTO(updatedSale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/associateEnvoice")
    public ResponseEntity<SaleDTO> associateEnvoice(@PathVariable Long id, @RequestParam Long envoiceId) {
        Sale updatedSale = saleService.associateEnvoice(id, envoiceId);
        return ResponseEntity.ok(convertToDTO(updatedSale));
    }

    private SaleDTO convertToDTO(Sale sale) {
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId_sale(sale.getId_sale());
        saleDTO.setMonto_sale(sale.getMonto_sale());
        saleDTO.setFecha_sale(sale.getFecha_sale());
        if (sale.getEnvoice() != null) {
            saleDTO.setEnvoice_id(sale.getEnvoice().getId_envoice());
        }
        return saleDTO;
    }

    private Sale convertToEntity(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setMonto_sale(saleDTO.getMonto_sale());
        sale.setFecha_sale(saleDTO.getFecha_sale());
        if (saleDTO.getEnvoice_id() != null) {
            Envoice envoice = envoiceRepository.findById(saleDTO.getEnvoice_id())
                    .orElseThrow(() -> new RuntimeException("Envoice not found for this id :: " + saleDTO.getEnvoice_id()));
            sale.setEnvoice(envoice);
        }
        return sale;
    }
}






