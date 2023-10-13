package cloudcode.pedidos.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Cacheable
@Entity(name = "Categoria")
@Table(name = "categoria")
public class Categoria {

//    public static final Logger log = LoggerFactory.getLogger(Categoria.class);

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "Nome", length = 20, nullable = false)
    private String nome;

    @Column(name = "Imagem", nullable = false)
    private String imagem;

    @Column(name = "statusCategoria", nullable = false)
    private String statusCategoria;

    public Categoria(String nome, String imagem, String statusCategoria) {
        this.nome = nome;
        this.imagem = imagem;
        this.statusCategoria = statusCategoria;
    }


    public Categoria() {
    }
}
