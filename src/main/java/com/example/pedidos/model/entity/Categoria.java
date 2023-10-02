package com.example.pedidos.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@Entity
@Table(name = "Categoria")
public class Categoria {

//    public static final Logger log = LoggerFactory.getLogger(Categoria.class);

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @Column(name = "Nome", length = 20, nullable = false)
    private String nome;

    @Lob
    @Column(name = "Imagem", nullable = false)
    private String imagem;

    @Column(name = "statusCategoria", nullable = true )
    private String statusCategoria;

    public Categoria(String nome, String imagem, String statusCategoria) {
        this.nome = nome;
        this.imagem = imagem;
        this.statusCategoria = statusCategoria;
    }




    public Categoria() {
    }
}
