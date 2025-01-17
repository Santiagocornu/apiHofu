package com.proyectoMama.proyectoMama.controllers;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceDTO;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceProductDTO;
import com.proyectoMama.proyectoMama.services.EnvoiceProductService;
import com.proyectoMama.proyectoMama.services.EnvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return envoiceService.getAllEnvoices();
        } catch (Exception e) {
            // Registro del error
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener todas las envoices", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvoiceDTO> getEnvoiceById(@PathVariable Long id) {
        try {
            EnvoiceDTO envoiceDTO = envoiceService.getEnvoiceById(id);
            return envoiceDTO != null ? ResponseEntity.ok(envoiceDTO) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Registro del error
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener envoice por ID", e);
        }
    }

    @PostMapping
    public ResponseEntity<EnvoiceDTO> createEnvoice(@RequestBody EnvoiceDTO envoiceDTO) {
        try {
            EnvoiceDTO createdEnvoice = envoiceService.createEnvoice(envoiceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEnvoice);
        } catch (Exception e) {
            // Registro del error
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear envoice", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvoiceDTO> updateEnvoice(@PathVariable Long id, @RequestBody EnvoiceDTO envoiceDTO) {
        try {
            EnvoiceDTO updatedEnvoice = envoiceService.updateEnvoice(id, envoiceDTO);
            return updatedEnvoice != null ? ResponseEntity.ok(updatedEnvoice) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Registro del error
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar envoice", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvoice(@PathVariable Long id) {
        try {
            if (envoiceService.deleteEnvoice(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Registro del error
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar envoice", e);
        }
    }

    @PutMapping("/{id}/associateClient")
    public ResponseEntity<EnvoiceDTO> associateClient(@PathVariable Long id, @RequestParam Long clientId) {
        try {
            EnvoiceDTO updatedEnvoice = envoiceService.associateClient(id, clientId);
            return updatedEnvoice != null ? ResponseEntity.ok(updatedEnvoice) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Registro del error
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al asociar cliente a envoice", e);
        }
    }

    @PutMapping("/{id}/associateEmployer")
    public ResponseEntity<EnvoiceDTO> associateEmployer(@PathVariable Long id, @RequestParam Long employerId) {
        try {
            EnvoiceDTO updatedEnvoice = envoiceService.associateEmployer(id, employerId);
            return updatedEnvoice != null ? ResponseEntity.ok(updatedEnvoice) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Registro del error
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al asociar empleador a envoice", e);
        }
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<EnvoiceProductDTO>> getProductsByEnvoiceId(@PathVariable Long id) {
        try {
            List<EnvoiceProductDTO> products = envoiceService.getProductsByEnvoiceId(id);
            return products != null && !products.isEmpty() ? ResponseEntity.ok(products) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Registro del error
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener productos de envoice", e);
        }
    }

    @PostMapping("/createWithProducts")
    public ResponseEntity<EnvoiceDTO> createEnvoiceWithProducts(@RequestBody EnvoiceDTO envoiceDTO) {
        try {
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
        } catch (Exception e) {
            // Registro del error
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear envoice con productos", e);
        }
    }
}

