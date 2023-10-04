package com.example.pedidos.service;

import com.example.pedidos.dtos.CategoriaRecordDto;
import com.example.pedidos.model.entity.Categoria;
import com.example.pedidos.model.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {


    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaRecordDto> findAll(){
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoria -> new CategoriaRecordDto(categoria.getId(), categoria.getNome(), categoria.getImagem()))
                .collect(Collectors.toList());
    }

    public List<CategoriaRecordDto> findAllAtivos(){
        List<Categoria> categorias = categoriaRepository.findAllByStatusCategoria("ACTIVE");
        return categorias.stream()
                .map(categoria -> new CategoriaRecordDto(categoria.getId(), categoria.getNome(), categoria.getImagem()))
                .collect(Collectors.toList());
    }
}
