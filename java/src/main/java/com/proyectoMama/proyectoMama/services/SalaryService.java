package com.proyectoMama.proyectoMama.services;

import com.proyectoMama.proyectoMama.entities.Person.Salary;
import com.proyectoMama.proyectoMama.repositories.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    private SalaryRepository salaryRepository;

    public List<Salary> findAll() {
        return salaryRepository.findAll();
    }

    public Optional<Salary> findById(Long id) {
        return salaryRepository.findById(id);
    }

    public Salary save(Salary salary) {
        return salaryRepository.save(salary);
    }

    public void deleteById(Long id) {
        salaryRepository.deleteById(id);
    }
}

