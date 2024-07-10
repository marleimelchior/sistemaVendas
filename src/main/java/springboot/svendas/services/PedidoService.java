package springboot.svendas.services;

import org.springframework.stereotype.Service;
import springboot.svendas.domain.repository.PedidosRepository;

@Service
public class PedidoService {

    private final PedidosRepository pedidosRepository;

    public PedidoService(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }
}
