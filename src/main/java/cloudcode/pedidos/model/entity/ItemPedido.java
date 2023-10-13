package cloudcode.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Cacheable

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


    @ManyToOne
    @JoinColumn(name = "pedido", nullable = false)
    private Pedido pedido;


    public ItemPedido(Produto produto, int quantProduto, Pedido pedido) {
        this.produto = produto;
        this.quantProduto = quantProduto;

        this.pedido = pedido;
    }


    public ItemPedido() {

    }
}
