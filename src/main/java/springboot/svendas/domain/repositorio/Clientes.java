package springboot.svendas.domain.repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import springboot.svendas.domain.entity.Cliente;
import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {


    @Query(value = "select * from cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    //fazer uma query que delete um cliente pelo nome
    @Modifying
    @Query(value = "delete from cliente c where c.nome = :nome")
    void deleteByNome(String nome);


}
