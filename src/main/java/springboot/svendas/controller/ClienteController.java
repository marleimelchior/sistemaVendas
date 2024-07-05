package springboot.svendas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springboot.svendas.domain.entity.Cliente;
import springboot.svendas.domain.repository.ClientesRepository;
import springboot.svendas.services.ClienteService;

import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> getClienteById(@PathVariable Integer id) {
        return clienteService.getClienteById(id);
    }

    @PostMapping("/cadastrar")
    @ResponseBody
    public ResponseEntity<Object> salvarCliente(@RequestBody Cliente cliente) {
        return clienteService.salvarCliente(cliente);
    }

    @DeleteMapping("/deletar/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        return clienteService.deletarCliente(id);
    }

    @PutMapping("/atualizar/{id}")
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return clienteService.atualizarCliente(id, cliente);
    }

    @GetMapping("/buscarTodos")
    @ResponseBody
    public ResponseEntity<Object> buscarTodos() {
        return clienteService.listarClientes();
    }
}
