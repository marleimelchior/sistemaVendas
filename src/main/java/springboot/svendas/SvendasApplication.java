package springboot.svendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.svendas.domain.entity.Cliente;
import springboot.svendas.domain.repositorio.Clientes;

import java.util.List;

@SpringBootApplication
@RestController
public class SvendasApplication {

	@Bean
	public CommandLineRunner init(@Autowired Clientes clientes){
		return args -> {
			System.out.println("Salvando clientes");
			clientes.save(new Cliente("Marlei"));
			clientes.save(new Cliente("Fernanda"));

			List<Cliente> result = clientes.encontrarPorNome("Marlei");
			result.forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SvendasApplication.class, args);
	}

}
