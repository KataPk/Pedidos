package cloudcode.pedidos.control.adm;


import cloudcode.pedidos.dtos.CreateUserRecordDto;
import cloudcode.pedidos.model.entity.User;
import cloudcode.pedidos.model.repository.UserRepository;
import cloudcode.pedidos.service.UserService;
import jakarta.validation.Valid;
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


    @Autowired
    UserRepository userRepository;

    
    @PostMapping("/funcionario")
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRecordDto createUserRecordDto) {
        var user = new User();
        BeanUtils.copyProperties(createUserRecordDto, user);
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
                                             @RequestBody @Valid CreateUserRecordDto createUserRecordDto) {

        Optional<User> user0 = userRepository.findById(id);
        if (user0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
        }
        var userModel = user0.get();
        BeanUtils.copyProperties(createUserRecordDto, userModel);
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


}


