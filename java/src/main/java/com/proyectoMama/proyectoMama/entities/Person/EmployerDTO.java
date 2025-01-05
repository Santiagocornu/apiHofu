package com.proyectoMama.proyectoMama.entities.Person;

import lombok.Data;

@Data
public class EmployerDTO {

    private Long id_person;
    private String nombre_person;
    private String apellido_person;
    private String telefono_person;
    private String legajo_Employer;
    private String gmail_Employer;
    private String turno_Employer;

    // Getters and Setters
    public Long getId_person() {
        return id_person;
    }

    public void setId_person(Long id_person) {
        this.id_person = id_person;
    }

    public String getNombre_person() {
        return nombre_person;
    }

    public void setNombre_person(String nombre_person) {
        this.nombre_person = nombre_person;
    }

    public String getApellido_person() {
        return apellido_person;
    }

    public void setApellido_person(String apellido_person) {
        this.apellido_person = apellido_person;
    }

    public String getTelefono_person() {
        return telefono_person;
    }

    public void setTelefono_person(String telefono_person) {
        this.telefono_person = telefono_person;
    }

    public String getLegajo_Employer() {
        return legajo_Employer;
    }

    public void setLegajo_Employer(String legajo_Employer) {
        this.legajo_Employer = legajo_Employer;
    }

    public String getGmail_Employer() {
        return gmail_Employer;
    }

    public void setGmail_Employer(String gmail_Employer) {
        this.gmail_Employer = gmail_Employer;
    }

    public String getTurno_Employer() {
        return turno_Employer;
    }

    public void setTurno_Employer(String turno_Employer) {
        this.turno_Employer = turno_Employer;
    }
}

