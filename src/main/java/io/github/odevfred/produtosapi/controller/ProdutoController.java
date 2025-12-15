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

/**
 * Controller REST para gerenciamento de Produtos.
 * 
 * Esta classe é responsável por expor endpoints HTTP para operações CRUD
 * (Create, Read, Update, Delete) de produtos. É a camada de apresentação
 * da aplicação, recebendo requisições HTTP e retornando respostas JSON.
 * 
 * ARQUITETURA MVC:
 * Controller (esta classe) -> Repository (ProdutoRepository) -> Model (Produto)
 * 
 * OBSERVAÇÃO IMPORTANTE - ARQUITETURA:
 * ⚠️ Este controller está acessando diretamente o Repository, o que NÃO é
 * considerado uma boa prática em aplicações profissionais. O ideal seria ter
 * uma camada intermediária de SERVICE:
 * Controller -> Service -> Repository
 * 
 * BENEFÍCIOS DE TER UMA CAMADA SERVICE:
 * - Separação de responsabilidades (Controller cuida de HTTP, Service de lógica
 * de negócio)
 * - Facilita testes unitários (mock do Service)
 * - Permite reutilizar lógica de negócio em diferentes controllers
 * - Gerenciamento de transações (@Transactional)
 * - Validações de negócio centralizadas
 * - Facilita manutenção e escalabilidade
 * 
 * @author odevfred
 * @version 1.0
 * @see Produto
 * @see ProdutoRepository
 */

// @RestController: Combinação de @Controller + @ResponseBody
// EFEITOS:
// - Marca esta classe como um Controller REST
// - Todos os métodos retornam dados JSON automaticamente (não views HTML)
// - Spring converte objetos Java para JSON usando Jackson
// - Trata requisições HTTP e retorna respostas HTTP
@RestController

// @RequestMapping: Define a rota base para TODOS os endpoints deste controller
// RESULTADO: Todos os endpoints começam com /produtos
// Exemplos de URLs completas:
// - POST http://localhost:8080/produtos
// - GET http://localhost:8080/produtos/{id}
// - GET http://localhost:8080/produtos?nome=Notebook
// - PUT http://localhost:8080/produtos/{id}
// - DELETE http://localhost:8080/produtos/{id}
@RequestMapping("produtos")
public class ProdutoController {

  // ============================================
  // INJEÇÃO DE DEPENDÊNCIAS
  // ============================================

  /**
   * Repositório para acesso aos dados de produtos.
   * 
   * INJEÇÃO DE DEPENDÊNCIA VIA CONSTRUTOR:
   * - Imutável (final): Não pode ser alterado após construção
   * - Recomendado pelo Spring: Facilita testes e garante dependência obrigatória
   * - Não precisa de @Autowired (opcional desde Spring 4.3)
   * 
   * ⚠️ ACESSO DIRETO AO REPOSITORY:
   * Em produção, deveria ser: private final ProdutoService produtoService;
   */
  private final ProdutoRepository produtoRepository;

  /**
   * Construtor para injeção de dependências.
   * 
   * INJEÇÃO AUTOMÁTICA:
   * O Spring identifica automaticamente este construtor e injeta
   * a instância de ProdutoRepository gerenciada pelo container IoC.
   * 
   * VANTAGENS DESTE PADRÃO:
   * - Facilita testes (pode passar mock no construtor)
   * - Deixa claro quais são as dependências obrigatórias
   * - Imutabilidade (campo final)
   * - Não precisa de @Autowired (opcional)
   * 
   * @param produtoRepository Repositório de produtos injetado pelo Spring
   */
  public ProdutoController(ProdutoRepository produtoRepository) {
    this.produtoRepository = produtoRepository;
  }

  // ============================================
  // ENDPOINTS CRUD
  // ============================================

