package cloudcode.pedidos.service;


import cloudcode.pedidos.dtos.CategoriaRecordDto;
import cloudcode.pedidos.model.entity.Categoria;
import cloudcode.pedidos.model.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {

        this.categoriaRepository = categoriaRepository;
    }


    public List<CategoriaRecordDto> findAllAtivos() {

        long startTime = System.currentTimeMillis();

        List<Categoria> categorias = categoriaRepository.findAllByStatusCategoriaIs("ACTIVE");
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Tempo total da consulta: " + elapsedTime + " milissegundos");
        return categorias.stream()
                .map(categoria -> new CategoriaRecordDto(categoria.getId(), categoria.getNome(), categoria.getImagem(), categoria.getStatusCategoria()))
                .collect(Collectors.toList());
    }


}
