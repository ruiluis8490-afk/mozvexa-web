package mz.ruiluis.solucoesdigitais.service;

import org.springframework.stereotype.Service;

import mz.ruiluis.solucoesdigitais.model.Configuracao;
import mz.ruiluis.solucoesdigitais.repository.ConfiguracaoRepository;

@Service
public class ConfiguracaoService {

    private final ConfiguracaoRepository repository;

    public ConfiguracaoService(ConfiguracaoRepository repository) {
        this.repository = repository;
    }

    public Configuracao obter() {

        return repository.findById(1L).orElseGet(() -> {

            Configuracao c = new Configuracao();

            c.setId(1L);

            /* EMPRESA */
            c.setNomeEmpresa("MozVexa — Soluções Digitais");
            c.setEmailEmpresa("MozVexa@solucoes.co.mz");
            c.setTelefoneEmpresa("84 902 3562");
            c.setWhatsappEmpresa("87 982 3562");
            c.setEnderecoEmpresa("Maputo, Moçambique");
            c.setUsername("admin");

            /* HERO */
            c.setHeroBadge("Desenvolvimento de soluções digitais");
            c.setHeroTitulo("Transformamos ideias em");
            c.setHeroDescricao("Desenvolvemos sistemas, aplicações web, aplicações Android e soluções de gestão que ajudam empresas e pessoas a transformar problemas em resultados.");

            /* SERVIÇOS */
            c.setServicosTitulo("Soluções para diferentes necessidades.");
            c.setServicosDescricao("Da ideia inicial até ao sistema final, desenvolvemos soluções adaptadas às necessidades de cada projecto.");

            /* PROJECTOS */
            c.setProjectosTitulo("Alguns projectos desenvolvidos.");
            c.setProjectosDescricao("Projectos reais desenvolvidos para resolver problemas concretos através da tecnologia.");

            /* SOBRE */
            c.setSobreTitulo("Tecnologia com propósito.");
            c.setSobreIntro("A MozVexa é uma empresa moçambicana de desenvolvimento de soluções digitais. Unimos gestão e informática para criar sistemas de gestão, aplicações móveis e web, automação e websites. Mais do que código, entregamos tecnologia com propósito — feita para resolver problemas reais e impulsionar negócios.");
            c.setSobreTexto1("O foco da MozVexa é transformar ideias e necessidades reais em sistemas, aplicações e ferramentas digitais que sejam úteis, organizadas e fáceis de utilizar.");
            c.setSobreTexto2("A MozVexa trabalha com tecnologias como Java, Spring Boot, MySQL, Kotlin, Android, JavaScript, HTML, CSS, etc... unindo competências técnicas sólidas a princípios de gestão para criar soluções digitais eficientes, escaláveis e alinhadas às necessidades reais de cada negócio.");

            /* PROCESSO */
            c.setProcessoTitulo("Da ideia à solução.");
            c.setProcessoDescricao("Um processo simples, transparente e organizado para transformar uma necessidade numa solução digital funcional.");
            c.setProcessoFrase("Cada projecto é pensado de acordo com as necessidades específicas do cliente.");

            /* CTA */
            c.setCtaTitulo("Vamos transformar a sua ideia em realidade.");
            c.setCtaDescricao("Conte-nos o que pretende desenvolver e vamos encontrar uma solução.");

            /* CONTACTOS */
            c.setContactosTitulo("Vamos criar algo juntos.");
            c.setContactosDescricao("Tem uma ideia, um projecto ou uma necessidade que pode ser resolvida através da tecnologia? Fale conosco.");
            c.setContactoHorario("Segunda-feira a Sexta-feira das 07:00 às 18:00 e Sábado das 07:00 às 15:30");
            c.setContactoResposta("Cada pedido é analisado individualmente");
            c.setContactoAtendimento("Contacto simples através da Internet");

            /* FOOTER */
            c.setFooterFrase("Transformamos ideias em soluções digitais.");
            c.setFooterCopyright("MozVexa — Soluções Digitais");

            return repository.save(c);
        });
    }

    public Configuracao guardar(Configuracao configuracao) {
        configuracao.setId(1L);
        return repository.save(configuracao);
    }
}