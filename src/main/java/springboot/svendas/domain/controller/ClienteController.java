//package springboot.svendas.domain.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import springboot.svendas.domain.entity.Cliente;
//import springboot.svendas.domain.repositorio.Clientes;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/clientes")
//public class ClienteController {
//
//    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
//
//    @Autowired
//    private Clientes clientes;
//
//    @PostMapping
//    public Cliente salvar(@RequestBody Cliente cliente) {
//        log.info("iniciando o processo de salvar o cliente: {}", cliente.getNome());
//        Cliente clienteSalvo = clientes.save(cliente);
//        if(clienteSalvo.getId() != null) {
//            log.info("cliente salvo com sucesso com o ID: {}", clienteSalvo.getId());
//        } else {
//            log.error("Erro ao salvar o cliente: {}", cliente.getNome());
//        }
//        return clienteSalvo;
//    }
//
//    @PutMapping
//    public Cliente atualizar(@RequestBody Cliente cliente) {
//        return clientes.save(cliente);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletar(@PathVariable Integer id) {
//        clientes.delete(clientes.findByNomeOrId("", id).get(0));
//    }
//
//    @GetMapping("/{nome}")
//    public List<Cliente> obterTodos( ) {
//        return clientes.findByNomeLike("");
//    }
//
//}
