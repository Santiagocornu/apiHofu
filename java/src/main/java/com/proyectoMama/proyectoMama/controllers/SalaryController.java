package com.proyectoMama.proyectoMama.controllers;

import com.proyectoMama.proyectoMama.entities.Person.Salary;
import com.proyectoMama.proyectoMama.services.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salaries")
@CrossOrigin(origins = {"http://localhost:3000", "https://front-hofu.vercel.app"})

public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @GetMapping
    public List<Salary> getAllSalaries() {
        return salaryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salary> getSalaryById(@PathVariable Long id) {
        Optional<Salary> salary = salaryService.findById(id);
        return salary.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Salary createSalary(@RequestBody Salary salary) {
        return salaryService.save(salary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Salary> updateSalary(@PathVariable Long id, @RequestBody Salary salaryDetails) {
        Optional<Salary> salary = salaryService.findById(id);
        if (salary.isPresent()) {
            Salary updatedSalary = salary.get();
            updatedSalary.setHora_salary(salaryDetails.getHora_salary());
            updatedSalary.setPrecioHora_salary(salaryDetails.getPrecioHora_salary());
            updatedSalary.setEmployer(salaryDetails.getEmployer());
            return ResponseEntity.ok(salaryService.save(updatedSalary));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long id) {
        if (salaryService.findById(id).isPresent()) {
            salaryService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
