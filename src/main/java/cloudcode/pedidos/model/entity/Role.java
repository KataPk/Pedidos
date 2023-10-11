package cloudcode.pedidos.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Cacheable
@Table(name = "NivelAcesso")
public class Role {

//    public static final Logger log = LoggerFactory.getLogger(Role.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome", length = 20)
    private ERole name;


    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }


}

