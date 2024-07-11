package springboot.svendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.svendas.domain.entity.ItemPedido;

public interface ItensPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
