package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@Entity
@Table(name = "Contato")
public class Contato {

//    public static final Logger log = LoggerFactory.getLogger(Contato.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;



    @ManyToOne
    @JoinColumn(name = "funcionario")
    private User user;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "email", length = 100)
    private String email;


    public Contato(User user, String telefone, String email) {
        this.user = user;
        this.telefone = telefone;
        this.email = email;
    }

    public Contato(){}
}
