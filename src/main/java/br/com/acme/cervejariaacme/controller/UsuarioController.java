package br.com.acme.cervejariaacme.controller;

import br.com.acme.cervejariaacme.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    @GetMapping
    public String getAll(Model model){
        this.usuarioService.findAll();
        model.addAttribute("usuarios", usuarioService.findAll());
        return "/usuario/index";
    }
}
