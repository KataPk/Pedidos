package com.example.pedidos.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Entity
@Table(name = "NivelAcesso")
public class Role {

//    public static final Logger log = LoggerFactory.getLogger(Role.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome",length = 20)
    private ERole name;


    public Role() {

    }
    public Role(ERole name) {
        this.name = name;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}

