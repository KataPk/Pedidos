package cloudcode.pedidos.model.repository;


import cloudcode.pedidos.model.entity.Categoria;
import cloudcode.pedidos.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProdutoRepository extends JpaRepository<Produto, Long> {


    List<Produto> findByCategoria(Categoria categoria);

    List<Produto> findAllByStatusProduto(String status);

    Produto findByNome(String nome);

}
