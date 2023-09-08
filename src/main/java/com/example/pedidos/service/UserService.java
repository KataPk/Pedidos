package com.example.pedidos.service;

import com.example.pedidos.dtos.UserRecordDto;
import com.example.pedidos.model.entity.Role;
import com.example.pedidos.model.entity.User;
import com.example.pedidos.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService {

//        Código do professor Leandro Cruz para Login
        //    @Transactional
//    public int acessar(String funLogin, String senha) {
//
//        Funcionario funcionario = funcionarioRepository.findByFunLogin(funLogin);
//
//        if (funcionario != null && funcionario.getStatusUsuario().equals("ATIVO")) {
//
//            if (funcionario.getSenha().equals(senha)) {
//
//                if (funcionario.getNivelAcesso().equals("USER")) {
//                    return 1;
//                } else if (funcionario.getNivelAcesso().equals("ADMIN")) {
//                    return 2;
//                }
//            } else {
//                return 0;
//            }
//        }
//        return 0;
//    }



    @Autowired
    UserRepository userRepository;

    public List<UserRecordDto> findAll(){
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(user -> new UserRecordDto(
                            user.getNome(),user.getCpf(),
                            user.getRg(),user.getDataNasc(),
                            user.getLogradouro(),user.getNumResid(),
                            user.getCep(),user.getCidade(),
                            user.getUf(),user.getComplemento(),
                            user.getUsername(),
                            user.getEmail(),user.getPassword(),
                    user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()),
                            user.getStatusUsuario()
                    ))
                    .collect(Collectors.toList());
        }


}
