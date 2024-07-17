package springboot.svendas.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {

    private Integer codigoPedido;
    private ClienteDTO cliente;
    private LocalDate dataPedido;
    private BigDecimal total;
    private List<ItemPedidoResponseDTO> itens;
}
