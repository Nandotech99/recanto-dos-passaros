package com.example.Recando_dos_Passaros.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "produto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String nome;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private int quantidadeMinima;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private String registro;

}
