package com.proyectoMama.proyectoMama.controllers;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceDTO;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceProductDTO;
import com.proyectoMama.proyectoMama.services.EnvoiceProductService;
import com.proyectoMama.proyectoMama.services.EnvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envoices")
@CrossOrigin(origins = {"http://localhost:3000", "https://front-hofu.vercel.app"})
public class EnvoiceController {

    @Autowired
    private EnvoiceService envoiceService;

    @Autowired
    private EnvoiceProductService envoiceProductService;

    @GetMapping
    public List<EnvoiceDTO> getAllEnvoices() {
        return envoiceService.getAllEnvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvoiceDTO> getEnvoiceById(@PathVariable Long id) {
        EnvoiceDTO envoiceDTO = envoiceService.getEnvoiceById(id);
        if (envoiceDTO != null) {
            return ResponseEntity.ok(envoiceDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EnvoiceDTO> createEnvoice(@RequestBody EnvoiceDTO envoiceDTO) {
        EnvoiceDTO createdEnvoice = envoiceService.createEnvoice(envoiceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvoiceDTO> updateEnvoice(@PathVariable Long id, @RequestBody EnvoiceDTO envoiceDTO) {
        EnvoiceDTO updatedEnvoice = envoiceService.updateEnvoice(id, envoiceDTO);
        if (updatedEnvoice != null) {
            return ResponseEntity.ok(updatedEnvoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvoice(@PathVariable Long id) {
        if (envoiceService.deleteEnvoice(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/associateClient")
    public ResponseEntity<EnvoiceDTO> associateClient(@PathVariable Long id, @RequestParam Long clientId) {
        EnvoiceDTO updatedEnvoice = envoiceService.associateClient(id, clientId);
        if (updatedEnvoice != null) {
            return ResponseEntity.ok(updatedEnvoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/associateEmployer")
    public ResponseEntity<EnvoiceDTO> associateEmployer(@PathVariable Long id, @RequestParam Long employerId) {
        EnvoiceDTO updatedEnvoice = envoiceService.associateEmployer(id, employerId);
        if (updatedEnvoice != null) {
            return ResponseEntity.ok(updatedEnvoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<EnvoiceProductDTO>> getProductsByEnvoiceId(@PathVariable Long id) {
        List<EnvoiceProductDTO> products = envoiceService.getProductsByEnvoiceId(id);
        if (products != null && !products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createWithProducts")
    public ResponseEntity<EnvoiceDTO> createEnvoiceWithProducts(@RequestBody EnvoiceDTO envoiceDTO) {
        EnvoiceDTO createdEnvoice = envoiceService.createEnvoice(envoiceDTO);
        if (createdEnvoice != null) {
            envoiceDTO.getProducts().forEach(envoiceProductDTO -> {
                envoiceProductDTO.setEnvoiceId(createdEnvoice.getId_envoice());
                envoiceProductService.createEnvoiceProduct(envoiceProductDTO);
            });
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEnvoice);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


