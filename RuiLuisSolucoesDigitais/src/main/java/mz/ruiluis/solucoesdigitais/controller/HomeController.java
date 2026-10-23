package mz.ruiluis.solucoesdigitais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mz.ruiluis.solucoesdigitais.service.ConfiguracaoService;

@Controller
public class HomeController {

    private final ConfiguracaoService configuracaoService;

    public HomeController(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }


    @GetMapping("/")
    public String inicio(Model model) {

        model.addAttribute(
                "config",
                configuracaoService.obter()
        );

        return "index";
    }


    @GetMapping("/solicitar")
    public String solicitar() {
        return "solicitar";
    }
}