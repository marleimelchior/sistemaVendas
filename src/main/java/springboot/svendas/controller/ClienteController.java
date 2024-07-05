package springboot.svendas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);
        if (cliente.isPresent()) {
            logger.info("Cliente com o id {} encontrado", id);
            return ResponseEntity.ok(cliente.get());
        } else {
            logger.info("Cliente com o id {} não encontrado", id);
            String mensagemErro = String.format("Cliente com o id %d não encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
    }

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

    //implementar o método de deletar um cliente pelo id
    @DeleteMapping("/deletar/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);
        if (cliente.isPresent()) {
            clientes.delete(cliente.get());
            logger.info("Cliente com o id {} deletado com sucesso", id);
            String mensagem = String.format("Cliente com o id %d deletado com sucesso", id);
            return ResponseEntity.ok(mensagem);
        } else {
            logger.info("Cliente com o id {} não encontrado", id);
            String mensagemErro = String.format("Cliente com o id %d não encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
    }

    //implementar o método para atualizar um cliente
    @PutMapping("/atualizar/{id}")
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Optional<Cliente> clienteOptional = clientes.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente clienteExistente = clienteOptional.get();
            cliente.setId(clienteExistente.getId());
            clienteExistente.setNome(cliente.getNome());
            clientes.save(clienteExistente);
            logger.info("Cliente com o id {} atualizado com sucesso", id);
            return ResponseEntity.ok(clienteExistente);
        } else {
            logger.info("Cliente com o id {} não encontrado", id);
            String mensagemErro = String.format("Cliente com o id %d não encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
    }

    //implementar o método para buscar todos os clientes pelo id
    @GetMapping("/buscarTodos")
    @ResponseBody
    public ResponseEntity<Object> buscarTodos() {
        return ResponseEntity.ok(clientes.findAll());
    }
}
