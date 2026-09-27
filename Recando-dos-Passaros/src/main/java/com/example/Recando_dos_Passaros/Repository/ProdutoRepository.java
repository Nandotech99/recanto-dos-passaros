package com.example.Recando_dos_Passaros.Repository;

import com.example.Recando_dos_Passaros.Model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Integer> {
}
