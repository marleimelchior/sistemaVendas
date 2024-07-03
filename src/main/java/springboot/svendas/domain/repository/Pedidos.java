package springboot.svendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.svendas.domain.entity.Cliente;
import springboot.svendas.domain.entity.Pedido;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
