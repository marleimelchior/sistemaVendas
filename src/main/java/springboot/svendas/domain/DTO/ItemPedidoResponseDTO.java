package springboot.svendas.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemPedidoResponseDTO {

    private Integer id;
    private ProdutoDTO produto;
    private Integer quantidade;
}
