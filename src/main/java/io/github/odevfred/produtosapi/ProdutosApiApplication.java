package io.github.odevfred.produtosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot - Produtos API.
 * 
 * Esta é a classe de bootstrap (inicialização) que serve como ponto de entrada
 * para toda a aplicação. Quando executada, ela inicializa o container Spring,
 * carrega todas as configurações e inicia o servidor web embutido.
 * 
 * @author odevfred
 * @version 1.0
 * @since 2025
 */

// @SpringBootApplication é uma anotação de conveniência que combina três
// anotações importantes:
// 1. @Configuration: Marca a classe como fonte de definições de beans para o
// contexto da aplicação
// 2. @EnableAutoConfiguration: Habilita a configuração automática do Spring
// Boot baseada nas dependências do classpath
// 3. @ComponentScan: Habilita a varredura de componentes no pacote atual e
// subpacotes
// (procura por @Component, @Service, @Repository, @Controller, etc.)
@SpringBootApplication
public class ProdutosApiApplication {

	/**
	 * Método principal (main) - ponto de entrada da aplicação Java.
	 * 
	 * Este método é invocado pela JVM (Java Virtual Machine) ao executar o
	 * programa.
	 * Ele é responsável por inicializar todo o contexto do Spring Boot.
	 * 
	 * FLUXO DE EXECUÇÃO:
	 * 1. JVM invoca o método main()
	 * 2. SpringApplication.run() é chamado
	 * 3. Spring Boot inicia o ApplicationContext (container de injeção de
	 * dependências)
	 * 4. Carrega todas as configurações (application.yml, properties, etc.)
	 * 5. Escaneia e registra todos os beans
	 * (@Component, @Service, @Repository, @Controller)
	 * 6. Configura automaticamente o DataSource, JPA, H2 Console baseado nas
	 * dependências
	 * 7. Inicia o servidor web embutido (Tomcat por padrão) na porta 8080
	 * 8. Aplica todas as configurações de segurança, CORS, filtros, etc.
	 * 9. Aplicação fica pronta para receber requisições HTTP
	 *
	 * @param args Argumentos de linha de comando passados ao executar a aplicação.
	 *             Exemplos de uso:
	 *             - java -jar produtos-api.jar --server.port=9090 (muda a porta)
	 *             - java -jar produtos-api.jar --spring.profiles.active=prod (ativa
	 *             profile)
	 */
	public static void main(String[] args) {
		// SpringApplication.run() é o método que inicializa toda a aplicação Spring
		// Boot
		// Parâmetros:
		// - ProdutosApiApplication.class: classe principal que contém
		// @SpringBootApplication
		// - args: argumentos de linha de comando que podem sobrescrever configurações
		//
		// Este método retorna um ApplicationContext que pode ser usado para acessar
		// beans,
		// mas geralmente não é necessário capturar o retorno em aplicações simples
		SpringApplication.run(ProdutosApiApplication.class, args);

		// APÓS A EXECUÇÃO DESTE MÉTODO:
		// ✅ Aplicação estará rodando
		// ✅ Servidor web escutando na porta configurada (padrão: 8080)
		// ✅ Banco H2 em memória inicializado
		// ✅ Console H2 acessível em http://localhost:8080/h2-console
		// ✅ Endpoints REST prontos para receber requisições
		// ✅ Logs exibindo informações de startup no console
	}

	// DICAS DE TROUBLESHOOTING:
	// - Se a porta 8080 estiver em uso, adicionar --server.port=OUTRA_PORTA nos
	// args
	// - Para debug detalhado, adicionar --debug nos argumentos
	// - Para ver todas as propriedades ativas, usar --spring.config.location=...
	// - Em caso de erro de conexão com banco, verificar configurações em
	// application.yml
}