  /**
   * Cria um novo produto no banco de dados.
   * 
   * ENDPOINT: POST /produtos
   * MÉTODO HTTP: POST (criação de recursos)
   * BODY (JSON):
   * {
   * "nome": "Notebook Gamer",
   * "descricao": "16GB RAM, RTX 3060",
   * "preco": 4500.00
   * }
   * 
   * RESPOSTA (JSON):
   * {
   * "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
   * "nome": "Notebook Gamer",
   * "descricao": "16GB RAM, RTX 3060",
   * "preco": 4500.00
   * }
   * 
   * FLUXO DE EXECUÇÃO:
   * 1. Cliente envia requisição POST com JSON no body
   * 2. Spring converte JSON para objeto Produto (@RequestBody)
   * 3. UUID aleatório é gerado para o ID
   * 4. ID é atribuído ao produto
   * 5. Produto é salvo no banco via repository
   * 6. Produto com ID é retornado ao cliente (JSON)
   * 
   * ⚠️ PROBLEMAS DESTA IMPLEMENTAÇÃO:
   * - Não valida dados de entrada (nome obrigatório, preço positivo, etc.)
   * - Não trata exceções (banco offline, dados inválidos)
   * - Não retorna código HTTP apropriado (deveria ser 201 CREATED)
   * - Não retorna location header com URI do recurso criado
   * - ID é gerado no controller (deveria ser no service ou entity)
   * 
   * IMPLEMENTAÇÃO IDEAL:
   * 
   * @PostMapping
   *              public ResponseEntity<Produto> salvar(@Valid @RequestBody
   *              Produto produto) {
   *              Produto produtoSalvo = produtoService.salvar(produto);
   *              URI location = URI.create("/produtos/" + produtoSalvo.getId());
   *              return ResponseEntity.created(location).body(produtoSalvo);
   *              }
   * 
   * @param produto Objeto produto enviado no corpo da requisição (deserializado
   *                do JSON)
   * @return Produto salvo com ID gerado
   */
  @PostMapping // Mapeia requisições POST para /produtos
  public Produto salvar(@RequestBody Produto produto) {
    // @RequestBody: Indica que o Spring deve converter o JSON do body da requisição
    // para um objeto Produto usando Jackson (biblioteca de serialização JSON)

    // Gera um ID único universal (UUID versão 4 - aleatório)
    // Formato: 8-4-4-4-12 caracteres hexadecimais
    // Exemplo: "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
    // Probabilidade de colisão: praticamente zero (2^122 possibilidades)
    var id = UUID.randomUUID().toString();

    // Atribui o ID gerado ao produto
    produto.setId(id);

    // Persiste o produto no banco de dados
    // O método save() do JpaRepository:
    // - Detecta se é INSERT (id novo) ou UPDATE (id existente)
    // - Executa: INSERT INTO produto (id, nome, descricao, preco) VALUES (?, ?, ?,
    // ?)
    // - Gerencia transação automaticamente
    // - Retorna o objeto salvo (mas estamos ignorando aqui)
    produtoRepository.save(produto);

    // Retorna o produto com ID para o cliente
    // Spring converte automaticamente para JSON usando Jackson
    // HTTP Status padrão: 200 OK (deveria ser 201 CREATED)
    return produto;
  }

  /**
   * Busca um produto específico por ID.
   * 
   * ENDPOINT: GET /produtos/{id}
   * MÉTODO HTTP: GET (leitura de recurso específico)
   * EXEMPLO: GET /produtos/a1b2c3d4-e5f6-7890-abcd-ef1234567890
   * 
   * RESPOSTA (JSON):
   * {
   * "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
   * "nome": "Notebook Gamer",
   * "descricao": "16GB RAM, RTX 3060",
   * "preco": 4500.00
   * }
   * 
   * COMPORTAMENTO:
   * - Produto encontrado: Retorna JSON do produto (HTTP 200)
   * - Produto NÃO encontrado: Retorna null serializado como JSON (HTTP 200)
   * 
   * ⚠️ PROBLEMA CRÍTICO:
   * Quando produto não é encontrado, retorna null (HTTP 200 OK).
   * O correto seria retornar HTTP 404 NOT FOUND.
   * 
   * IMPLEMENTAÇÃO IDEAL:
   * @GetMapping("{id}")
   * public ResponseEntity<Produto> obterPorId(@PathVariable String id) {
   * return produtoRepository.findById(id)
   * .map(ResponseEntity::ok)
   * .orElse(ResponseEntity.notFound().build());
   * }
   * 
   * @param id ID do produto a ser buscado (extraído da URL)
   * @return Produto encontrado ou null se não existir
   */
  @GetMapping("{id}") // Mapeia GET /produtos/{id}
                      // {id} é uma variável de path (path variable)
  public Produto obterPorId(@PathVariable("id") String id) {
    // @PathVariable: Extrai o valor da URL e injeta no parâmetro
    // Exemplo: GET /produtos/abc-123 -> id = "abc-123"
    // O nome "id" entre parênteses deve corresponder ao {id} da URL

    // findById() retorna Optional<Produto>:
    // - Optional.of(produto) se encontrado
    // - Optional.empty() se não encontrado
    //
    // .orElse(null): Se Optional estiver vazio, retorna null
    //
    // QUERY SQL EXECUTADA:
    // SELECT * FROM produto WHERE id = ?
    return produtoRepository.findById(id).orElse(null);

    // ⚠️ MELHOR PRÁTICA:
    // Nunca retorne null. Use ResponseEntity:
    // return produtoRepository.findById(id)
    // .map(produto -> ResponseEntity.ok(produto)) // 200 OK com produto
    // .orElseGet(() -> ResponseEntity.notFound().build()); // 404 NOT FOUND
  }

