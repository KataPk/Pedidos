package com.example.pedidos.model.repository;

import com.example.pedidos.model.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    Mesa findByNumMesa(int numMesa);
    List<Mesa> findByMStatus(String status);
    List<Mesa> findByNumMesaAndMStatusNot(int numMesa, String status);

    @Query("SELECT m.numMesa from Mesa m where m.mStatus <> 'INATIVA' ")
    List<Integer> findNumeroMesasAtivas();

}
