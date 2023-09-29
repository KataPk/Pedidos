package com.example.pedidos.service;

import com.example.pedidos.dtos.CreateUserRecordDto;
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




    @Autowired
    UserRepository userRepository;

    public List<CreateUserRecordDto> findAllCreate(){
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(user -> new CreateUserRecordDto(
                            user.getNome(),user.getCpf(),
                            user.getRg(),user.getDataNasc(),
                            user.getLogradouro(),user.getNumResid(),
                            user.getCep(), user.getBairro(), user.getCidade(),
                            user.getUf(),user.getComplemento(),
                            user.getUsername(),
                            user.getEmail(),user.getPassword(),
                    user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()),
                            user.getStatusUsuario()
                    ))
                    .collect(Collectors.toList());
        }

    public List<UserRecordDto> findAll(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserRecordDto(
                        user.getId(),
                        user.getNome(),user.getCpf(),
                        user.getRg(),user.getDataNasc(),
                        user.getLogradouro(),user.getNumResid(),
                        user.getCep(), user.getBairro(), user.getCidade(),
                        user.getUf(),user.getComplemento(),
                        user.getUsername(),
                        user.getEmail(),user.getPassword(),
                        user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()),
                        user.getStatusUsuario()
                ))
                .collect(Collectors.toList());
    }


}
