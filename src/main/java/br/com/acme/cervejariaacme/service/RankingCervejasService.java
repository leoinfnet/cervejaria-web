package br.com.acme.cervejariaacme.service;

import br.com.acme.cervejariaacme.model.RankingCerveja;

import java.time.LocalDate;
import java.util.List;

public interface RankingCervejasService {
    List<RankingCerveja> findAllByDataRanking(LocalDate dataRanking);
    List<RankingCerveja> findAllByDataRankingWithoutCache(LocalDate dataRanking);
}