  /**
   * Deleta um produto por ID.
   * 
   * ENDPOINT: DELETE /produtos/{id}
   * MÉTODO HTTP: DELETE (remoção de recurso)
   * EXEMPLO: DELETE /produtos/a1b2c3d4-e5f6-7890-abcd-ef1234567890
   * 
   * RESPOSTA: Corpo vazio (void) com HTTP 200 OK
   * 
   * COMPORTAMENTO:
   * - Produto existe: Deleta e retorna 200 OK
   * - Produto NÃO existe: LANÇA EXCEÇÃO EmptyResultDataAccessException
   * 
   * ⚠️ PROBLEMA CRÍTICO:
   * Se o ID não existir, deleteById() lança exceção não tratada,
   * resultando em HTTP 500 INTERNAL SERVER ERROR para o cliente.
   * 
   * QUERY SQL EXECUTADA:
   * DELETE FROM produto WHERE id = ?
   * 
   * IMPLEMENTAÇÃO IDEAL:
   * @DeleteMapping("{id}")
   * public ResponseEntity<Void> deletar(@PathVariable String id) {
   * if (!produtoRepository.existsById(id)) {
   * return ResponseEntity.notFound().build(); // 404 NOT FOUND
   * }
   * produtoRepository.deleteById(id);
   * return ResponseEntity.noContent().build(); // 204 NO CONTENT
   * }
   * 
   * @param id ID do produto a ser deletado
   */
  @DeleteMapping("{id}") // Mapeia DELETE /produtos/{id}
  public void deletar(@PathVariable("id") String id) {
    // @PathVariable: Extrai ID da URL

    // deleteById(): Deleta produto com o ID especificado
    // ATENÇÃO:
    // - Se ID não existir, lança EmptyResultDataAccessException (HTTP 500)
    // - Não há validação se o produto existe antes de deletar
    // - Não retorna confirmação de sucesso (void)
    //
    // FLUXO INTERNO:
    // 1. Busca produto por ID
    // 2. Se encontrado, executa DELETE
    // 3. Se não encontrado, LANÇA EXCEÇÃO
    produtoRepository.deleteById(id);

    // Retorno void: Spring retorna HTTP 200 OK com corpo vazio
    // HTTP Status ideal: 204 NO CONTENT (indica sucesso sem corpo de resposta)
  }

