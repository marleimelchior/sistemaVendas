package springboot.svendas.controller;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.svendas.domain.DTO.PedidoDTO;
import springboot.svendas.domain.DTO.PedidoResponseDTO;
import springboot.svendas.domain.entity.Pedido;
import springboot.svendas.services.PedidoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody PedidoDTO pedidoDTO) {
        try {
            logger.info("Requisição recebida para salvar um novo pedido.");

            if (pedidoDTO.getCliente() == null || pedidoDTO.getItens() == null || pedidoDTO.getItens().isEmpty()) {
                logger.error("Dados do pedido inválidos: ID do cliente ou itens ausentes.");
                return ResponseEntity.badRequest().body("ID do cliente ou itens ausentes.");
            }

            Pedido pedido = pedidoService.salvar(pedidoDTO);
            PedidoResponseDTO responseDTO = pedidoService.converterParaResponseDTO(pedido);
            logger.info("Pedido salvo com sucesso com ID: {}", pedido.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            logger.error("Erro ao salvar pedido: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao salvar o pedido.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterPedidoPorId(@PathVariable Integer id) {
        logger.info("Requisição recebida para obter pedido com ID: {}", id);
        Optional<Pedido> pedidoOptional = pedidoService.obterPorId(id);
        if (pedidoOptional.isPresent()) {
            PedidoResponseDTO responseDTO = pedidoService.converterParaResponseDTO(pedidoOptional.get());
            logger.info("Pedido encontrado com ID: {}", id);
            return ResponseEntity.ok(responseDTO);
        } else {
            logger.warn("Pedido não encontrado com ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado.");
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> obterTodosPedidos() {
        logger.info("Requisição recebida para obter todos os pedidos.");
        List<Pedido> pedidos = pedidoService.obterTodos();
        List<PedidoResponseDTO> responseDTOs = pedidos.stream()
                .map(pedidoService::converterParaResponseDTO)
                .collect(Collectors.toList());
        logger.info("Total de pedidos encontrados: {}", responseDTOs.size());
        return ResponseEntity.ok(responseDTOs);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPedido(@PathVariable Integer id) {
        try {
            logger.info("Requisição recebida para cancelar pedido com ID: {}", id);
            pedidoService.cancelarPedido(id);
            logger.info("Pedido com ID: {} cancelado com sucesso.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao cancelar pedido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado ao cancelar pedido: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cancelar pedido.");
        }
    }

}
