package com.example.pedidos.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Entity
@Table(name = "Categoria")
public class Categoria {

//    public static final Logger log = LoggerFactory.getLogger(Categoria.class);

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    @Column(name = "Nome", length = 20, nullable = false)
    private String nome;

    @Lob
    @Column(name = "Imagem", nullable = false)
    private String imagem;

    public Categoria(String nome, String imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Categoria() {
    }
}
