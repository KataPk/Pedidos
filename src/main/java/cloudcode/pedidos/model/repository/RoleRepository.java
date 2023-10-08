package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.ERole;
import cloudcode.pedidos.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
