package mz.ruiluis.solucoesdigitais.model;

import jakarta.persistence.*;

@Entity
@Table(name = "configuracoes")
public class Configuracao {

    @Id
    private Long id = 1L;

    /* =========================================================
       EMPRESA
       ========================================================= */

    @Column(length = 150)
    private String nomeEmpresa;

    @Column(length = 150)
    private String emailEmpresa;

    @Column(length = 30)
    private String telefoneEmpresa;

    @Column(length = 30)
    private String whatsappEmpresa;

    @Column(length = 200)
    private String enderecoEmpresa;

    @Column(length = 50)
    private String username = "admin";


    /* =========================================================
       HERO
       ========================================================= */

    @Column(length = 100)
    private String heroBadge;

    @Column(columnDefinition = "TEXT")
    private String heroTitulo;

    @Column(columnDefinition = "TEXT")
    private String heroDescricao;


    /* =========================================================
       SERVIÇOS
       ========================================================= */

    @Column(columnDefinition = "TEXT")
    private String servicosTitulo;

    @Column(columnDefinition = "TEXT")
    private String servicosDescricao;


    /* =========================================================
       PROJECTOS
       ========================================================= */

    @Column(columnDefinition = "TEXT")
    private String projectosTitulo;

    @Column(columnDefinition = "TEXT")
    private String projectosDescricao;


    /* =========================================================
       SOBRE
       ========================================================= */

    @Column(columnDefinition = "TEXT")
    private String sobreTitulo;

    @Column(columnDefinition = "TEXT")
    private String sobreIntro;

    @Column(columnDefinition = "TEXT")
    private String sobreTexto1;

    @Column(columnDefinition = "TEXT")
    private String sobreTexto2;


    /* =========================================================
       PROCESSO
       ========================================================= */

    @Column(columnDefinition = "TEXT")
    private String processoTitulo;

    @Column(columnDefinition = "TEXT")
    private String processoDescricao;

    @Column(columnDefinition = "TEXT")
    private String processoFrase;


    /* =========================================================
       CTA
       ========================================================= */

    @Column(columnDefinition = "TEXT")
    private String ctaTitulo;

    @Column(columnDefinition = "TEXT")
    private String ctaDescricao;


    /* =========================================================
       CONTACTOS
       ========================================================= */

    @Column(columnDefinition = "TEXT")
    private String contactosTitulo;

    @Column(columnDefinition = "TEXT")
    private String contactosDescricao;

    @Column(length = 150)
    private String contactoHorario;

    @Column(length = 150)
    private String contactoResposta;

    @Column(length = 150)
    private String contactoAtendimento;


    /* =========================================================
       FOOTER
       ========================================================= */

    @Column(columnDefinition = "TEXT")
    private String footerFrase;

    @Column(length = 200)
    private String footerCopyright;


    public Configuracao() {
    }


    /* =========================================================
       GETTERS E SETTERS
       ========================================================= */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

    public String getEmailEmpresa() { return emailEmpresa; }
    public void setEmailEmpresa(String emailEmpresa) { this.emailEmpresa = emailEmpresa; }

    public String getTelefoneEmpresa() { return telefoneEmpresa; }
    public void setTelefoneEmpresa(String telefoneEmpresa) { this.telefoneEmpresa = telefoneEmpresa; }

    public String getWhatsappEmpresa() { return whatsappEmpresa; }
    public void setWhatsappEmpresa(String whatsappEmpresa) { this.whatsappEmpresa = whatsappEmpresa; }

    public String getEnderecoEmpresa() { return enderecoEmpresa; }
    public void setEnderecoEmpresa(String enderecoEmpresa) { this.enderecoEmpresa = enderecoEmpresa; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getHeroBadge() { return heroBadge; }
    public void setHeroBadge(String heroBadge) { this.heroBadge = heroBadge; }

    public String getHeroTitulo() { return heroTitulo; }
    public void setHeroTitulo(String heroTitulo) { this.heroTitulo = heroTitulo; }

    public String getHeroDescricao() { return heroDescricao; }
    public void setHeroDescricao(String heroDescricao) { this.heroDescricao = heroDescricao; }

    public String getServicosTitulo() { return servicosTitulo; }
    public void setServicosTitulo(String servicosTitulo) { this.servicosTitulo = servicosTitulo; }

    public String getServicosDescricao() { return servicosDescricao; }
    public void setServicosDescricao(String servicosDescricao) { this.servicosDescricao = servicosDescricao; }

    public String getProjectosTitulo() { return projectosTitulo; }
    public void setProjectosTitulo(String projectosTitulo) { this.projectosTitulo = projectosTitulo; }

    public String getProjectosDescricao() { return projectosDescricao; }
    public void setProjectosDescricao(String projectosDescricao) { this.projectosDescricao = projectosDescricao; }

    public String getSobreTitulo() { return sobreTitulo; }
    public void setSobreTitulo(String sobreTitulo) { this.sobreTitulo = sobreTitulo; }

    public String getSobreIntro() { return sobreIntro; }
    public void setSobreIntro(String sobreIntro) { this.sobreIntro = sobreIntro; }

    public String getSobreTexto1() { return sobreTexto1; }
    public void setSobreTexto1(String sobreTexto1) { this.sobreTexto1 = sobreTexto1; }

    public String getSobreTexto2() { return sobreTexto2; }
    public void setSobreTexto2(String sobreTexto2) { this.sobreTexto2 = sobreTexto2; }

    public String getProcessoTitulo() { return processoTitulo; }
    public void setProcessoTitulo(String processoTitulo) { this.processoTitulo = processoTitulo; }

    public String getProcessoDescricao() { return processoDescricao; }
    public void setProcessoDescricao(String processoDescricao) { this.processoDescricao = processoDescricao; }

    public String getProcessoFrase() { return processoFrase; }
    public void setProcessoFrase(String processoFrase) { this.processoFrase = processoFrase; }

    public String getCtaTitulo() { return ctaTitulo; }
    public void setCtaTitulo(String ctaTitulo) { this.ctaTitulo = ctaTitulo; }

    public String getCtaDescricao() { return ctaDescricao; }
    public void setCtaDescricao(String ctaDescricao) { this.ctaDescricao = ctaDescricao; }

    public String getContactosTitulo() { return contactosTitulo; }
    public void setContactosTitulo(String contactosTitulo) { this.contactosTitulo = contactosTitulo; }

    public String getContactosDescricao() { return contactosDescricao; }
    public void setContactosDescricao(String contactosDescricao) { this.contactosDescricao = contactosDescricao; }

    public String getContactoHorario() { return contactoHorario; }
    public void setContactoHorario(String contactoHorario) { this.contactoHorario = contactoHorario; }

    public String getContactoResposta() { return contactoResposta; }
    public void setContactoResposta(String contactoResposta) { this.contactoResposta = contactoResposta; }

    public String getContactoAtendimento() { return contactoAtendimento; }
    public void setContactoAtendimento(String contactoAtendimento) { this.contactoAtendimento = contactoAtendimento; }

    public String getFooterFrase() { return footerFrase; }
    public void setFooterFrase(String footerFrase) { this.footerFrase = footerFrase; }

    public String getFooterCopyright() { return footerCopyright; }
    public void setFooterCopyright(String footerCopyright) { this.footerCopyright = footerCopyright; }
}