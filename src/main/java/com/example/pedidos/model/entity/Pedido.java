package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Pedido")
public class Pedido {

//    public static final Logger log = LoggerFactory.getLogger(Pedido.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;



    @Column(name = "nomeCliente", length = 12, nullable = false)
    private String nomeCliente;

    @Column(name = "dtRegistro")
    private LocalDateTime dtRegistro;

    @Column(name = "dtFechamento")
    private LocalDateTime dtFechamento;

    @ManyToOne
    @JoinColumn(name = "funcionario", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "mesa", nullable = false)
    private Mesa mesa;

    @Column(name = "statusPedido", nullable = false, length = 20)
    private String statusPedido;

    public Pedido() {

    }




    public Pedido(String nomeCliente, LocalDateTime dtRegistro, LocalDateTime dtFechamento, User user, Mesa mesa, String statusPedido) {
        this.nomeCliente = nomeCliente;
        this.dtRegistro = dtRegistro;
        this.dtFechamento = dtFechamento;
        this.user = user;
        this.mesa = mesa;
        this.statusPedido = statusPedido;
    }
}
