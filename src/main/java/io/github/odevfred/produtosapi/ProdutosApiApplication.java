package io.github.odevfred.produtosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 * É o ponto de entrada para a execução do programa.
 */

// Anotação de conveniência que habilita a configuração automática, varredura de
// componentes e se comporta como uma classe de configuração.
@SpringBootApplication
public class ProdutosApiApplication {

	/**
	 * Método principal (main) padrão do Java.
	 * É o ponto de partida da Java Virtual Machine (JVM).
	 *
	 * @param args Argumentos de linha de comando.
	 */
	public static void main(String[] args) {
		// Inicializa a aplicação Spring Boot.
		// Este método configura o ambiente, inicia o ApplicationContext
		// e, se necessário, inicia o servidor web embutido (ex: Tomcat).
		SpringApplication.run(ProdutosApiApplication.class, args);
	}
}