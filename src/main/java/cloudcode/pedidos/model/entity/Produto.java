package cloudcode.pedidos.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Cacheable
@Table(name = "Produto")
public class Produto {

//    public static final Logger log = LoggerFactory.getLogger(Produto.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;


    @Column(name = "descricao", length = 400, nullable = false)
    private String descricao;

    @Column(name = "valor", precision = 7, scale = 2, nullable = false)
    private double valor;

    @Column(name = "statusProduto", length = 20, nullable = false)
    private String statusProduto;

    @Column(name = "imagem", nullable = false)
    private String imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria", nullable = false)
    private Categoria categoria;

    public Produto(String nome, String descricao, double valor, String imagem, Categoria categoria, String statusProduto) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.imagem = imagem;
        this.categoria = categoria;
        this.statusProduto = statusProduto;
    }

    public Produto() {

    }


}