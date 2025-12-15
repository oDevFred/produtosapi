package io.github.odevfred.produtosapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Classe de Entidade JPA que representa um Produto.
 * 
 * POJO - Plain Old Java Object (Objeto Java Simples e Comum):
 * É uma classe Java tradicional com atributos privados, getters/setters e
 * sem dependências complexas de frameworks (exceto anotações JPA).
 * 
 * MAPEAMENTO OBJETO-RELACIONAL (ORM):
 * Esta classe estabelece uma ponte entre o mundo orientado a objetos (Java)
 * e o mundo relacional (banco de dados SQL). Cada instância desta classe
 * representa uma linha na tabela 'produto'.
 * 
 * RESPONSABILIDADES:
 * - Representar a estrutura de dados de um produto
 * - Mapear atributos Java para colunas do banco de dados
 * - Ser gerenciada pelo EntityManager do JPA/Hibernate
 * 
 * @author odevfred
 * @version 1.0
 */

// @Entity: Marca esta classe como uma entidade JPA gerenciada pelo Hibernate.
// EFEITOS:
// - Hibernate criará/atualizará automaticamente a tabela correspondente
// - Objetos desta classe podem ser persistidos, buscados, atualizados e
// deletados via JPA
// - EntityManager rastreia mudanças nos objetos (Persistent Context)
// - Suporta relacionamentos (@OneToMany, @ManyToOne, etc.)
@Entity

// @Table: Especifica o nome EXATO da tabela no banco de dados.
// QUANDO USAR:
// - Obrigatório se o nome da tabela for diferente do nome da classe
// - Opcional se seguir convenção: classe 'Produto' -> tabela 'produto'
// (lowercase)
// ATRIBUTOS ADICIONAIS (opcionais):
// - schema: Define o schema do banco (ex: @Table(name="produto",
// schema="vendas"))
// - uniqueConstraints: Define restrições de unicidade compostas
// - indexes: Define índices para otimização de queries
@Table(name = "produto")
public class Produto {

  // ============================================
  // ATRIBUTOS (Campos que serão colunas no BD)
  // ============================================

  /**
   * Identificador único do produto (Chave Primária).
   * 
   * ESTRATÉGIA DE GERAÇÃO:
   * Neste caso, o ID é String e deve ser gerado manualmente (ex: UUID).
   * Se fosse Long, poderia usar @GeneratedValue para auto-incremento:
   * 
   * @GeneratedValue(strategy = GenerationType.IDENTITY)
   * 
   *                          IMPORTANTE:
   *                          - Deve ser único e não nulo
   *                          - Imutável após persistência (não deve ser alterado)
   *                          - Recomendado usar UUID para IDs distribuídos
   */
  @Id // Define este campo como chave primária (PRIMARY KEY)
      // SEM @GeneratedValue: Significa que o valor do ID deve ser definido
      // MANUALMENTE
      // antes de salvar a entidade (ex: produto.setId(UUID.randomUUID().toString()))

  @Column(name = "id") // Mapeia para a coluna 'id' no banco de dados
                       // OPCIONAL: Se omitido, JPA usa o nome do campo (id -> id)
                       // ATRIBUTOS ÚTEIS:
                       // - nullable = false: NOT NULL (recomendado para IDs)
                       // - unique = true: UNIQUE constraint
                       // - length = 255: VARCHAR(255) - padrão já é 255
                       // - updatable = false: Impede UPDATE nesta coluna
  private String id;

  /**
   * Nome do produto.
   * 
   * CONSTRAINT NO BANCO: VARCHAR(50) NOT NULL (definido no schema SQL)
   * VALIDAÇÕES: Considere adicionar @NotBlank e @Size(max=50) do Bean Validation
   */
  @Column(name = "nome") // Mapeia para coluna 'nome'
  private String nome;

  /**
   * Descrição detalhada do produto.
   * 
   * CONSTRAINT NO BANCO: VARCHAR(300) (definido no schema SQL)
   * VALIDAÇÕES: Considere adicionar @Size(max=300) do Bean Validation
   * Campo OPCIONAL (pode ser null)
   */
  @Column(name = "descricao") // Mapeia para coluna 'descricao'
  private String descricao;

  /**
   * Preço do produto em reais (R$).
   * 
   * ⚠️ ATENÇÃO - TIPO DE DADO INADEQUADO:
   * Double NÃO é recomendado para valores monetários devido a:
   * - Imprecisão de ponto flutuante (0.1 + 0.2 != 0.3 em Double)
   * - Erros de arredondamento em cálculos financeiros
   * 
   * RECOMENDAÇÃO:
   * Use BigDecimal para valores monetários:
   * 
   * import java.math.BigDecimal;
   * 
   * @Column(name = "preco", precision = 18, scale = 2)
   *              private BigDecimal preco;
   * 
   *              JUSTIFICATIVA:
   *              - BigDecimal mantém precisão exata (sem erros de arredondamento)
   *              - Suporta operações aritméticas precisas
   *              - Padrão da indústria para valores monetários
   *              - Mapeia perfeitamente para NUMERIC(18,2) do SQL
   * 
   *              CONSTRAINT NO BANCO: NUMERIC(18, 2) (definido no schema SQL)
   */
  @Column(name = "preco") // Mapeia para coluna 'preco'
                          // DEVERIA TER:
                          // @Column(name = "preco", precision = 18, scale = 2)
                          // precision = total de dígitos
                          // scale = casas decimais
  private Double preco; // ⚠️ Trocar para BigDecimal em produção!

