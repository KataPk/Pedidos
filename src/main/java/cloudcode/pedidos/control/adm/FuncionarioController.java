package cloudcode.pedidos.control.adm;


import cloudcode.pedidos.dtos.UserRecordDto;
import cloudcode.pedidos.model.entity.ERole;
import cloudcode.pedidos.model.entity.Role;
import cloudcode.pedidos.model.entity.User;
import cloudcode.pedidos.model.repository.RoleRepository;
import cloudcode.pedidos.model.repository.UserRepository;
import cloudcode.pedidos.response.MessageResponse;
import cloudcode.pedidos.service.UserService;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.*;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('ADMIN')")

@RequestMapping("/api/admin/")
public class FuncionarioController {

    private final UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    public FuncionarioController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/funcionarios")
    public String Funcionarios(Model model,
                               @AuthenticationPrincipal UserDetails userDetails) {
        List<UserRecordDto> users = userService.findAtivos();

        ERole roleUser = ERole.ROLE_USER;
        ERole roleAdmin = ERole.ROLE_ADMIN;

        String currentUsername = userDetails.getUsername();


        model.addAttribute("currentUser", currentUsername);
        model.addAttribute("users", users);
        model.addAttribute("RoleUser", roleUser);
        model.addAttribute("RoleAdmin", roleAdmin);

        return "Adm/Funcionarios";
    }


    @Transactional
    @PostMapping("/funcionarios/createFuncionario")
    public ResponseEntity<?> createFuncionario(
            @RequestParam("nome") String nome,
            @RequestParam("cpf") String cpf,
            @RequestParam("rg") String rg,
            @RequestParam("dataNasc") String dataNasc,
            @RequestParam("endereco") String logradouro,
            @RequestParam("numResid") String numResid,
            @RequestParam("cep") String cep,
            @RequestParam("cidade") String cidade,
            @RequestParam("bairro") String bairro,
            @RequestParam("uf") String uf,
            @Param("complemento") String complemento,
            @RequestParam("emailRecup") String emailRecup,
            @RequestParam("username") String username,
            @RequestParam("senha") String password,
            @RequestParam("Role") String strRole

    ) {
        Map<String, Object> responseData = new HashMap<>();

        try {

            String cpfValue = cpf.replace(".", "").replace("-", "");
            String rgValue = rg.replace(".", "").replace("-", "");
            String cepValue = cep.replace("-", "");
            Set<Role> roles = new HashSet<>();
//            String tel1Value = tel1.replace("(", "").replace(")", "").replace("-", "");
//            String tel2Value = "";
//            String emailUserValue = "";

//            if (emailUser != null){
//                emailUserValue = emailUser;
//            }
//            if (tel2 != null) {
//                tel2Value = tel2.replace("(", "").replace(")", "").replace("-", "");
//            }

            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username já registrado!"));
            }
                if (userRepository.existsByEmail(emailRecup)) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Erro: Email já cadastrado no sistema!"));
                }
//            Verifica se o usuario já foi cadastrado anteriormente
                    if (userRepository.existsByCpf(cpf) && userRepository.existsByRg(rg)) {
                        User user1 = userRepository.findByCpf(cpf);
                        User user2 = userRepository.findByRg(rg);
//                checa se os dados são do usuário
                        if (user1 == user2) {
//                 se forem verdadeiros checa se o usuario já está ativo
                            if (user1.getStatusUsuario().equals("DELETADO")) {
                                user1.setNome(nome);
                                user1.setLogradouro(logradouro);
                                user1.setNumResid(numResid);
                                user1.setCep(cepValue);
                                user1.setBairro(bairro);
                                user1.setCidade(cidade);
                                user1.setUf(uf);
                                user1.setComplemento(complemento);
                                user1.setComplemento(emailRecup);
                                user1.setUsername(username);
                                user1.setPassword(encoder.encode(password));
                                user1.setStatusUsuario("ACTIVE");
                                userRepository.save(user1);

                                if (strRole.equals("admin")) {
                                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                    roles.add(adminRole);

                                } else {
                                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                    roles.add(userRole);
                                }


                                user1.setRoles(roles);

                                responseData.put("success", true);
                                responseData.put("message", "Usuário recadastrado com sucesso.");

                                return ResponseEntity.ok(responseData);
                            }
                        }
                        return ResponseEntity.badRequest().body(new MessageResponse("Erro: Os dados CPF e RG são de pessoas diferentes!"));
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
                    "ACTIVE"
            );
            userRepository.save(user);


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


//            Contato contato1 = new Contato(
//                    user,
//                    tel1Value,
//                    emailRecup
//            );
//                contatoRepository.save(contato1);
//            if (!Objects.equals(tel2Value, "") && !Objects.equals(emailUserValue, "")){
//
//                Contato contato2 = new Contato(
//                        user,
//                        tel2Value,
//                        emailUser
//                );
//
//                contatoRepository.save(contato2);
//
//            } else if (!Objects.equals(tel2Value, "")) {
//                Contato contato2 = new Contato(
//                        user,
//                        tel2Value,
//                        null
//                );
//
//                contatoRepository.save(contato2);
//
//
//            } else if (!Objects.equals(emailUserValue, "")) {
//                Contato contato2 = new Contato(
//                        user,
//                        null,
//                        emailUser
//                );
//                contatoRepository.save(contato2);
//
//            }
            responseData.put("success", true);
            responseData.put("message", "Usuário registrado com sucesso.");

