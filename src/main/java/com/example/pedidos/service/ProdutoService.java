package com.example.pedidos.service;


import com.example.pedidos.dtos.PedidoRecordDTO;
import com.example.pedidos.dtos.ProdutoRecordDto;
import com.example.pedidos.model.entity.Categoria;
import com.example.pedidos.model.entity.Pedido;
import com.example.pedidos.model.entity.Produto;
import com.example.pedidos.model.repository.CategoriaRepository;
import com.example.pedidos.model.repository.MesaRepository;
import com.example.pedidos.model.repository.PedidoRepository;
import com.example.pedidos.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;



    @Autowired
    MesaRepository mesaRepository;


    public List<ProdutoRecordDto> findAll(){
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> new ProdutoRecordDto(produto.getNome(), produto.getDescricao(), produto.getImagem(),
                        produto.getValor(), produto.getCategoria()))
                .collect(Collectors.toList());

    }
    public List<ProdutoRecordDto> findByCategoria(String categoriaNome){
        Categoria categoria = categoriaRepository.findByNome(categoriaNome);

            List<Produto> produtos = produtoRepository.findByCategoria(categoria);

            return produtos.stream().map(produto -> new ProdutoRecordDto(
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getImagem(),
                    produto.getValor(),
                    produto.getCategoria()
                    )).collect(Collectors.toList());
        }




    }