  /**
   * Atualiza um produto existente.
   * 
   * ENDPOINT: PUT /produtos/{id}
   * MÉTODO HTTP: PUT (atualização completa de recurso)
   * EXEMPLO: PUT /produtos/a1b2c3d4-e5f6-7890-abcd-ef1234567890
   * BODY (JSON):
   * {
   * "nome": "Notebook Gamer ATUALIZADO",
   * "descricao": "32GB RAM, RTX 4090",
   * "preco": 8500.00
   * }
   * 
   * RESPOSTA: Corpo vazio (void) com HTTP 200 OK
   * 
   * COMPORTAMENTO:
   * - ID existe: Atualiza todos os campos e retorna 200 OK
   * - ID NÃO existe: CRIA um novo produto com esse ID (comportamento inesperado!)
   * 
   * ⚠️ PROBLEMAS CRÍTICOS:
   * 1. Se ID não existir, cria novo produto em vez de retornar 404
   * 2. Não valida se dados de entrada são válidos
   * 3. Não retorna o produto atualizado
   * 4. Não diferencia UPDATE de INSERT
   * 5. Cliente envia ID no body (que é ignorado) E na URL (que é usado)
   * 
   * QUERY SQL EXECUTADA:
   * - Se ID existe: UPDATE produto SET nome=?, descricao=?, preco=? WHERE id=?
   * - Se ID NÃO existe: INSERT INTO produto (id, nome, descricao, preco) VALUES
   * (?, ?, ?, ?)
   * 
   * IMPLEMENTAÇÃO IDEAL:
   * @PutMapping("{id}")
   * public ResponseEntity<Produto> atualizar(
   * 
   * @PathVariable String id,
   * @Valid @RequestBody Produto produto) {
   * 
   *        if (!produtoRepository.existsById(id)) {
   *        return ResponseEntity.notFound().build(); // 404 NOT FOUND
   *        }
   * 
   *        produto.setId(id);
   *        Produto atualizado = produtoRepository.save(produto);
   *        return ResponseEntity.ok(atualizado); // 200 OK com produto atualizado
   *        }
   * 
   * @param id      ID do produto a ser atualizado (extraído da URL)
   * @param produto Objeto com novos dados (deserializado do JSON)
   */
  @PutMapping("{id}") // Mapeia PUT /produtos/{id}
  public void atualizar(
      @PathVariable("id") String id, // Extrai ID da URL
      @RequestBody Produto produto) { // Extrai dados do JSON body

    // Sobrescreve qualquer ID que veio no JSON com o ID da URL
    // Isso garante que o produto será atualizado com o ID correto
    // PROBLEMA: Se ID não existir, save() criará novo registro em vez de atualizar
    produto.setId(id);

    // save() do JpaRepository tem comportamento UPSERT (Update or Insert):
    // - Se ID existe no banco: UPDATE
    // - Se ID NÃO existe: INSERT (comportamento indesejado aqui!)
    //
    // DEVERIA FAZER:
    // 1. Verificar se produto existe (existsById ou findById)
    // 2. Se não existir, retornar 404 NOT FOUND
    // 3. Se existir, atualizar e retornar 200 OK
    produtoRepository.save(produto);

    // Retorno void: HTTP 200 OK sem corpo
    // IDEAL: Retornar o produto atualizado ou HTTP 204 NO CONTENT
  }

