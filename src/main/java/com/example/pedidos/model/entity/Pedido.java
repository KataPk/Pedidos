package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Getter
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


    public void setId(long id) {
        this.id = id;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setDtRegistro(LocalDateTime dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    public void setDtFechamento(LocalDateTime dtFechamento) {
        this.dtFechamento = dtFechamento;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
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
