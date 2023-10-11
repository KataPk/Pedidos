package cloudcode.pedidos.service;


import cloudcode.pedidos.dtos.ProdutoRecordDto;
import cloudcode.pedidos.model.entity.Categoria;
import cloudcode.pedidos.model.entity.Produto;
import cloudcode.pedidos.model.repository.CategoriaRepository;
import cloudcode.pedidos.model.repository.MesaRepository;
import cloudcode.pedidos.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;


    @Autowired
    MesaRepository mesaRepository;


    public List<ProdutoRecordDto> findAll() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> new ProdutoRecordDto(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getImagem(),
                        produto.getValor(), produto.getCategoria()))
                .collect(Collectors.toList());

    }

    public List<ProdutoRecordDto> findByCategoria(Categoria categoria) {

        List<Produto> produtos = produtoRepository.findByCategoriaAndAndStatusProduto(categoria, "ACTIVE");

        return produtos.stream().map(produto -> new ProdutoRecordDto(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getImagem(),
                produto.getValor(),
                produto.getCategoria()
        )).collect(Collectors.toList());
    }

    public List<ProdutoRecordDto> findAtivos() {
        List<Produto> produtos = produtoRepository.findAllByStatusProduto("ACTIVE");
        produtos.sort(Comparator.comparing(produto -> produto.getCategoria().getId()));

        return produtos.stream()
                .map(produto -> new ProdutoRecordDto(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getImagem(),
                        produto.getValor(), produto.getCategoria()))
                .collect(Collectors.toList());

    }

}


