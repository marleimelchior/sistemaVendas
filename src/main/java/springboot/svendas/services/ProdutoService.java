package springboot.svendas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springboot.svendas.domain.entity.Produto;
import springboot.svendas.domain.repository.ProdutosRepository;

import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);
    private final ProdutosRepository produtosRepository;

    public ProdutoService(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    // método para buscar um produto pelo id
    public ResponseEntity<Object> getProdutoById(Integer id) {
        Optional<Produto> produto = produtosRepository.findById(id);
        if (produto.isPresent()) {
            logger.info("Produto com o id {} encontrado", id);
            return ResponseEntity.ok(produto.get());
        } else {
            logger.info("Produto com o id {} não encontrado", id);
            String mensagemErro = String.format("Produto com o id %d não encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
    }

    // método para salvar um produto
    public ResponseEntity<Object> salvarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            logger.error("Nome do produto não pode ser vazio");
            return ResponseEntity.badRequest().body("O corpo da solicitação deve conter pelo menos o nome do produto");
        }
        Produto produtoSalvo = produtosRepository.save(produto);
        logger.info("Produto salvo com sucesso com o id {}", produtoSalvo.getId());
        return ResponseEntity.ok(produtoSalvo);
    }

    // método para deletar um produto pelo id
    public ResponseEntity<String> deletarProduto(Integer id) {
        Optional<Produto> produto = produtosRepository.findById(id);
        if (produto.isPresent()) {
            produtosRepository.delete(produto.get());
            logger.info("Produto com o id {} deletado com sucesso", id);
            String mensagem = String.format("Produto com o id %d deletado com sucesso", id);
            return ResponseEntity.ok(mensagem);
        } else {
            logger.info("Produto com o id {} não encontrado", id);
            String mensagemErro = String.format("Produto com o id %d não encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
    }

    // método para atualizar um produto
    public ResponseEntity<Object> atualizarProduto(Integer id, Produto produto) {
        Optional<Produto> produtoOptional = produtosRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produtoExistente = produtoOptional.get();
            produto.setId(produtoExistente.getId());
            produtoExistente.setNome(produto.getNome());
            produtoExistente.setDescricao(produto.getDescricao());
            produtoExistente.setPreco(produto.getPreco());
            produtosRepository.save(produtoExistente);
            logger.info("Produto com o id {} atualizado com sucesso", id);
            return ResponseEntity.ok(produtoExistente);
        } else {
            logger.info("Produto com o id {} não encontrado", id);
            String mensagemErro = String.format("Produto com o id %d não encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
    }

    // método que retorna lista de filtro por caracteres do nome
    public ResponseEntity<Object> buscarPorNome(String nome) {
        logger.info("Buscando produtos com o nome {}", nome);
        return ResponseEntity.ok(produtosRepository.encontrarPorNome(nome));
    }

    // método para listar todos os produtos
    public ResponseEntity<Object> listarProdutos() {
        logger.info("Listando todos os produtos");
        return ResponseEntity.ok(produtosRepository.findAll());
    }


}
