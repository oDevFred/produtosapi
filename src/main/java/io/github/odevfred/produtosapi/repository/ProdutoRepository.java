package io.github.odevfred.produtosapi.repository;

import io.github.odevfred.produtosapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interface de Repositório (DAO - Data Access Object) para a entidade Produto.
 * 
 * Esta interface é a camada de acesso aos dados, responsável por todas as
 * operações
 * de persistência (salvar, buscar, atualizar, deletar) no banco de dados.
 * 
 * SPRING DATA JPA - MAGIA DA IMPLEMENTAÇÃO AUTOMÁTICA:
 * Ao estender JpaRepository, o Spring Data JPA cria AUTOMATICAMENTE em tempo de
 * execução
 * uma classe proxy que implementa todos os métodos declarados. Você NÃO precisa
 * escrever
 * nenhuma linha de código SQL ou implementação!
 * 
 * BENEFÍCIOS:
 * - Reduz código boilerplate (repetitivo)
 * - Implementação otimizada e testada pelo Spring
 * - Suporte a paginação, ordenação e queries complexas
 * - Transações gerenciadas automaticamente
 * 
 * @author odevfred
 * @see Produto
 * @see JpaRepository
 */
public interface ProdutoRepository extends JpaRepository<Produto, String> {

  // ============================================
  // GENÉRICOS DO JpaRepository<T, ID>
  // ============================================
  // Parâmetro 1 (T): Produto - A entidade JPA que será gerenciada por este
  // repositório
  // Parâmetro 2 (ID): String - O tipo de dado da chave primária (@Id) da entidade
  // Produto
  // (definido como 'String id' na classe Produto)

  // ============================================
  // MÉTODOS HERDADOS AUTOMATICAMENTE
  // ============================================
  // Ao estender JpaRepository, você ganha GRATUITAMENTE os seguintes métodos
  // (não precisa declarar, já estão disponíveis):
  //
  // OPERAÇÕES BÁSICAS:
  // - save(Produto produto): Salva ou atualiza um produto
  // - saveAll(List<Produto> produtos): Salva múltiplos produtos em batch
  // - findById(String id): Busca produto por ID (retorna Optional<Produto>)
  // - existsById(String id): Verifica se produto existe
  // - findAll(): Retorna TODOS os produtos (cuidado em produção!)
  // - findAllById(List<String> ids): Busca múltiplos produtos por lista de IDs
  // - count(): Retorna quantidade total de produtos
  // - deleteById(String id): Deleta produto por ID
  // - delete(Produto produto): Deleta produto pela entidade
  // - deleteAll(): Deleta TODOS os produtos (PERIGOSO!)
  //
  // OPERAÇÕES AVANÇADAS:
  // - findAll(Sort sort): Busca todos ordenados
  // - findAll(Pageable pageable): Busca com paginação (performance!)
  // - flush(): Força sincronização com banco de dados
  // - saveAndFlush(Produto produto): Salva e força commit imediato
  // - deleteInBatch(List<Produto> produtos): Deleta em lote (mais performático)

  // ============================================
  // MÉTODOS DE QUERY PERSONALIZADOS
  // ============================================

  /**
   * Busca produtos por nome exato.
   * 
   * SPRING DATA JPA - QUERY METHOD (Método de Consulta):
   * O Spring Data JPA analisa o NOME do método e gera automaticamente a query
   * SQL!
   * 
   * CONVENÇÃO DE NOMENCLATURA:
   * - findBy: Prefixo que indica uma busca (SELECT)
   * - Nome: Nome do atributo da entidade Produto (deve ser EXATAMENTE igual)
   * 
   * QUERY SQL GERADA AUTOMATICAMENTE:
   * SELECT * FROM produto WHERE nome = ?
   * 
   * OUTRAS CONVENÇÕES POSSÍVEIS:
   * - findByNomeContaining(String texto): WHERE nome LIKE %texto%
   * - findByNomeStartingWith(String prefixo): WHERE nome LIKE prefixo%
   * - findByNomeEndingWith(String sufixo): WHERE nome LIKE %sufixo
   * - findByNomeIgnoreCase(String nome): WHERE UPPER(nome) = UPPER(?)
   * - findByPrecoGreaterThan(BigDecimal preco): WHERE preco > ?
   * - findByPrecoLessThan(BigDecimal preco): WHERE preco < ?
   * - findByPrecoBetween(BigDecimal min, BigDecimal max): WHERE preco BETWEEN ?
   * AND ?
   * - findByNomeAndPreco(String nome, BigDecimal preco): WHERE nome = ? AND preco
   * = ?
   * - findByNomeOrDescricao(String nome, String desc): WHERE nome = ? OR
   * descricao = ?
   * - findByOrderByNomeAsc(): ORDER BY nome ASC
   * - countByNome(String nome): SELECT COUNT(*) WHERE nome = ?
   * - deleteByNome(String nome): DELETE FROM produto WHERE nome = ?
   * 
   * @param nome Nome exato do produto a ser buscado (case-sensitive por padrão)
   * @return Lista de produtos com o nome especificado.
   *         Pode retornar lista vazia se nenhum produto for encontrado.
   *         Nunca retorna null.
   * 
   *         EXEMPLO DE USO NO SERVICE:
   *         List<Produto> produtos = produtoRepository.findByNome("Notebook
   *         Gamer");
   * 
   *         ATENÇÃO:
   *         - Este método busca nome EXATO (não parcial)
   *         - Para busca parcial, use findByNomeContaining(String nome)
   *         - Para buscar ignorando maiúsculas/minúsculas, use
   *         findByNomeIgnoreCase(String nome)
   *         - Se quiser apenas UM resultado, mude retorno para Optional<Produto>
   */
  List<Produto> findByNome(String nome);

  // ============================================
  // EXEMPLOS DE OUTROS MÉTODOS PERSONALIZADOS
  // ============================================
  // Você pode adicionar mais métodos seguindo a convenção de nomenclatura:

  // List<Produto> findByPrecoLessThan(BigDecimal preco);
  // List<Produto> findByNomeContainingIgnoreCase(String nome);
  // Page<Produto> findByDescricaoContaining(String descricao, Pageable pageable);
  // Optional<Produto> findFirstByNomeOrderByPrecoAsc(String nome);
  // boolean existsByNome(String nome);
  // long countByPrecoGreaterThan(BigDecimal preco);
  // @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :min AND :max")
  // List<Produto> buscarPorFaixaDePreco(@Param("min") BigDecimal min,
  // @Param("max") BigDecimal max);

  // ============================================
  // NOTAS IMPORTANTES
  // ============================================
  // 1. Esta interface NÃO precisa de implementação (@Repository é opcional mas
  // recomendado)
  // 2. O Spring cria o proxy automaticamente em tempo de execução
  // 3. Todas as operações são transacionais por padrão
  // 4. Use @Transactional(readOnly = true) no Service para otimizar leituras
  // 5. Evite usar findAll() em produção sem paginação (pode explodir memória)
  // 6. Para queries complexas, use @Query com JPQL ou SQL nativo
  // 7. Métodos de query derivados funcionam até certo nível de complexidade
}