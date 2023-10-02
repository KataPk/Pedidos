package com.example.pedidos.control.adm;

import com.example.pedidos.dtos.UserRecordDto;
import com.example.pedidos.model.entity.Contato;
import com.example.pedidos.model.entity.ERole;
import com.example.pedidos.model.entity.Role;
import com.example.pedidos.model.entity.User;
import com.example.pedidos.model.repository.ContatoRepository;
import com.example.pedidos.model.repository.RoleRepository;
import com.example.pedidos.model.repository.UserRepository;
import com.example.pedidos.payload.response.MessageResponse;
import com.example.pedidos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/api/admin/")
public class FuncionarioController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ContatoRepository contatoRepository;

    @Autowired
    PasswordEncoder encoder;
    private final UserService userService;

    public FuncionarioController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/funcionarios")
    public String funcionarios(Model model,
                               @AuthenticationPrincipal UserDetails userDetails){
        List<UserRecordDto> users = userService.findAll();

        ERole roleUser = ERole.ROLE_USER;
        ERole roleAdmin = ERole.ROLE_ADMIN;

        String currentUsername = userDetails.getUsername();


        model.addAttribute("currentUser", currentUsername);
        model.addAttribute("users", users);
        model.addAttribute("RoleUser", roleUser);
        model.addAttribute("RoleAdmin", roleAdmin);

        return "Adm/Funcionarios";
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
            @Param ("telefoneUser2")            String tel2,
            @RequestParam ("emailRecup")    String emailRecup,
            @Param ("emailUser")         String emailUser,
            @RequestParam ("username")      String username,
            @RequestParam ("senha")         String password,
            @RequestParam ("Role")          String strRole

            ) {
        Map<String, Object> responseData = new HashMap<>();

        try {

            String cpfValue = cpf.replace(".", "").replace("-", "");
            String rgValue = rg.replace(".", "").replace("-", "");
            String cepValue = cep.replace("-", "");
            String tel1Value = tel1.replace("(", "").replace(")", "").replace("-", "");
            String tel2Value = "";
            String emailUserValue = "";

            if (emailUser != null){
                emailUserValue = emailUser;
            }
            if (tel2 != null) {
                tel2Value = tel2.replace("(", "").replace(")", "").replace("-", "");
            }

            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username já registrado!"));
            }

            if (userRepository.existsByEmail(emailRecup)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Erro: Email já cadastrado no sistema!"));
            }
            if (userRepository.existsByCpf(cpfValue) || userRepository.existsByRg(rgValue)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Erro: Usuário já cadastrado no sistema!"));
            }


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
                emailRecup,
                username,
                encoder.encode(password),
                "ATIVO"
        );
            userRepository.save(user);

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


            Contato contato1 = new Contato(
                    user,
                    tel1Value,
                    emailRecup
            );
                contatoRepository.save(contato1);


            if (!Objects.equals(tel2Value, "") && !Objects.equals(emailUserValue, "")){

                Contato contato2 = new Contato(
                        user,
                        tel2Value,
                        emailUser
                );

                contatoRepository.save(contato2);

            } else if (!Objects.equals(tel2Value, "")) {
                Contato contato2 = new Contato(
                        user,
                        tel2Value,
                        null
                );

                contatoRepository.save(contato2);




            } else if (!Objects.equals(emailUserValue, "")) {
                Contato contato2 = new Contato(
                        user,
                        null,
                        emailUser
                );
                contatoRepository.save(contato2);

            }
            responseData.put("success", true);
            responseData.put("message", "Usuário registrado com sucesso.");

            return ResponseEntity.ok(responseData);

            } catch (Exception e){

            responseData.put("success", false);
            responseData.put("message", "Erro ao processar a solicitação.");


            return ResponseEntity.badRequest().body(responseData);
        }

    }


    @PostMapping("/EditUsuario")
    public ResponseEntity<?> editUsuario(
            @RequestParam ("id")            long userId,
            @RequestParam ("nome")          String nome,
            @RequestParam ("endereco")      String logradouro,
            @RequestParam ("numResid")      String numResid,
            @RequestParam ("cep")           String cep,
            @RequestParam ("cidade")        String cidade,
            @RequestParam ("bairro")        String bairro,
            @RequestParam ("uf")            String uf,
            @Param ("complemento")          String complemento,
            @RequestParam ("telefone1")     String tel1,
            @Param ("telefoneUser2")            String tel2,
            @RequestParam ("emailRecup")    String emailRecup,
            @Param ("emailUser")         String emailUser,
            @RequestParam ("username")      String username,
            @RequestParam ("Role")          String strRole
    ) {
        // UserForm é uma classe que você deve criar para representar os campos do formulário
        Map<String, Object> responseData = new HashMap<>();

        try {

            String cepValue = cep.replace("-", "");
            String tel1Value = "";
            tel1Value = tel1.replace("(", "").replace(")", "").replace("-", "");
            String tel2Value = "";
            String emailUserValue = "";

            User editUser = userRepository.findById(userId).orElse(null);
            if (editUser == null) {
                // Trate o caso em que o usuário não existe
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Usuário não encontrado."));
            }

            if (emailUser != null) {
                emailUserValue = emailUser;
            }
            if (tel2 != null) {
                tel2Value = tel2.replace("(", "").replace(")", "").replace("-", "");
            }

            if (!emailRecup.equals(editUser.getEmail()) && userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username já registrado!"));
            }



            editUser.setNome(nome);
            editUser.setLogradouro(logradouro);
            editUser.setNumResid(numResid);
            editUser.setCep(cepValue);
            editUser.setBairro(bairro);
            editUser.setCidade(cidade);
            editUser.setUf(uf);
            editUser.setComplemento(complemento);
            editUser.setEmail(emailRecup);
            editUser.setUsername(username);


            userRepository.save(editUser);


            editUser.getRoles().clear();
            Set<Role> roles = new HashSet<>();
            if ("admin".equals(strRole)) {
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
            } else {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
            editUser.setRoles(roles);

            contatoRepository.deleteByUser(editUser);


            Contato contato1 = new Contato(
                    editUser,
                    tel1Value,
                    emailRecup
            );
            contatoRepository.save(contato1);


            if (!Objects.equals(tel2Value, "") && !Objects.equals(emailUserValue, "")) {

                Contato contato2 = new Contato(
                        editUser,
                        tel2Value,
                        emailUser
                );

                contatoRepository.save(contato2);

            } else if (!Objects.equals(tel2Value, "")) {
                Contato contato2 = new Contato(
                        editUser,
                        tel2Value,
                        null
                );

                contatoRepository.save(contato2);
            }

            responseData.put("success", true);
            responseData.put("message", "Usuário atualizado com sucesso.");

            return ResponseEntity.ok(responseData);

        } catch (Exception e) {
            responseData.put("success", false);
            responseData.put("message", "Erro ao processar a solicitação.");

            return ResponseEntity.badRequest().body(responseData);
        }


    }

    @PostMapping("/deleteFuncionario")
    public ResponseEntity<?> deleteUser(@RequestParam ("id") long id) {
        Map<String, Object> responseData = new HashMap<>();
        try {
            User user = userRepository.findById(id).orElse(null);

            if (user == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Usuário não encontrado."));
            }

            userRepository.delete(user);


            responseData.put("success", true);
            responseData.put("message", "Usuário registrado com sucesso.");

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {

            responseData.put("success", false);
            responseData.put("message", "Erro ao processar a solicitação.");


            return ResponseEntity.badRequest().body(responseData);
        }

    }

}


















