package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Mesa")
public class Mesa {

//    public static final Logger log = LoggerFactory.getLogger(Mesa.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;



    @Column(name = "num_mesa", nullable = false)
    private Integer numMesa;

    @Column(name = "mstatus", nullable = false)
    private String MStatus;


}
