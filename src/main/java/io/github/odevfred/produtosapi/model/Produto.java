package io.github.odevfred.produtosapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// POJO - Plain Old Java Object (Objeto Java Simples)
// 1. Marca a classe como uma Entidade JPA, indicando que ela representa uma tabela no banco de dados.
@Entity
// 2. Especifica o nome da tabela no banco de dados (neste caso, "produto"). Não
// é obrigatório se o nome da classe for o mesmo do banco (ex: Produto ->
// produto).
@Table(name = "produto")
public class Produto {

  // ---------------------- Atributos (Campos da Tabela) ----------------------

  @Id // 3. Anotação essencial: Define 'id' como a chave primária da tabela.
  @Column(name = "id") // Mapeia o campo 'id' para a coluna 'id' do banco.
                       // Não é obrigatório se o nome da coluna for o mesmo do campo.
  private String id;

  @Column(name = "nome") // Mapeia o campo 'nome' para a coluna 'nome'.
  private String nome;

  @Column(name = "descricao") // Mapeia o campo 'descricao' para a coluna 'descricao'.
  private String descricao;

  @Column(name = "preco") // Mapeia o campo 'preco' para a coluna 'preco'.
  private Double preco;

  // ---------------------- Getters e Setters ----------------------
  // Estes métodos são necessários para acessar e modificar os atributos da
  // Entidade.

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  // ---------------------- Método toString() ----------------------

  @Override // Sobrescreve o método toString padrão da classe Object.
  public String toString() {
    // Implementação para retornar uma representação legível do objeto Produto (útil
    // para logs/debug).
    return ("Produto{" +
        "id='" +
        id +
        '\'' +
        ", nome='" +
        nome +
        '\'' +
        ", descricao='" +
        descricao +
        '\'' +
        ", preco=" +
        preco +
        '}');
  }
}