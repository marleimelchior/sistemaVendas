package springboot.svendas.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.svendas.domain.DTO.ItemPedidoDTO;
import springboot.svendas.domain.DTO.PedidoDTO;
import springboot.svendas.domain.entity.Cliente;
import springboot.svendas.domain.entity.ItemPedido;
import springboot.svendas.domain.entity.Pedido;
import springboot.svendas.domain.entity.Produto;
import springboot.svendas.domain.repository.ClientesRepository;
import springboot.svendas.domain.repository.ItensPedidoRepository;
import springboot.svendas.domain.repository.PedidosRepository;
import springboot.svendas.domain.repository.ProdutosRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItensPedidoRepository itensPedidoRepository;

    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElse(null);

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemsPedido = converterItems(pedido, pedidoDTO.getItens());
        pedidosRepository.save(pedido);
        itensPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if(items.isEmpty()) {
            throw new IllegalArgumentException("Pedido não pode ser vazio");
        }

        return items.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository.findById(idProduto).orElse(null);

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setProduto(produto);
            return itemPedido;
        }).toList();
    };

}
