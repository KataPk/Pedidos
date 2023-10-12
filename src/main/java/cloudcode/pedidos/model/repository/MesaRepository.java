package cloudcode.pedidos.model.repository;

import cloudcode.pedidos.model.entity.Mesa;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    @Query(value = "SELECT COUNT(*) > 0 FROM MesasView WHERE mesasview.num_mesa = :numMesa", nativeQuery = true)
    Boolean existsByNumMesa(@Param("numMesa") int numMesa);

    @Query(value = "SELECT * FROM MesasView WHERE num_mesa = :numMesa", nativeQuery = true)
    Mesa findByNumMesa(@Param("numMesa") int numMesa);

    @Query(value = "SELECT * FROM MesasView WHERE mesasview.mstatus <> :status ORDER BY num_mesa", nativeQuery = true)
    List<Mesa> findAllByMStatusNotOrderByNumMesa(String status);

    @NotNull
    @Query(value = "SELECT * FROM MesasView", nativeQuery = true)
    List<Mesa> findAll();


    @Query(value = "REFRESH MATERIALIZED VIEW mesasview", nativeQuery = true)
    void updateMesasView();


}
