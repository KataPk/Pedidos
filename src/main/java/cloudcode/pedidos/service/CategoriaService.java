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

    public List<CategoriaRecordDto> findAll(){
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoria -> new CategoriaRecordDto(categoria.getId(), categoria.getNome(), categoria.getImagem()))
                .collect(Collectors.toList());
    }

    public List<CategoriaRecordDto> findAllAtivos(){
        List<Categoria> categorias = categoriaRepository.findAllByStatusCategoriaIs("ACTIVE");
        return categorias.stream()
                .map(categoria -> new CategoriaRecordDto(categoria.getId(), categoria.getNome(), categoria.getImagem()))
                .collect(Collectors.toList());
    }
}
