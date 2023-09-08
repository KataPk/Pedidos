package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Getter
@Entity
@Table(name = "ItemPedido")
public class ItemPedido {

//    public static final Logger log = LoggerFactory.getLogger(ItemPedido.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ipStatus", nullable = false)
    private int itemProdutoStatus;

    @ManyToOne
    @JoinColumn(name = "produto", nullable = false)
    private Produto produto;

    @Column(name = "quantProduto", nullable = false)
    private int quantProduto;

    @Column(name = "observacao", length = 100, nullable = false)
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "pedido", nullable = false)
    private Pedido pedido;


    public void setId(int id) {
        this.id = id;
    }


    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantProduto(int quantProduto) {
        this.quantProduto = quantProduto;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setItemProdutoStatus(int itemProdutoStatus) {
        this.itemProdutoStatus = itemProdutoStatus;
    }
}
