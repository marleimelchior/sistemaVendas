package springboot.svendas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.svendas.domain.entity.Produto;
import springboot.svendas.services.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoController.class);
    private final ProdutoService produtoService;


    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    //método para buscar um produto pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProdutoById(@PathVariable Integer id) {
        return produtoService.getProdutoById(id);
    }

    //método para salvar um produto
    @PostMapping("/cadastrar")
    public ResponseEntity<Object> salvarProduto(@RequestBody Produto produto) {
        return produtoService.salvarProduto(produto);
    }

    //método para deletar um produto pelo id
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Integer id) {
        return produtoService.deletarProduto(id);
    }

    //método para atualizar um produto pelo id
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable Integer id, @RequestBody Produto produto) {
        return produtoService.atualizarProduto(id, produto);
    }

    //método para buscar produtos pelo nome
    @GetMapping("/buscarPorNome")
    public ResponseEntity<Object> buscarPorNome(@RequestParam String nome) {
        return produtoService.buscarPorNome(nome);
    }

    //método para listar todos os produtos
    @GetMapping("/buscarTodos")
    public ResponseEntity<Object> buscarTodos() {
        return produtoService.listarProdutos();
    }
}
