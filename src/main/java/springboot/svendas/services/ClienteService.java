package springboot.svendas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springboot.svendas.domain.entity.Cliente;
import springboot.svendas.domain.repository.ClientesRepository;

import java.util.Optional;

@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private final ClientesRepository clientesRepository;

    public ClienteService(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    //metodo para buscar um cliente pelo id
    public ResponseEntity<Object> getClienteById(Integer id){
        Optional<Cliente> cliente = clientesRepository.findById(id);
        if (cliente.isPresent()) {
            logger.info("Cliente com o id {} encontrado", id);
            return ResponseEntity.ok(cliente.get());
        } else {
            logger.info("Cliente com o id {} não encontrado", id);
            String mensagemErro = String.format("Cliente com o id %d não encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
    }

    //metodo para salvar um cliente
    public ResponseEntity<Object> salvarCliente(Cliente cliente){
        if(cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            logger.error("Nome do cliente não pode ser vazio");
            return ResponseEntity.badRequest().body("O corpo da solicitação deve conter pelo menos o nome do cliente");
        }
        Cliente clienteSalvo = clientesRepository.save(cliente);
        logger.info("Cliente salvo com sucesso com o id {}", clienteSalvo.getId());
        return ResponseEntity.ok(clienteSalvo);
    }

    //metodo para deletar um cliente pelo id
    public ResponseEntity<String> deletarCliente(Integer id){
        Optional<Cliente> cliente = clientesRepository.findById(id);
        if (cliente.isPresent()) {
            clientesRepository.delete(cliente.get());
            logger.info("Cliente com o id {} deletado com sucesso", id);
            String mensagem = String.format("Cliente com o id %d deletado com sucesso", id);
            return ResponseEntity.ok(mensagem);
        } else {
            logger.info("Cliente com o id {} não encontrado", id);
            String mensagemErro = String.format("Cliente com o id %d não encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
    }

    //metodo para atualizar um cliente
    public ResponseEntity<Object> atualizarCliente(Integer id, Cliente cliente){
        Optional<Cliente> clienteOptional = clientesRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente clienteExistente = clienteOptional.get();
            cliente.setId(clienteExistente.getId());
            clienteExistente.setNome(cliente.getNome());
            clientesRepository.save(clienteExistente);
            logger.info("Cliente com o id {} atualizado com sucesso", id);
            return ResponseEntity.ok(clienteExistente);
        } else {
            logger.info("Cliente com o id {} não encontrado", id);
            String mensagemErro = String.format("Cliente com o id %d não encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
    }

    //metodo para listar todos os clientes
    public ResponseEntity<Object> listarClientes(){
        return ResponseEntity.ok(clientesRepository.findAll());
    }
}
