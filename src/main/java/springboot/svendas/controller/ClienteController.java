package springboot.svendas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.svendas.domain.entity.Cliente;
import springboot.svendas.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClienteById(@PathVariable Integer id) {
        return clienteService.getClienteById(id);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> salvarCliente(@RequestBody Cliente cliente) {
        return clienteService.salvarCliente(cliente);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return clienteService.deletarCliente(id);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return clienteService.atualizarCliente(id, cliente);
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<Object> buscarTodos() {
        return clienteService.listarClientes();
    }

    //filtro usando o query params
    @GetMapping("/buscarPorNome")
    public ResponseEntity<Object> buscarPorNome(@RequestParam String nome) {
        return clienteService.buscarPorNome(nome);
    }
}
