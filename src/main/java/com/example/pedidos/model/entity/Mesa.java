package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
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

    public void setId(long id) {
        this.id = id;
    }

    public void setNumMesa(Integer numMesa) {
        this.numMesa = numMesa;
    }

    public void setmMStatus(String mStatus) {
        this.MStatus = mStatus;
    }
}
