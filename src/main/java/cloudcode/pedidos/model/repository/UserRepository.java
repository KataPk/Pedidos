package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findAllByStatusUsuario(String status);
    User findByCpf(String cpf);

    User findByRg(String rg);

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);

    Boolean existsByRg(String rg);

}