  // ============================================
  // CONSTRUTORES
  // ============================================
  // O JPA exige um construtor público sem argumentos (default constructor)
  // para criar instâncias via reflection.
  //
  // Se você adicionar um construtor parametrizado, deve adicionar explicitamente
  // o construtor vazio:
  //
  // public Produto() {
  // // Construtor vazio obrigatório para JPA
  // }
  //
  // public Produto(String id, String nome, String descricao, BigDecimal preco) {
  // this.id = id;
  // this.nome = nome;
  // this.descricao = descricao;
  // this.preco = preco;
  // }

  // ============================================
  // GETTERS E SETTERS (Encapsulamento)
  // ============================================
  // Necessários para:
  // - JPA/Hibernate acessar e modificar os campos (via reflection)
  // - Manter encapsulamento (atributos privados)
  // - Permitir validações futuras nos setters
  // - Frameworks JSON (Jackson) serializar/deserializar

  /**
   * Obtém o ID do produto.
   * 
   * @return ID único do produto
   */
  public String getId() {
    return id;
  }

  /**
   * Define o ID do produto.
   * 
   * IMPORTANTE:
   * - Deve ser chamado ANTES de persistir a entidade
   * - Recomenda-se usar UUID: produto.setId(UUID.randomUUID().toString())
   * - NÃO alterar após persistência (viola integridade referencial)
   * 
   * @param id ID único do produto (geralmente UUID)
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Obtém o preço do produto.
   * 
   * @return Preço em reais (Double - DEVERIA ser BigDecimal)
   */
  public Double getPreco() {
    return preco;
  }

  /**
   * Define o preço do produto.
   * 
   * VALIDAÇÕES RECOMENDADAS:
   * - Verificar se preço não é negativo
   * - Arredondar para 2 casas decimais
   * - Usar BigDecimal para precisão
   * 
   * @param preco Preço do produto em reais
   */
  public void setPreco(Double preco) {
    this.preco = preco;
  }

  /**
   * Obtém a descrição do produto.
   * 
   * @return Descrição detalhada (pode ser null)
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * Define a descrição do produto.
   * 
   * @param descricao Texto descritivo (máximo 300 caracteres)
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  /**
   * Obtém o nome do produto.
   * 
   * @return Nome do produto
   */
  public String getNome() {
    return nome;
  }

  /**
   * Define o nome do produto.
   * 
   * @param nome Nome do produto (máximo 50 caracteres)
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  // ============================================
  // MÉTODO toString() - Representação Textual
  // ============================================

  /**
   * Retorna uma representação em String do objeto Produto.
   * 
   * UTILIDADE:
   * - Facilita debugging (System.out.println(produto))
   * - Melhora logs (logger.info("Salvando: {}", produto))
   * - Útil em testes unitários
   * 
   * FORMATO DE SAÍDA:
   * Produto{id='abc-123', nome='Notebook', descricao='16GB RAM', preco=3500.0}
   * 
   * @return Representação textual do produto com todos os campos
   */
  @Override // Sobrescreve o método toString() da classe Object
  public String toString() {
    // Concatenação de string usando StringBuilder interno do Java
    // Retorna formato legível para humanos (não é JSON)
    return ("Produto{" +
        "id='" + id + '\'' + // ID entre aspas simples
        ", nome='" + nome + '\'' + // Nome entre aspas simples
        ", descricao='" + descricao + '\'' + // Descrição entre aspas simples
        ", preco=" + preco + // Preço sem aspas (número)
        '}');
  }

  // ============================================
  // MÉTODOS ADICIONAIS RECOMENDADOS
  // ============================================
  // Considere adicionar:
  //
  // 1. equals() e hashCode() - Essenciais para coleções e comparações:
  // @Override
  // public boolean equals(Object o) {
  // if (this == o) return true;
  // if (o == null || getClass() != o.getClass()) return false;
  // Produto produto = (Produto) o;
  // return Objects.equals(id, produto.id);
  // }
  //
  // @Override
  // public int hashCode() {
  // return Objects.hash(id);
  // }
  //
  // 2. Validações com Bean Validation:
  // @NotBlank(message = "Nome é obrigatório")
  // @Size(max = 50, message = "Nome deve ter no máximo 50 caracteres")
  // private String nome;
  //
  // @DecimalMin(value = "0.0", message = "Preço deve ser positivo")
  // private BigDecimal preco;

  // ============================================
  // MELHORIAS FUTURAS RECOMENDADAS
  // ============================================
  // 1. Trocar Double preco por BigDecimal
  // 2. Adicionar @GeneratedValue para auto-gerar ID
  // 3. Implementar equals() e hashCode()
  // 4. Adicionar anotações de validação (@NotNull, @Size, etc.)
  // 5. Adicionar auditoria (@CreatedDate, @LastModifiedDate)
  // 6. Considerar usar Lombok (@Data, @Entity, @Table)
  // 7. Adicionar relacionamentos se necessário (@OneToMany, etc.)
}