  /**
   * Busca produtos por nome exato.
   * 
   * ENDPOINT: GET /produtos?nome=Notebook
   * MÉTODO HTTP: GET (leitura de coleção com filtro)
   * QUERY PARAMETER: nome (obrigatório)
   * 
   * EXEMPLOS DE USO:
   * - GET /produtos?nome=Notebook → Busca produtos com nome "Notebook"
   * - GET /produtos?nome=Mouse%20Gamer → Busca "Mouse Gamer" (espaço codificado)
   * - GET /produtos?nome= → Busca produtos com nome vazio
   * 
   * RESPOSTA (JSON - Array):
   * [
   * {
   * "id": "abc-123",
   * "nome": "Notebook",
   * "descricao": "16GB RAM",
   * "preco": 3500.00
   * },
   * {
   * "id": "def-456",
   * "nome": "Notebook",
   * "descricao": "32GB RAM",
   * "preco": 5500.00
   * }
   * ]
   * 
   * COMPORTAMENTO:
   * - Produtos encontrados: Retorna lista (pode estar vazia [])
   * - Nenhum produto encontrado: Retorna lista vazia []
   * - Nome não fornecido: ERRO 400 BAD REQUEST (parâmetro obrigatório)
   * 
   * ⚠️ LIMITAÇÕES:
   * - Busca apenas por nome EXATO (case-sensitive)
   * - Não suporta busca parcial (LIKE %nome%)
   * - Parâmetro é obrigatório (causa erro se omitido)
   * - Não há paginação (pode retornar milhares de registros)
   * - Não há ordenação
   * 
   * QUERY SQL EXECUTADA:
   * SELECT * FROM produto WHERE nome = ?
   * 
   * MELHORIAS SUGERIDAS:
   * 
   * @GetMapping
   *             public ResponseEntity<Page<Produto>> buscar(
   * @RequestParam(required = false) String nome,
   *                        Pageable pageable) {
   * 
   *                        Page<Produto> produtos;
   *                        if (nome != null && !nome.isBlank()) {
   *                        produtos =
   *                        produtoRepository.findByNomeContainingIgnoreCase(nome,
   *                        pageable);
   *                        } else {
   *                        produtos = produtoRepository.findAll(pageable);
   *                        }
   *                        return ResponseEntity.ok(produtos);
   *                        }
   * 
   * @param nome Nome do produto a ser buscado (parâmetro de query obrigatório)
   * @return Lista de produtos com o nome especificado (pode estar vazia)
   */
  @GetMapping // Mapeia GET /produtos (sem path variable)
  public List<Produto> buscar(@RequestParam("nome") String nome) {
    // @RequestParam: Extrai parâmetro da query string da URL
    // Exemplo: GET /produtos?nome=Notebook -> nome = "Notebook"
    //
    // ATRIBUTOS ÚTEIS:
    // - required = true (padrão): Parâmetro obrigatório, erro 400 se ausente
    // - required = false: Parâmetro opcional, valor null se ausente
    // - defaultValue = "": Valor padrão se parâmetro não for fornecido
    //
    // COMO FICARIA OPCIONAL:
    // @RequestParam(value = "nome", required = false) String nome

    // findByNome() é um método de query derivado do ProdutoRepository
    // Executa: SELECT * FROM produto WHERE nome = ?
    // Retorna List<Produto>:
    // - Lista com produtos encontrados (1 ou mais)
    // - Lista vazia [] se nenhum produto tiver esse nome
    // - NUNCA retorna null
    return produtoRepository.findByNome(nome);

    // PROBLEMA DE PERFORMANCE:
    // Se houver 10.000 produtos com o mesmo nome, todos serão carregados
    // na memória e retornados de uma vez (sem paginação).
    //
    // SOLUÇÃO: Adicionar paginação com Pageable
  }

  // ============================================
  // MELHORIAS GERAIS RECOMENDADAS
  // ============================================
  //
  // 1. ADICIONAR CAMADA DE SERVICE:
  // - Mover lógica de negócio para ProdutoService
  // - Controller só trata HTTP, Service trata regras de negócio
  //
  // 2. USAR ResponseEntity PARA CÓDIGOS HTTP APROPRIADOS:
  // - 201 CREATED para POST
  // - 204 NO CONTENT para PUT/DELETE bem-sucedidos
  // - 404 NOT FOUND quando recurso não existe
  // - 400 BAD REQUEST para dados inválidos
  //
  // 3. ADICIONAR VALIDAÇÕES:
  // - @Valid no @RequestBody
  // - Bean Validation (@NotBlank, @Positive, etc.)
  //
  // 4. TRATAMENTO DE EXCEÇÕES:
  // - @ControllerAdvice para tratamento global
  // - Respostas de erro padronizadas
  //
  // 5. DOCUMENTAÇÃO DA API:
  // - SpringDoc OpenAPI (Swagger)
  // - Documentar endpoints, parâmetros, respostas
  //
  // 6. SEGURANÇA:
  // - Autenticação (Spring Security)
  // - Autorização (roles/permissions)
  // - Rate limiting
  //
  // 7. PAGINAÇÃO E ORDENAÇÃO:
  // - Usar Pageable nos métodos de listagem
  // - Evitar carregar todos os registros de uma vez
  //
  // 8. TESTES:
  // - Testes unitários do controller (@WebMvcTest)
  // - Testes de integração (@SpringBootTest)
  //
  // 9. LOGS:
  // - Adicionar logs estruturados (SLF4J)
  // - Registrar operações importantes
  //
  // 10. VERSIONAMENTO DA API:
  // - @RequestMapping("api/v1/produtos")
  // - Facilita evolução sem quebrar clientes
}