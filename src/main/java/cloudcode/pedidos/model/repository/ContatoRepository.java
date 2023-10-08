package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.Contato;
import cloudcode.pedidos.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {


    void deleteByUser(User user);

    List<Contato> findByUserId(Long userId);

    List<Contato> findByUser(User user);
}
