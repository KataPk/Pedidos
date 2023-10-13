package cloudcode.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Cacheable
@Entity
@Table(name = "Categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "Nome", length = 100, nullable = false)
    private String nome;


    @Column(name = "Imagem", nullable = false)
    private String imagem;

    @Column(name = "StatusCategoria", nullable = false)
    private String statusCategoria;

    public Categoria(String nome, String imagem, String statusCategoria) {
        this.nome = nome;
        this.imagem = imagem;
        this.statusCategoria = statusCategoria;
    }

    public Categoria() {
    }
}
