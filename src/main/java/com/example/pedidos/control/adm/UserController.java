package com.example.pedidos.control.adm;


import com.example.pedidos.dtos.UserRecordDto;
import com.example.pedidos.model.entity.User;
import com.example.pedidos.model.repository.UserRepository;
import com.example.pedidos.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@RequestMapping("/api/admin/user")
public class UserController {

//    public static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    private String serverMessage = null;
//    @GetMapping("/login")
//    public String login(ModelMap model) {
//        model.addAttribute("funcionario", new Funcionario());
//        model.addAttribute("serverMessage", serverMessage);
//        return "/login";
//    }




    @PostMapping("/funcionario")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        var user = new User();
        BeanUtils.copyProperties(userRecordDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/funcionario/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") long id) {

        Optional<User> user0 = userRepository.findById(id);
        return user0.<ResponseEntity<Object>>map(user -> ResponseEntity.status(HttpStatus.OK).body(user)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario não encontrado"));
    }


    @PutMapping("/funcionario/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") long id,
                                                    @RequestBody @Valid UserRecordDto userRecordDto) {

        Optional<User> user0 = userRepository.findById(id);
        if (user0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
        }
        var userModel = user0.get();
        BeanUtils.copyProperties(userRecordDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
    }

    @DeleteMapping("/funcionario/{id}")
    public ResponseEntity<Object> deleteFuncionario(@PathVariable(value = "id") long id) {
        Optional<User> user0 = userRepository.findById(id);
        if (user0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
        }
        userRepository.delete(user0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Funcionário deletado");
    }







//    @PostMapping("/login")
//    public String autenticarLogin(@RequestParam String username, @RequestParam String senha) {
//        FuncionarioRecordDto funcionario = new FuncionarioRecordDto();
//        funcionario.set(username);
//        funcionario.setSenha(senha);
//
//        boolean autenticado = funcionarioService.autenticarLogin(funcionario);
//
//        if (autenticado) {
//            return "redirect:/mesas";
//        } else {
//            return "redirect:/login?error";
//        }
//    }
}


