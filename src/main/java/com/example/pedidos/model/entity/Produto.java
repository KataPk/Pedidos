package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Produto")
public class Produto {

//    public static final Logger log = LoggerFactory.getLogger(Produto.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;



    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 400, nullable = false)
    private String descricao;

    @Column(name = "valor", precision = 5, scale = 2, nullable = false)
    private double valor;

    @Column(name = "statusProduto", length = 20, nullable = false)
    private String statusProduto;


    @Lob
    @Column(name = "imagem", nullable = false)
    private String imagem;
    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private Categoria categoria;

    public Produto(String nome, String descricao, double valor, String imagem, Categoria categoria, String statusProduto) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.imagem = imagem;
        this.categoria = categoria;
        this.statusProduto = statusProduto;
    }

    public Produto() {

    }




}