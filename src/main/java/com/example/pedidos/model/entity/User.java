package com.example.pedidos.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Funcionario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "funLogin")})

public class User {

//    public static final Logger log = LoggerFactory.getLogger(User.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Getter
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Getter
    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @Getter
    @Column(name = "rg", length = 12, nullable = false)
    private String rg;

    @Getter
    @Column(name = "dataNasc", nullable = false)
    private LocalDate dataNasc;

    @Getter
    @Column(name = "logradouro", length = 100, nullable = false)
    private String logradouro;

    @Getter
    @Column(name = "numResid", length = 10, nullable = false)
    private String numResid;

    @Getter
    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @Getter
    @Column(name = "cidade", length = 50, nullable = false)
    private String cidade;

    @Getter
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @Getter
    @Column(name = "complemento", length = 50, nullable = false)
    private String complemento;

    @Getter
    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Getter
    @Column(name = "funLogin", length = 100, nullable = false)
    private String email;



    @Getter
    @Column(name = "senha", length = 100, nullable = false)
    private String password;

    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FuncionarioAcesso",
            joinColumns = @JoinColumn(name = "Funcionario", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "Acesso", referencedColumnName = "Id"))
    private Set<Role> roles = new HashSet<>();

    @Getter
    @Column(name = "statusUsuario", length = 20, nullable = false )
    private String statusUsuario;

    public void setNome(String nome) {
            this.nome = nome;
        }

    public void setCpf(String cpf) {
            this.cpf = cpf;
        }

    public void setRg(String rg) {
            this.rg = rg;
        }

    public void setDataNasc(LocalDate dataNasc) {
            this.dataNasc = dataNasc;
        }

    public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

    public void setNumResid(String numResid) {
            this.numResid = numResid;
        }

    public void setCep(String cep) {
            this.cep = cep;
        }

    public void setCidade(String cidade) {
            this.cidade = cidade;
        }

    public void setUf(String uf) {
            this.uf = uf;
        }

    public void setComplemento(String complemento) {
            this.complemento = complemento;
        }






    public User(
            String nome, String cpf, String rg, LocalDate dataNasc, String logradouro, String numResid,
            String cep, String cidade, String uf, String complemento,
                String email, String username,
                       String senha,
                String statusUsuario
    ) {


        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNasc = dataNasc;
        this.logradouro = logradouro;
        this.numResid = numResid;
        this.cep = cep;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String funLogin) {
        this.email = funLogin;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setStatusUsuario(String statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
