package com.example.pedidos.control.adm;

import com.example.pedidos.model.entity.ERole;
import com.example.pedidos.model.entity.Role;
import com.example.pedidos.model.entity.User;
import com.example.pedidos.model.repository.RoleRepository;
import com.example.pedidos.model.repository.UserRepository;
import com.example.pedidos.payload.response.MessageResponse;
import com.example.pedidos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/api/admin/")
public class FuncionarioController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    PasswordEncoder encoder;

    public FuncionarioController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/funcionarios/createFuncionario")
    public ResponseEntity<?> createFuncionario(
            @RequestParam ("nome")          String nome,
            @RequestParam ("cpf")           String cpf,
            @RequestParam ("rg")            String rg,
            @RequestParam ("dataNasc")      String dataNasc,
            @RequestParam ("endereco")      String logradouro,
            @RequestParam ("numResid")      String numResid,
            @RequestParam ("cep")           String cep,
            @RequestParam ("cidade")        String cidade,
            @RequestParam ("bairro")        String bairro,
            @RequestParam ("uf")            String uf,
            @Param ("complemento")          String complemento,
            @RequestParam ("telefone1")     String tel1,
            @Param ("telefone2")            String tel2,
            @RequestParam ("emailRecup")    String emailRecup,
            @RequestParam ("emailUsuario")  String emailUser,
            @RequestParam ("username")      String username,
            @RequestParam ("senha")         String password,
            @RequestParam ("Role")          String strRole

            ) {
        try {
            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }
            if (userRepository.existsByCpf(cpf) || userRepository.existsByRg(rg)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: User is already in system!"));
            }
        String cpfValue = cpf.replace(".", "").replace("-", "");
        String rgValue = rg.replace(".", "").replace("-", "");
        String cepValue = cep.replace("-", "");


        User user = new User(
                nome,
                cpfValue,
                rgValue,
                LocalDate.parse(dataNasc),
                logradouro,
                numResid,
                cepValue,
                bairro,
                cidade,
                uf,
                complemento,
                username,
                emailRecup,
                encoder.encode(password),
                "ATIVO"
        );

            Set<Role> roles = new HashSet<>();


                if (strRole.equals("admin")) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);

                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }

            user.setRoles(roles);





            userRepository.save(user);





            } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity.badRequest().build() ;
    }
}

















