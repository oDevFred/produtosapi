package io.github.odevfred.produtosapi.controller;

import java.util.UUID;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.odevfred.produtosapi.model.Produto;
import io.github.odevfred.produtosapi.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

  private final ProdutoRepository produtoRepository;

  public ProdutoController(ProdutoRepository produtoRepository) {
    this.produtoRepository = produtoRepository;
  }

  // Criando Produtos
  @PostMapping
  public Produto salvar(@RequestBody Produto produto) {
    var id = UUID.randomUUID().toString();
    produto.setId(id);

    produtoRepository.save(produto);

    return produto;
  }

  // Obtendo Produto
  @GetMapping("{id}")
  public Produto obterPorId(@PathVariable("id") String id) {
    return produtoRepository.findById(id).orElse(null);
  }

  // Deletando Produto
  @DeleteMapping("{id}")
  public void deletar(@PathVariable("id") String id) {
    produtoRepository.deleteById(id);
  }

  // Atualizando Produto
  @PutMapping("{id}")
  public void atualizar(@PathVariable("id") String id, @RequestBody Produto produto) {
    produto.setId(id);
    produtoRepository.save(produto);
  }

  // Listando Produto
  @GetMapping
  public List<Produto> buscar(@RequestParam("nome") String nome) {
    return produtoRepository.findByNome(nome);
  }
}