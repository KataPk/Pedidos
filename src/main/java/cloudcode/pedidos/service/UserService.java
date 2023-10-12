package cloudcode.pedidos.service;


import cloudcode.pedidos.dtos.UserRecordDto;
import cloudcode.pedidos.model.entity.Role;
import cloudcode.pedidos.model.entity.User;
import cloudcode.pedidos.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;


    public List<UserRecordDto> findAtivos() {
        List<User> users = userRepository.findAllByStatusUsuario("ACTIVE");

        return users.stream()
                .map(user ->
                        new UserRecordDto(
                                user.getId(),
                                user.getNome(),
                                user.getCpf(),
                                user.getRg(),
                                user.getDataNasc(),
                                user.getLogradouro(),
                                user.getNumResid(),
                                user.getCep(),
                                user.getBairro(),
                                user.getCidade(),
                                user.getUf(),
                                user.getComplemento(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getPassword(),
                                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))


                ).collect(Collectors.toList());


    }

    @Scheduled(fixedRate = 60000)
    public void updateFuncionariosView() {

        userRepository.updateFuncionariosView();
    }
}
