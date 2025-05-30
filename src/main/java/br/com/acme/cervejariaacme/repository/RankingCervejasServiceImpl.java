package br.com.acme.cervejariaacme.repository;

import br.com.acme.cervejariaacme.model.RankingCerveja;
import br.com.acme.cervejariaacme.service.RankingCervejasService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service@RequiredArgsConstructor
public class RankingCervejasServiceImpl implements RankingCervejasService {
    private final RankingCervejasRepository repository;
    private final AtomicInteger contador = new AtomicInteger();

    @Override
    @Cacheable(value = "cervejas")
    public List<RankingCerveja> findAllByDataRanking(LocalDate dataRanking) {
        List<RankingCerveja> ranking = new ArrayList<>();
        int chamada = contador.incrementAndGet();
        try{
            System.out.println("Executando método! Chamada #" + chamada);
            return repository.findAllByDataRanking(dataRanking);
        }catch (Exception e){
            return ranking;
        }
    }

    @Override
    public List<RankingCerveja> findAllByDataRankingWithoutCache(LocalDate dataRanking) {
        return repository.findAllByDataRanking(dataRanking);
    }

    @CacheEvict(value = "cervejas", allEntries = true)
    public void limparCache() {
        System.out.println("🚫 Cache de cervejas invalidado!");
    }
}
