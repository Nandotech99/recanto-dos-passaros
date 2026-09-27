package com.example.Recando_dos_Passaros.Repository;

import com.example.Recando_dos_Passaros.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Integer> {
}
