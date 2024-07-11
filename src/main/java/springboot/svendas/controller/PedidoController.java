package springboot.svendas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springboot.svendas.domain.DTO.PedidoDTO;
import springboot.svendas.domain.entity.Pedido;
import springboot.svendas.services.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);
    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO pedidoDTO) {
        logger.info("Salvando pedido");
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }
}
