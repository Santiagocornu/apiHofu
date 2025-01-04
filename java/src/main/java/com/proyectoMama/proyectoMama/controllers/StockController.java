package com.proyectoMama.proyectoMama.controllers;

import com.proyectoMama.proyectoMama.entities.Stock;
import com.proyectoMama.proyectoMama.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = {"http://localhost:3000", "https://front-hofu.vercel.app"})

public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Optional<Stock> stock = stockService.findById(id);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Stock createStock(@RequestBody Stock stock) {
        return stockService.save(stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stockDetails) {
        Optional<Stock> stock = stockService.findById(id);
        if (stock.isPresent()) {
            Stock updatedStock = stock.get();
            updatedStock.setNombre_stock(stockDetails.getNombre_stock());
            updatedStock.setCantidad_stock(stockDetails.getCantidad_stock());
            return ResponseEntity.ok(stockService.save(updatedStock));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        if (stockService.findById(id).isPresent()) {
            stockService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
