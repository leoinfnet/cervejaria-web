package br.com.acme.cervejariaacme.repository;

import br.com.acme.cervejariaacme.model.RankingCerveja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RankingCervejasRepository extends JpaRepository<RankingCerveja,Long> {
    List<RankingCerveja> findAllByDataRanking(LocalDate dataRanking);

}
