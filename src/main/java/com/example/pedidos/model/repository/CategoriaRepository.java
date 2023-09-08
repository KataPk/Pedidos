package com.example.pedidos.model.repository;

import com.example.pedidos.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {


    Categoria findByNome(String nome);

    Boolean existsByNome(String nome);




}