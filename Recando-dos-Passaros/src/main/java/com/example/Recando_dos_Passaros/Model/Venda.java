package com.example.Recando_dos_Passaros.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venda")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private double valorTotal;
    @Column
    private LocalDate date;

    @OneToMany(mappedBy = "idItem",fetch = FetchType.LAZY)
    private List<Item> items=new ArrayList<>();
}
