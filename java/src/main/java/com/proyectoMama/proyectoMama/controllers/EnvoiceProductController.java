package com.proyectoMama.proyectoMama.controllers;


import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceProductDTO;
import com.proyectoMama.proyectoMama.services.EnvoiceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envoiceProducts")
@CrossOrigin(origins = "http://localhost:3000")
public class EnvoiceProductController {

    @Autowired
    private EnvoiceProductService envoiceProductService;

    @GetMapping
    public List<EnvoiceProductDTO> getAllEnvoiceProducts() {
        return envoiceProductService.getAllEnvoiceProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvoiceProductDTO> getEnvoiceProductById(@PathVariable Long id) {
        EnvoiceProductDTO envoiceProductDTO = envoiceProductService.getEnvoiceProductById(id);
        if (envoiceProductDTO != null) {
            return ResponseEntity.ok(envoiceProductDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EnvoiceProductDTO> createEnvoiceProduct(@RequestBody EnvoiceProductDTO dto) {
        System.out.println("Creating Envoice Product: " + dto);
        try {
            EnvoiceProductDTO createdEnvoiceProduct = envoiceProductService.createEnvoiceProduct(dto);
            return ResponseEntity.ok(createdEnvoiceProduct);
        } catch (Exception e) {
            System.err.println("Error creating Envoice Product: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<EnvoiceProductDTO> updateEnvoiceProduct(@PathVariable Long id, @RequestBody EnvoiceProductDTO dto) {
        EnvoiceProductDTO updatedEnvoiceProduct = envoiceProductService.updateEnvoiceProduct(id, dto);
        if (updatedEnvoiceProduct != null) {
            return ResponseEntity.ok(updatedEnvoiceProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvoiceProduct(@PathVariable Long id) {
        if (envoiceProductService.deleteEnvoiceProduct(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/associateEnvoice")
    public ResponseEntity<EnvoiceProductDTO> associateEnvoice(@PathVariable Long id, @RequestParam Long envoiceId) {
        EnvoiceProductDTO updatedEnvoiceProduct = envoiceProductService.associateEnvoice(id, envoiceId);
        if (updatedEnvoiceProduct != null) {
            return ResponseEntity.ok(updatedEnvoiceProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/associateProduct")
    public ResponseEntity<EnvoiceProductDTO> associateProduct(@PathVariable Long id, @RequestParam Long productId) {
        EnvoiceProductDTO updatedEnvoiceProduct = envoiceProductService.associateProduct(id, productId);
        if (updatedEnvoiceProduct != null) {
            return ResponseEntity.ok(updatedEnvoiceProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


