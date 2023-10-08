package cloudcode.pedidos.model.repository;

import cloudcode.pedidos.model.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    Boolean existsByNumMesa(int numMesa);
    Mesa findByNumMesa(int numMesa);
    List<Mesa> findByMStatus(String status);
    List<Mesa> findByNumMesaAndMStatusNot(int numMesa, String status);

    List<Mesa> findAllByMStatusNotOrderByNumMesa(String status);

    List<Mesa> findAllByMStatusOrderByNumMesa(String status);


//    List<Integer> findNumeroMesasAtivas();

}
