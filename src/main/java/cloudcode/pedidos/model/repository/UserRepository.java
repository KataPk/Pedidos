package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

//   Optional <User> findByUsername(String username);
    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);

    Boolean existsByRg(String rg);

}