            return ResponseEntity.ok(responseData);

        } catch (Exception e) {

            responseData.put("success", false);
            responseData.put("message", "Erro ao processar a solicitação.");


            return ResponseEntity.badRequest().body(responseData);
        }

    }


    @Transactional
    @PostMapping("/EditUsuario")
    public ResponseEntity<?> editUsuario(
            @RequestParam("id") long userId,
            @RequestParam("nome") String nome,
            @RequestParam("endereco") String logradouro,
            @RequestParam("numResid") String numResid,
            @RequestParam("cep") String cep,
            @RequestParam("cidade") String cidade,
            @RequestParam("bairro") String bairro,
            @RequestParam("uf") String uf,
            @Param("complemento") String complemento,
            @RequestParam("emailRecup") String emailRecup,
            @RequestParam("username") String username,
            @Param("Role") String strRole
    ) {
        // UserForm é uma classe que você deve criar para representar os campos do formulário
        Map<String, Object> responseData = new HashMap<>();

        try {

            String cepValue = cep.replace("-", "");
//            String tel1Value = "";
//            tel1Value = tel1.replace("(", "").replace(")", "").replace("-", "");
//            String tel2Value = "";
//            String emailUserValue = "";

            User editUser = userRepository.findById(userId).orElse(null);
            if (editUser == null) {
                // Trate o caso em que o usuário não existe
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Usuário não encontrado."));
            }

//            if (emailUser != null) {
//                emailUserValue = emailUser;
//            }
//            if (tel2 != null) {
//                tel2Value = tel2.replace("(", "").replace(")", "").replace("-", "");
//            }

            if (userRepository.existsByUsername(username) && !Objects.equals(editUser.getUsername(), username)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username já registrado!"));
            }
            if (userRepository.existsByEmail(emailRecup) && !Objects.equals(editUser.getEmail(), emailRecup)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email já registrado!"));

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

            if (strRole != null && !strRole.isEmpty())
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

//            contatoRepository.deleteByUser(editUser);

//            Contato contato1 = new Contato(
//                    editUser,
//                    tel1Value,
//                    emailRecup
//            );
//            contatoRepository.save(contato1);


//            if (!Objects.equals(tel2Value, "") && !Objects.equals(emailUserValue, "")) {
//
//                Contato contato2 = new Contato(
//                        editUser,
//                        tel2Value,
//                        emailUser
//                );
//
//                contatoRepository.save(contato2);
//
//            } else if (!Objects.equals(tel2Value, "")) {
//                Contato contato2 = new Contato(
//                        editUser,
//                        tel2Value,
//                        null
//                );
//
//                contatoRepository.save(contato2);
//            }

            responseData.put("success", true);
            responseData.put("message", "Usuário atualizado com sucesso.");

            return ResponseEntity.ok(responseData);

        } catch (Exception e) {
            responseData.put("success", false);
            responseData.put("message", "Erro ao processar a solicitação.");

            return ResponseEntity.badRequest().body(responseData);
        }


    }
    @Transactional
    @PostMapping("/deleteFuncionario")
    public RedirectView deleteUser(@RequestParam("userId") long id) {
        try {
            User user = userRepository.getReferenceById(id);

            user.setStatusUsuario("DELETADO");
            String userDeleted = "DELETADO" + user.getId();
            user.setUsername(userDeleted);
            user.setEmail(userDeleted);
            user.setPassword(userDeleted);
            user.getRoles().clear();
            userRepository.save(user);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        return new RedirectView("/api/admin/funcionarios");


    }

}


















