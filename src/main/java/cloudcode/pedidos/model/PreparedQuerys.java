package cloudcode.pedidos.model;

import cloudcode.pedidos.model.entity.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PreparedQuerys {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Categoria> categoriasByStatus(String status) {
        TypedQuery<Categoria> query = entityManager.createQuery(
                "SELECT cv FROM Categoria cv WHERE statusCategoria = :status",
                Categoria.class
        );
        query.setParameter("status", status);

        return query.getResultList();
    }

    public List<Categoria> categorias(String status) {
        TypedQuery<Categoria> query = entityManager.createQuery(
                "SELECT cv FROM Categoria cv WHERE statusCategoria = :status",
                Categoria.class
        );
        query.setParameter("status", status);

        return query.getResultList();
    }

}
