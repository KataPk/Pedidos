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


    public List<ProdutoRecordDto> findByCategoria(Categoria categoria) {

        List<Produto> produtos = produtoRepository.findByCategoriaAndStatusProduto(categoria, "ACTIVE");

        return produtos.stream().map(produto -> new ProdutoRecordDto(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getImagem(),
                produto.getValor(),
                produto.getCategoria(),
                produto.getStatusProduto()
        )).collect(Collectors.toList());
    }

    public List<ProdutoRecordDto> findAtivos() {
        List<Produto> produtos = produtoRepository.findAllByStatusProdutoOrderByCategoria("ACTIVE");

        return produtos.stream()
                .map(produto -> new ProdutoRecordDto(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getImagem(),
                        produto.getValor(), produto.getCategoria(), produto.getStatusProduto()))
                .collect(Collectors.toList());

    }


}


