package br.com.acme.cervejariaacme.cache;

import br.com.acme.cervejariaacme.model.RankingCerveja;
import br.com.acme.cervejariaacme.service.RankingCervejasService;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.ToxiproxyContainer;
import org.testcontainers.containers.ToxiproxyContainer.ContainerProxy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = RankingCervejaRedisFailoverTest.Initializer.class)
public class RankingCervejaRedisFailoverTest {

    static final Network network = Network.newNetwork();

    @Container
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7"))
            .withExposedPorts(6379)
            .withNetwork(network)
            .withNetworkAliases("redis");

    @Container
    static ToxiproxyContainer toxiproxy = new ToxiproxyContainer(DockerImageName.parse("shopify/toxiproxy"))
            .withNetwork(network);

    static ContainerProxy redisProxy;

    @Autowired
    RankingCervejasService rankingService;

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            redisProxy = toxiproxy.getProxy(redis, 6379);

            TestPropertyValues.of(
                    "spring.data.redis.host=" + toxiproxy.getHost(),
                    "spring.data.redis.port=" + redisProxy.getProxyPort(),
                    "spring.cache.type=redis"
            ).applyTo(context.getEnvironment());
        }
    }

    private static final LocalDate DIA_TESTE = LocalDate.of(2025, 4, 15);

    @BeforeEach
    void resetToxics() throws IOException {
        redisProxy.toxics().getAll().forEach(toxic -> {
            try {
                toxic.remove(); // ✅ forma correta
            } catch (IOException e) {
                throw new RuntimeException("Erro ao remover toxic: " + toxic.getName(), e);
            }
        });
    }

    @Test
    void deveConsultarBancoQuandoRedisFunciona() {
        System.out.println("🔁 Consulta inicial com Redis OK");
        List<RankingCerveja> resultado = rankingService.findAllByDataRanking(DIA_TESTE);
        assertThat(resultado).isNotEmpty();
    }

    @Test
    void deveConsultarBancoMesmoSeRedisFalhar() throws IOException {
        System.out.println(" Simulando queda total do Redis");
        redisProxy.toxics().latency("latency-timeout", ToxicDirection.DOWNSTREAM, 10_000);


        List<RankingCerveja> resultado = rankingService.findAllByDataRanking(DIA_TESTE);
        assertThat(resultado).isNotEmpty();
        System.out.println(" Redis falhou, mas banco respondeu normalmente.");
    }
}
