package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.Categoria;
import cloudcode.pedidos.model.entity.Produto;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @NotNull
    @Query(value = "SELECT * FROM produtosview WHERE produtosview.categoria = :categoria AND produtosview.statusproduto = :status ", nativeQuery = true)
    List<Produto> findByCategoriaAndAndStatusProduto(@Param("categoria") Categoria categoria, @Param("status") String status);

    @Query(value = "SELECT * FROM produtosview WHERE produtosview.statusproduto = :status ", nativeQuery = true)
    List<Produto> findAllByStatusProduto(@Param("status") String status);

    @Query(value = "REFRESH MATERIALIZED VIEW produtosview", nativeQuery = true)
    void updateProdutosView();
}
