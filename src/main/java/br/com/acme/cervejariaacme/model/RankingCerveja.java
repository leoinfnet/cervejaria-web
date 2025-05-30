package br.com.acme.cervejariaacme.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.io.Serializable;

@Entity
@Table(name = "ranking_cerveja")
@Getter @Setter
public class RankingCerveja implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cerveja_id")
    private Cerveja cerveja;

    @Column(name = "data_ranking", nullable = false)
    private LocalDate dataRanking;

    @Column(nullable = false)
    private Integer posicao;

    @Column
    private Double pontuacao; // se houver nota

    public RankingCerveja() {
    }

    public RankingCerveja(Cerveja cerveja, LocalDate dataRanking, Integer posicao, Double pontuacao) {
        this.cerveja = cerveja;
        this.dataRanking = dataRanking;
        this.posicao = posicao;
        this.pontuacao = pontuacao;
    }


}
