package com.example.pedidos.control.adm;

import com.example.pedidos.model.repository.UserRepository;
import com.example.pedidos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/api/admin/user")
public class FuncionarioController {

    @Autowired
    UserRepository userRepository;

    private final UserService userService;


    public FuncionarioController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/api/admin/funcionarios/createFuncionario")
    public RedirectView createFuncionario(
            @RequestParam ("nome") String nome,
            @RequestParam ("cpf") String cpf,
            @RequestParam ("rg") String rg,
            @RequestParam ("datanasc") String dataNasc,
            @RequestParam ("endereco") String logradouro,
            @RequestParam ("") String numResid,
            @RequestParam ("") String cep,
            @RequestParam ("") String cidade,
            @RequestParam ("") String uf,
            @Param ("")        String complemento,
            @RequestParam ("") String username,
            @RequestParam ("") String email,
            @RequestParam ("") String password
            ) {

        // Aqui você pode criar um novo funcionário usando os parâmetros recebidos
        // Certifique-se de realizar a validação e salvar o funcionário no banco de dados
        // Exemplo:
        // Funcionário funcionário = new Funcionário();
        // funcionário.setNome(nome);
        // funcionário.setCpf(cpf);
        // ...

        // Redirecionar para a página de funcionários após a criação
        return new RedirectView("/api/admin/funcionarios");
    }
}

















