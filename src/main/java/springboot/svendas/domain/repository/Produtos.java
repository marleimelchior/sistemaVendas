package springboot.svendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.svendas.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
