package com.Smartplants.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Salud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estado;
}