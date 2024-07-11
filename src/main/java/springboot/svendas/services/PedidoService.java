package springboot.svendas.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.svendas.domain.repository.ClientesRepository;
import springboot.svendas.domain.repository.ItensPedidoRepository;
import springboot.svendas.domain.repository.PedidosRepository;
import springboot.svendas.domain.repository.ProdutosRepository;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItensPedidoRepository itensPedidoRepository;

}
