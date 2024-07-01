package springboot.svendas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean(name = "titleAplication")
    public String titleAplication() {
        return "PROJETO SPRING BOOT - VENDAS";
    }
}
