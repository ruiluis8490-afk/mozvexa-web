package mz.ruiluis.solucoesdigitais.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mz.ruiluis.solucoesdigitais.model.Configuracao;
import mz.ruiluis.solucoesdigitais.service.ConfiguracaoService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ConteudoController {

    private final ConfiguracaoService configuracaoService;

    public ConteudoController(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }


    /* =====================================================
       MOSTRAR PÁGINA
       ===================================================== */

    @GetMapping("/admin/conteudo")
    public String conteudo(Model model) {
        model.addAttribute("config", configuracaoService.obter());
        return "conteudo";
    }


    /* =====================================================
       PREVIEW — devolve JSON com o texto actual
       ===================================================== */

    @GetMapping("/admin/conteudo/preview")
    @ResponseBody
    public Map<String, String> preview() {

        Configuracao c = configuracaoService.obter();

        Map<String, String> dados = new HashMap<>();

        dados.put("heroBadge", c.getHeroBadge());
        dados.put("heroTitulo", c.getHeroTitulo());
        dados.put("heroDescricao", c.getHeroDescricao());

        dados.put("servicosTitulo", c.getServicosTitulo());
        dados.put("servicosDescricao", c.getServicosDescricao());

        dados.put("projectosTitulo", c.getProjectosTitulo());
        dados.put("projectosDescricao", c.getProjectosDescricao());

        dados.put("sobreTitulo", c.getSobreTitulo());
        dados.put("sobreIntro", c.getSobreIntro());
        dados.put("sobreTexto1", c.getSobreTexto1());
        dados.put("sobreTexto2", c.getSobreTexto2());

        dados.put("processoTitulo", c.getProcessoTitulo());
        dados.put("processoDescricao", c.getProcessoDescricao());
        dados.put("processoFrase", c.getProcessoFrase());

        dados.put("ctaTitulo", c.getCtaTitulo());
        dados.put("ctaDescricao", c.getCtaDescricao());

        dados.put("contactosTitulo", c.getContactosTitulo());
        dados.put("contactosDescricao", c.getContactosDescricao());
        dados.put("contactoHorario", c.getContactoHorario());
        dados.put("contactoResposta", c.getContactoResposta());
        dados.put("contactoAtendimento", c.getContactoAtendimento());

        dados.put("footerFrase", c.getFooterFrase());
        dados.put("footerCopyright", c.getFooterCopyright());

        return dados;
    }


    /* =====================================================
       GUARDAR POR SECÇÃO
       ===================================================== */

    @PostMapping("/admin/conteudo/hero")
    public String guardarHero(
            @RequestParam("heroBadge") String heroBadge,
            @RequestParam("heroTitulo") String heroTitulo,
            @RequestParam("heroDescricao") String heroDescricao,
            RedirectAttributes ra) {

        try {
            Configuracao c = configuracaoService.obter();
            c.setHeroBadge(heroBadge);
            c.setHeroTitulo(heroTitulo);
            c.setHeroDescricao(heroDescricao);
            configuracaoService.guardar(c);
            ra.addFlashAttribute("sucesso", "Secção HERO actualizada.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }

        return "redirect:/admin/conteudo#sec-hero";
    }


    @PostMapping("/admin/conteudo/servicos")
    public String guardarServicos(
            @RequestParam("servicosTitulo") String servicosTitulo,
            @RequestParam("servicosDescricao") String servicosDescricao,
            RedirectAttributes ra) {

        try {
            Configuracao c = configuracaoService.obter();
            c.setServicosTitulo(servicosTitulo);
            c.setServicosDescricao(servicosDescricao);
            configuracaoService.guardar(c);
            ra.addFlashAttribute("sucesso", "Secção SERVIÇOS actualizada.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }

        return "redirect:/admin/conteudo#sec-servicos";
    }


    @PostMapping("/admin/conteudo/projectos")
    public String guardarProjectos(
            @RequestParam("projectosTitulo") String projectosTitulo,
            @RequestParam("projectosDescricao") String projectosDescricao,
            RedirectAttributes ra) {

        try {
            Configuracao c = configuracaoService.obter();
            c.setProjectosTitulo(projectosTitulo);
            c.setProjectosDescricao(projectosDescricao);
            configuracaoService.guardar(c);
            ra.addFlashAttribute("sucesso", "Secção PROJECTOS actualizada.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }

        return "redirect:/admin/conteudo#sec-projectos";
    }


    @PostMapping("/admin/conteudo/sobre")
    public String guardarSobre(
            @RequestParam("sobreTitulo") String sobreTitulo,
            @RequestParam("sobreIntro") String sobreIntro,
            @RequestParam("sobreTexto1") String sobreTexto1,
            @RequestParam("sobreTexto2") String sobreTexto2,
            RedirectAttributes ra) {

        try {
            Configuracao c = configuracaoService.obter();
            c.setSobreTitulo(sobreTitulo);
            c.setSobreIntro(sobreIntro);
            c.setSobreTexto1(sobreTexto1);
            c.setSobreTexto2(sobreTexto2);
            configuracaoService.guardar(c);
            ra.addFlashAttribute("sucesso", "Secção SOBRE actualizada.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }

        return "redirect:/admin/conteudo#sec-sobre";
    }


    @PostMapping("/admin/conteudo/processo")
    public String guardarProcesso(
            @RequestParam("processoTitulo") String processoTitulo,
            @RequestParam("processoDescricao") String processoDescricao,
            @RequestParam("processoFrase") String processoFrase,
            RedirectAttributes ra) {

        try {
            Configuracao c = configuracaoService.obter();
            c.setProcessoTitulo(processoTitulo);
            c.setProcessoDescricao(processoDescricao);
            c.setProcessoFrase(processoFrase);
            configuracaoService.guardar(c);
            ra.addFlashAttribute("sucesso", "Secção PROCESSO actualizada.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }

        return "redirect:/admin/conteudo#sec-processo";
    }


    @PostMapping("/admin/conteudo/cta")
    public String guardarCta(
            @RequestParam("ctaTitulo") String ctaTitulo,
            @RequestParam("ctaDescricao") String ctaDescricao,
            RedirectAttributes ra) {

        try {
            Configuracao c = configuracaoService.obter();
            c.setCtaTitulo(ctaTitulo);
            c.setCtaDescricao(ctaDescricao);
            configuracaoService.guardar(c);
            ra.addFlashAttribute("sucesso", "Secção CTA actualizada.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }

        return "redirect:/admin/conteudo#sec-cta";
    }


    @PostMapping("/admin/conteudo/contactos")
    public String guardarContactos(
            @RequestParam("contactosTitulo") String contactosTitulo,
            @RequestParam("contactosDescricao") String contactosDescricao,
            @RequestParam("contactoHorario") String contactoHorario,
            @RequestParam("contactoResposta") String contactoResposta,
            @RequestParam("contactoAtendimento") String contactoAtendimento,
            RedirectAttributes ra) {

        try {
            Configuracao c = configuracaoService.obter();
            c.setContactosTitulo(contactosTitulo);
            c.setContactosDescricao(contactosDescricao);
            c.setContactoHorario(contactoHorario);
            c.setContactoResposta(contactoResposta);
            c.setContactoAtendimento(contactoAtendimento);
            configuracaoService.guardar(c);
            ra.addFlashAttribute("sucesso", "Secção CONTACTOS actualizada.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }

        return "redirect:/admin/conteudo#sec-contactos";
    }


    @PostMapping("/admin/conteudo/footer")
    public String guardarFooter(
            @RequestParam("footerFrase") String footerFrase,
            @RequestParam("footerCopyright") String footerCopyright,
            RedirectAttributes ra) {

        try {
            Configuracao c = configuracaoService.obter();
            c.setFooterFrase(footerFrase);
            c.setFooterCopyright(footerCopyright);
            configuracaoService.guardar(c);
            ra.addFlashAttribute("sucesso", "Secção FOOTER actualizada.");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }

        return "redirect:/admin/conteudo#sec-footer";
    }


    /* =====================================================
       GUARDAR TUDO (compatibilidade)
       ===================================================== */

    @PostMapping("/admin/conteudo")
    public String guardarTudo(
            @RequestParam("heroBadge") String heroBadge,
            @RequestParam("heroTitulo") String heroTitulo,
            @RequestParam("heroDescricao") String heroDescricao,
            @RequestParam("servicosTitulo") String servicosTitulo,
            @RequestParam("servicosDescricao") String servicosDescricao,
            @RequestParam("projectosTitulo") String projectosTitulo,
            @RequestParam("projectosDescricao") String projectosDescricao,
            @RequestParam("sobreTitulo") String sobreTitulo,
            @RequestParam("sobreIntro") String sobreIntro,
            @RequestParam("sobreTexto1") String sobreTexto1,
            @RequestParam("sobreTexto2") String sobreTexto2,
            @RequestParam("processoTitulo") String processoTitulo,
            @RequestParam("processoDescricao") String processoDescricao,
            @RequestParam("processoFrase") String processoFrase,
            @RequestParam("ctaTitulo") String ctaTitulo,
            @RequestParam("ctaDescricao") String ctaDescricao,
            @RequestParam("contactosTitulo") String contactosTitulo,
            @RequestParam("contactosDescricao") String contactosDescricao,
            @RequestParam("contactoHorario") String contactoHorario,
            @RequestParam("contactoResposta") String contactoResposta,
            @RequestParam("contactoAtendimento") String contactoAtendimento,
            @RequestParam("footerFrase") String footerFrase,
            @RequestParam("footerCopyright") String footerCopyright,
            RedirectAttributes ra) {

        try {
            Configuracao c = configuracaoService.obter();

            c.setHeroBadge(heroBadge);
            c.setHeroTitulo(heroTitulo);
            c.setHeroDescricao(heroDescricao);

            c.setServicosTitulo(servicosTitulo);
            c.setServicosDescricao(servicosDescricao);

            c.setProjectosTitulo(projectosTitulo);
            c.setProjectosDescricao(projectosDescricao);

            c.setSobreTitulo(sobreTitulo);
            c.setSobreIntro(sobreIntro);
            c.setSobreTexto1(sobreTexto1);
            c.setSobreTexto2(sobreTexto2);

            c.setProcessoTitulo(processoTitulo);
            c.setProcessoDescricao(processoDescricao);
            c.setProcessoFrase(processoFrase);

            c.setCtaTitulo(ctaTitulo);
            c.setCtaDescricao(ctaDescricao);

            c.setContactosTitulo(contactosTitulo);
            c.setContactosDescricao(contactosDescricao);
            c.setContactoHorario(contactoHorario);
            c.setContactoResposta(contactoResposta);
            c.setContactoAtendimento(contactoAtendimento);

            c.setFooterFrase(footerFrase);
            c.setFooterCopyright(footerCopyright);

            configuracaoService.guardar(c);

            ra.addFlashAttribute("sucesso", "Todo o conteúdo foi actualizado.");

        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Erro: " + e.getMessage());
        }

        return "redirect:/admin/conteudo";
    }
}