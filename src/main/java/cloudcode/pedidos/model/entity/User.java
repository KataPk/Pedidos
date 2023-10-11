package cloudcode.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Funcionario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "funLogin")})
@Getter
@Setter
public class User {

    //    public static final Logger log = LoggerFactory.getLogger(User.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;


    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @Column(name = "rg", length = 12, nullable = false)
    private String rg;

    @Column(name = "dataNasc", nullable = false)
    private LocalDate dataNasc;

    @Column(name = "logradouro", length = 100, nullable = false)
    private String logradouro;

    @Column(name = "numResid", length = 10, nullable = false)
    private String numResid;

    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @Column(name = "bairro", length = 50, nullable = false)
    private String bairro;
    @Column(name = "cidade", length = 50, nullable = false)
    private String cidade;

    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @Column(name = "complemento", length = 50, nullable = false)
    private String complemento;

    @Column(name = "username", length = 20, nullable = false)
    private String username;


    @Column(name = "funLogin", length = 100, nullable = false)
    private String email;


    @Column(name = "senha", length = 100, nullable = false)
    private String password;

    @Column(name = "statususuario", length = 25, nullable = false)
    private String statusUsuario;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FuncionarioAcesso",
            joinColumns = @JoinColumn(name = "Funcionario", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "Acesso", referencedColumnName = "Id"))
    private Set<Role> roles = new HashSet<>();


    public User(
            String nome, String cpf, String rg, LocalDate dataNasc, String logradouro, String numResid,
            String cep, String bairro, String cidade, String uf, String complemento,
            String email, String username,
            String senha, String statusUsuario
    ) {


        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNasc = dataNasc;
        this.logradouro = logradouro;
        this.numResid = numResid;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.complemento = complemento;
        this.username = username;
        this.email = email;
        this.password = senha;
        this.statusUsuario = statusUsuario;
    }

    public User() {

    }


}
