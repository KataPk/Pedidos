package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(value = "SELECT * FROM CategoriasView", nativeQuery = true)
    List<Categoria> findAllByStatusCategoriaIs(String status);

    @Query(value = "REFRESH MATERIALIZED VIEW CategoriasView", nativeQuery = true)
    void updateCategoriasView();

}