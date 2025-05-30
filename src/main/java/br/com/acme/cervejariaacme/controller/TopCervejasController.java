package br.com.acme.cervejariaacme.controller;

import br.com.acme.cervejariaacme.model.Cerveja;
import br.com.acme.cervejariaacme.model.RankingCerveja;
import br.com.acme.cervejariaacme.service.RankingCervejasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/top-cervejas")
@RequiredArgsConstructor
public class TopCervejasController {
    private final RankingCervejasService rankingCervejasService;
    @GetMapping("/index")
    public String getAllTipos(Model model , @ModelAttribute("sucesso") Object sucesso,
                              @ModelAttribute("sucessoDelete") Object sucessoDelete,
                              @ModelAttribute("message") Object message
    ){
        List<RankingCerveja> allByDataRanking = rankingCervejasService.findAllByDataRanking(LocalDate.of(2025, 4, 17));
        allByDataRanking=  allByDataRanking.stream().sorted(Comparator.comparing(RankingCerveja::getPosicao)).toList();

        List<Cerveja> todas = new ArrayList<>();
        List<Cerveja> ranking = new ArrayList<>();

        model.addAttribute("ranking", allByDataRanking);

        return "topCervejas/index";
    }
}
