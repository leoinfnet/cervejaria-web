package br.com.acme.cervejariaacme.conf;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {
    @Value("${spring.datasource.segredo}")
    private String segredo;

    @PostConstruct
    public void init(){
        System.out.println("Segredo do banco de dados: " + segredo);
    }
}
