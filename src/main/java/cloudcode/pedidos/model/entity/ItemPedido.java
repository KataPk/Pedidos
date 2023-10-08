package cloudcode.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "ItemPedido")
public class ItemPedido {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @ManyToOne
    @JoinColumn(name = "produto", nullable = false)
    private Produto produto;

    @Column(name = "quantProduto", nullable = false)
    private int quantProduto;

    @Column(name = "observacao", length = 100, nullable = false)
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "pedido", nullable = false)
    private Pedido pedido;


    public ItemPedido(Produto produto, int quantProduto, String observacao, Pedido pedido) {
        this.produto = produto;
        this.quantProduto = quantProduto;
        this.observacao = observacao;
        this.pedido = pedido;
    }


    public ItemPedido() {

    }
}
