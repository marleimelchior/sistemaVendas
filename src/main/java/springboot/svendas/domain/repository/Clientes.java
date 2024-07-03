package springboot.svendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import springboot.svendas.domain.entity.Cliente;
import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {


    @Query(value = " select * from cliente c where c.nome like %:nome% ", nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    //fazer uma query que delete um cliente pelo nome
    @Modifying
    @Transactional
    @Query(value = "delete from cliente c where c.nome = :nome", nativeQuery = true)
    void deleteByNome(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos p where c.id = :id")
    Cliente findClientFetchPedidos(@Param("id") Integer id);

}
