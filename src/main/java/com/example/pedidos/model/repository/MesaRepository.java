package com.example.pedidos.model.repository;

import com.example.pedidos.model.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    Mesa findByNumMesa(int numMesa);

}
