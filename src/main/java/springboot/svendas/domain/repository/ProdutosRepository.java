package springboot.svendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot.svendas.domain.entity.Cliente;
import springboot.svendas.domain.entity.Produto;

import java.util.List;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {

    @Query(value = " select * from produto p where p.nome like %:nome% ", nativeQuery = true)
    List<Produto> encontrarPorNome(@Param("nome") String nome);

}
