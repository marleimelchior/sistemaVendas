package springboot.svendas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.svendas.domain.entity.Cliente;
import springboot.svendas.domain.repository.Clientes;

import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    private static  final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);
        if( cliente.isPresent() ) {
            logger.info("Cliente com o id {} encontrado", id);
            return ResponseEntity.ok(cliente.get());
        }
        logger.info("Cliente com o id {} não encontrado", id);
        return ResponseEntity.notFound().header("Cliente não encontrado", "Cliente com o id " + id + " não encontrado").build();
    };

    @PostMapping("/cadastrar")
    @ResponseBody
    public ResponseEntity salvarCliente(@RequestBody Cliente cliente) {
        if(cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            logger.error("Nome do cliente não pode ser vazio");
            return ResponseEntity.badRequest().body("O corpo da solicitação deve conter pelo menos o nome do cliente");
        }
        Cliente clienteSalvo = clientes.save(cliente);
        logger.info("Cliente salvo com sucesso com o id {}", clienteSalvo.getId());
        return ResponseEntity.ok(clienteSalvo);
    }
}
