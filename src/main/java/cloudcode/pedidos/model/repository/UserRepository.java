package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query(value = "SELECT * FROM funcionariosview WHERE funcionariosview.statususuario = :status", nativeQuery = true)
    List<User> findAllByStatusUsuario(@Param("status") String status);

    @Query(value = "SELECT * FROM funcionariosview WHERE funcionariosview.cpf = :cpf", nativeQuery = true)
    User findByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM funcionariosview WHERE funcionariosview.rg = :rg", nativeQuery = true)
    User findByRg(@Param("rg") String rg);

    @Query(value = "SELECT * FROM funcionariosview WHERE funcionariosview.username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

    @Query(value = "SELECT COUNT(*) > 0 FROM funcionariosview WHERE funcionariosview.username = :username", nativeQuery = true)
    Boolean existsByUsername(@Param("username") String username);

    @Query(value = "SELECT COUNT(*) > 0 FROM funcionariosview WHERE funcionariosview.funlogin = :email", nativeQuery = true)
    Boolean existsByEmail(@Param("email") String email);

    @Query(value = "SELECT COUNT(*) > 0 FROM funcionariosview WHERE funcionariosview.cpf = :cpf", nativeQuery = true)
    Boolean existsByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT COUNT(*) > 0 FROM funcionariosview WHERE funcionariosview.rg = :rg", nativeQuery = true)
    Boolean existsByRg(@Param("rg") String rg);

    @Query(value = "REFRESH MATERIALIZED VIEW funcionariosview", nativeQuery = true)
    void updateFuncionariosView();

}
