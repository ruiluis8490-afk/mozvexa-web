package mz.ruiluis.solucoesdigitais.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mz.ruiluis.solucoesdigitais.model.Configuracao;
import mz.ruiluis.solucoesdigitais.model.Usuario;
import mz.ruiluis.solucoesdigitais.repository.UsuarioRepository;
import mz.ruiluis.solucoesdigitais.service.ConfiguracaoService;

@Controller
public class ConfiguracaoController {

    private final ConfiguracaoService configuracaoService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ConfiguracaoController(
            ConfiguracaoService configuracaoService,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.configuracaoService = configuracaoService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /* =====================================================
       GUARDAR DADOS DA EMPRESA
       ===================================================== */

    @PostMapping("/admin/definicoes/empresa")
    public String guardarEmpresa(

            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("telefone") String telefone,
            @RequestParam("whatsapp") String whatsapp,
            @RequestParam("endereco") String endereco,

            RedirectAttributes redirectAttributes
    ) {

        try {

            Configuracao c = configuracaoService.obter();

            c.setNomeEmpresa(nome);
            c.setEmailEmpresa(email);
            c.setTelefoneEmpresa(telefone);
            c.setWhatsappEmpresa(whatsapp);
            c.setEnderecoEmpresa(endereco);

            configuracaoService.guardar(c);

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Dados da empresa atualizados com sucesso."
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Erro ao guardar: " + e.getMessage()
            );
        }

        return "redirect:/admin/definicoes";
    }


    /* =====================================================
       ALTERAR USERNAME E/OU PALAVRA-PASSE
       ===================================================== */

    @PostMapping("/admin/definicoes/password")
    public String alterarCredenciais(

            @RequestParam("username") String novoUsername,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,

            RedirectAttributes redirectAttributes
    ) {

        try {

            /* ---------------------------------------------
               VALIDAR USERNAME
               --------------------------------------------- */

            if (novoUsername == null || novoUsername.isBlank()) {

                redirectAttributes.addFlashAttribute(
                        "erro",
                        "O utilizador não pode ficar vazio."
                );

                return "redirect:/admin/definicoes";
            }

            novoUsername = novoUsername.trim();


            /* ---------------------------------------------
               PROCURAR O UTILIZADOR ACTUAL
               --------------------------------------------- */

            Usuario usuario = usuarioRepository
                    .findAll()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "Nenhum utilizador encontrado."
                    ));


            /* ---------------------------------------------
               VERIFICAR SE O NOVO USERNAME JÁ EXISTE
               --------------------------------------------- */

            if (!usuario.getUsername().equals(novoUsername)) {

                if (usuarioRepository.findByUsername(novoUsername).isPresent()) {

                    redirectAttributes.addFlashAttribute(
                            "erro",
                            "Já existe um utilizador com esse nome."
                    );

                    return "redirect:/admin/definicoes";
                }

                usuario.setUsername(novoUsername);
            }


            /* ---------------------------------------------
               ALTERAR PASSWORD (se foi preenchida)
               --------------------------------------------- */

            boolean alterouPassword = false;

            if (password != null && !password.isBlank()) {

                if (password.length() < 6) {

                    redirectAttributes.addFlashAttribute(
                            "erro",
                            "A palavra-passe deve ter pelo menos 6 caracteres."
                    );

                    return "redirect:/admin/definicoes";
                }

                if (!password.equals(confirmPassword)) {

                    redirectAttributes.addFlashAttribute(
                            "erro",
                            "As palavras-passe não coincidem."
                    );

                    return "redirect:/admin/definicoes";
                }

                usuario.setPassword(
                        passwordEncoder.encode(password)
                );

                alterouPassword = true;
            }


            /* ---------------------------------------------
               GUARDAR
               --------------------------------------------- */

            usuarioRepository.save(usuario);


            /* ---------------------------------------------
               ACTUALIZAR A CONFIGURAÇÃO
               --------------------------------------------- */

            Configuracao c = configuracaoService.obter();
            c.setUsername(novoUsername);
            configuracaoService.guardar(c);


            /* ---------------------------------------------
               MENSAGEM
               --------------------------------------------- */

            if (alterouPassword) {

                redirectAttributes.addFlashAttribute(
                        "sucesso",
                        "Utilizador e palavra-passe atualizados com sucesso."
                );

            } else {

                redirectAttributes.addFlashAttribute(
                        "sucesso",
                        "Utilizador atualizado com sucesso."
                );
            }

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Erro: " + e.getMessage()
            );
        }

        return "redirect:/admin/definicoes";
    }
}