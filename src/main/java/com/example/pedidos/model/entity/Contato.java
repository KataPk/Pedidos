package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
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

    @Column(name = "telefone", length = 20, nullable = false)
    private String telefone;

    @Column(name = "email", length = 100)
    private String email;

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
