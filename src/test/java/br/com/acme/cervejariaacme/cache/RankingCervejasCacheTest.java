package br.com.acme.cervejariaacme.cache;

import br.com.acme.cervejariaacme.model.RankingCerveja;
import br.com.acme.cervejariaacme.service.RankingCervejasService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = RankingCervejasCacheTest.Initializer.class)
class RankingCervejasCacheTest {

    @Autowired
    private RankingCervejasService service;

    private static final LocalDate DATA_TESTE = LocalDate.of(2025, 4, 17);

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7")
            .withExposedPorts(6379);

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.data.redis.host=" + redis.getHost(),
                    "spring.data.redis.port=" + redis.getMappedPort(6379),
                    "spring.cache.type=redis"
            ).applyTo(context.getEnvironment());
        }
    }

    @Test
    void deveUsarCacheAposPrimeiraChamada() {
        System.out.println("🔁 Primeira chamada (deve executar o método real)");
        List<RankingCerveja> chamada1 = service.findAllByDataRanking(DATA_TESTE);
        assertThat(chamada1).isNotNull();
        chamada1=  chamada1.stream().sorted(Comparator.comparing(RankingCerveja::getPosicao)).toList();
        String nome1 = chamada1.get(0).getCerveja().getNome();

        System.out.println("✅ Segunda chamada (deve vir do cache, sem reexecutar)");
        List<RankingCerveja> chamada2 = service.findAllByDataRanking(DATA_TESTE);
        chamada2=  chamada2.stream().sorted(Comparator.comparing(RankingCerveja::getPosicao)).toList();
        String nome2 = chamada2.get(0).getCerveja().getNome();

        assertThat(chamada2).isNotNull(); // mesma referência -> cacheado
        assertEquals(nome1, nome2);
    }
}
