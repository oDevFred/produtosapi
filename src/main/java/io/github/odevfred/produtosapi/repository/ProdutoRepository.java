package io.github.odevfred.produtosapi.repository;

import io.github.odevfred.produtosapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interface de Repositório para a Entidade Produto.
 * Ao estender JpaRepository, esta interface herda automaticamente
 * dezenas de métodos CRUD (Create, Read, Update, Delete) básicos e avançados,
 * eliminando a necessidade de escrever a implementação.
 */
public interface ProdutoRepository extends JpaRepository<Produto, String> {
  // A interface JpaRepository exige dois parâmetros genéricos:

  // 1. A Entidade que será gerenciada: Produto.
  // 2. O tipo da Chave Primária (ID) dessa Entidade: String (conforme definido na
  // classe Produto).

  // NOTA: Não é necessário adicionar nenhum método aqui para operações básicas,
  // como save(), findById(), findAll(), delete(), pois são herdados de
  // JpaRepository.

  // Se você precisasse de um método personalizado, como 'buscarPorNome(String
  // nome)',
  // você o declararia aqui (e o Spring Data JPA implementaria automaticamente a
  // lógica SQL).

  List<Produto> findByNome(String nome);

}