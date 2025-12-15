# 🛒 Produtos API

API RESTful desenvolvida em Java com Spring Boot para gerenciamento de produtos. Projeto criado como parte do curso "Profissionalize-se em Java com Spring Boot em um Guia Completo e atualizado do Zero ao Deploy na AWS" da Udemy.

## 📋 Sobre o Projeto

Esta é uma API de estudos que implementa operações CRUD completas (Create, Read, Update, Delete) para gerenciamento de produtos, utilizando as melhores práticas do ecossistema Spring Boot.

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.2.5** - Framework principal
- **Spring Data JPA** - Abstração de persistência de dados
- **H2 Database** - Banco de dados em memória
- **Maven** - Gerenciador de dependências
- **Lombok** - Redução de código boilerplate
- **Spring Boot DevTools** - Ferramentas de desenvolvimento
- **Spring Boot Actuator** - Monitoramento e métricas

## 📁 Estrutura do Projeto

```
src/main/java/io/github/odevfred/produtosapi/
├── controller/
│   └── ProdutoController.java      # Endpoints REST
├── model/
│   └── Produto.java                # Entidade JPA
├── repository/
│   └── ProdutoRepository.java      # Interface de persistência
└── ProdutosApiApplication.java     # Classe principal

src/main/resources/
├── application.yml                 # Configurações da aplicação
└── data.sql                        # Script de criação do banco
```

## 🔧 Funcionalidades

A API oferece os seguintes endpoints:

### Criar Produto
```http
POST /produtos
Content-Type: application/json

{
  "nome": "Notebook",
  "descricao": "Notebook Dell Inspiron 15",
  "preco": 3500.00
}
```

### Buscar Produto por ID
```http
GET /produtos/{id}
```

### Listar Produtos por Nome
```http
GET /produtos?nome=Notebook
```

### Atualizar Produto
```http
PUT /produtos/{id}
Content-Type: application/json

{
  "nome": "Notebook Atualizado",
  "descricao": "Nova descrição",
  "preco": 3800.00
}
```

### Deletar Produto
```http
DELETE /produtos/{id}
```

## ⚙️ Como Executar

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/oDevFred/produtosapi
cd produtosapi
```

2. Execute o projeto:
```bash
./mvnw spring-boot:run
```

Ou no Windows:
```bash
mvnw.cmd spring-boot:run
```

3. A API estará disponível em:
```
http://localhost:8080
```

## 💾 Banco de Dados H2

O projeto utiliza H2, um banco de dados em memória ideal para desenvolvimento e testes.

### Acessar o Console H2

1. Acesse: `http://localhost:8080/h2-console`
2. Use as credenciais:
   - **JDBC URL**: `jdbc:h2:mem:produtos`
   - **Username**: `sa`
   - **Password**: `admin`

> ⚠️ **Nota**: Os dados são perdidos ao reiniciar a aplicação, pois o H2 está configurado em memória.

## 📦 Dependências Principais

```xml
<!-- Spring Boot Starter Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Spring Boot Actuator -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## 🔍 Conceitos Aplicados

### Arquitetura em Camadas
- **Controller**: Recebe requisições HTTP e retorna respostas
- **Repository**: Interface de acesso aos dados
- **Model**: Representa a entidade do domínio

### Spring Data JPA
- Uso de `JpaRepository` para operações CRUD automáticas
- Query Methods personalizados (`findByNome`)
- Mapeamento objeto-relacional com anotações JPA

### REST API
- Uso de anotações `@RestController`, `@RequestMapping`
- Verbos HTTP adequados (GET, POST, PUT, DELETE)
- `@PathVariable` e `@RequestParam` para parâmetros

### Boas Práticas
- Injeção de dependência via construtor
- Uso de UUID para IDs únicos
- Configuração externalizada em `application.yml`
- Comentários detalhados no código

## 📚 Recursos de Aprendizado

Este projeto foi desenvolvido seguindo o curso:
- **Curso**: [Profissionalize-se em Java com Spring Boot](https://www.udemy.com/course/spring-boot-expert)
- **Plataforma**: Udemy
- **Nível**: Iniciante ao Avançado

## 👨‍💻 Autor

Desenvolvido por **odevfred** como projeto de estudos.

## 📄 Licença

Este projeto é destinado apenas para fins educacionais.

---

⭐ Se este projeto te ajudou nos estudos, considere dar uma estrela no repositório!