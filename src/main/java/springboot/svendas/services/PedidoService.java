package springboot.svendas.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.svendas.domain.DTO.*;
import springboot.svendas.domain.entity.Cliente;
import springboot.svendas.domain.entity.ItemPedido;
import springboot.svendas.domain.entity.Pedido;
import springboot.svendas.domain.entity.Produto;
import springboot.svendas.domain.repository.ClientesRepository;
import springboot.svendas.domain.repository.ItensPedidoRepository;
import springboot.svendas.domain.repository.PedidosRepository;
import springboot.svendas.domain.repository.ProdutosRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<Pedido> obterPorId(Integer id) {
        return pedidosRepository.findById(id);
    }

    public List<Pedido> obterTodos() {
        return pedidosRepository.findAll();
    }

    public PedidoResponseDTO converterParaResponseDTO( Pedido pedido ) {
        PedidoResponseDTO responseDTO = new PedidoResponseDTO();
        responseDTO.setCodigoPedido(pedido.getId());

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(pedido.getCliente().getId());
        clienteDTO.setNome(pedido.getCliente().getNome());
        responseDTO.setCliente(clienteDTO);

        responseDTO.setDataPedido(pedido.getDataPedido());

        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream().map(item -> {
            ItemPedidoResponseDTO itemDTO = new ItemPedidoResponseDTO();
            itemDTO.setId(item.getId());

            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setId(item.getProduto().getId());
            produtoDTO.setNome(item.getProduto().getNome());
            produtoDTO.setDescricao(item.getProduto().getDescricao());
            produtoDTO.setPreco(item.getProduto().getPreco());
            itemDTO.setProduto(produtoDTO);

            itemDTO.setQuantidade(item.getQuantidade());
            return itemDTO;
        }).toList();

        responseDTO.setItens(itensDTO);

        BigDecimal total = itensDTO.stream().map(item ->
                item.getProduto()
                        .getPreco()
                        .multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        responseDTO.setTotal(total);
        return  responseDTO;
    };

    @Transactional
    public void cancelarPedido(Integer id) {
        Optional<Pedido> pedidoOptional = pedidosRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedidosRepository.save(pedido);
        } else {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }
    }
}
