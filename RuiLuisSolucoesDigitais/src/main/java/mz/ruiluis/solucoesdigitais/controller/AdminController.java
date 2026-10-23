package mz.ruiluis.solucoesdigitais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mz.ruiluis.solucoesdigitais.model.EstadoPedido;
import mz.ruiluis.solucoesdigitais.service.ConfiguracaoService;
import mz.ruiluis.solucoesdigitais.service.OrcamentoService;
import mz.ruiluis.solucoesdigitais.service.PedidoServicoService;

@Controller
public class AdminController {

    private final PedidoServicoService pedidoServicoService;
    private final OrcamentoService orcamentoService;
    private final ConfiguracaoService configuracaoService;

    public AdminController(
            PedidoServicoService pedidoServicoService,
            OrcamentoService orcamentoService,
            ConfiguracaoService configuracaoService
    ) {
        this.pedidoServicoService = pedidoServicoService;
        this.orcamentoService = orcamentoService;
        this.configuracaoService = configuracaoService;
    }


    /* =====================================================
       LOGIN
       ===================================================== */

    @GetMapping("/login")
    public String login() {

        return "login";
    }


    /* =====================================================
       PAINEL DE ADMINISTRAÇÃO
       ===================================================== */

    @GetMapping("/admin")
    public String admin(Model model) {

        model.addAttribute(
                "pedidos",
                pedidoServicoService.listarPedidos()
        );

        model.addAttribute(
                "orcamentos",
                orcamentoService.listarOrcamentos()
        );

        model.addAttribute(
                "totalPedidos",
                pedidoServicoService.contarPedidos()
        );

        model.addAttribute(
                "pedidosNovos",
                pedidoServicoService.contarPorEstado(EstadoPedido.NOVA)
        );

        model.addAttribute(
                "pedidosEmAnalise",
                pedidoServicoService.contarPorEstado(EstadoPedido.EM_ANALISE)
        );

        model.addAttribute(
                "pedidosConcluidos",
                pedidoServicoService.contarPorEstado(EstadoPedido.CONCLUIDA)
        );

        return "admin";
    }


    /* =====================================================
       DEFINIÇÕES
       ===================================================== */

    @GetMapping("/admin/definicoes")
    public String definicoes(Model model) {

        model.addAttribute(
                "configuracao",
                configuracaoService.obter()
        );

        return "definicoes";
    }
}