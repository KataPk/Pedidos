package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "Produto")
public class Produto {

//    public static final Logger log = LoggerFactory.getLogger(Produto.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Version
    private long version;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "descricao", length = 400, nullable = false)
    private String descricao;

    @Column(name = "valor", precision = 5, scale = 2, nullable = false)
    private double valor;

    @Lob
    @Column(name = "imagem", nullable = false)
    private String imagem;
    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private Categoria categoria;

    public Produto(String nome, String descricao, double valor, String imagem, Categoria categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.imagem = imagem;
        this.categoria = categoria;
    }

    public Produto() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


}