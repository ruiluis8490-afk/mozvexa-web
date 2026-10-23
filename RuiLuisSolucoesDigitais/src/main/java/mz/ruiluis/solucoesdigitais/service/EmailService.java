package mz.ruiluis.solucoesdigitais.service;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

//@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envia um e-mail com o PDF do orçamento em anexo.
     *
     * @param destinatario e-mail do cliente
     * @param nomeCliente nome do cliente
     * @param numeroOrcamento número do orçamento
     * @param valor valor do orçamento
     * @param validade validade do orçamento
     * @param pdfBytes conteúdo do PDF
     */
    public void enviarOrcamento(
            String destinatario,
            String nomeCliente,
            String numeroOrcamento,
            String valor,
            String validade,
            byte[] pdfBytes
    ) {

        if (destinatario == null || destinatario.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O e-mail do cliente é obrigatório."
            );
        }

        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new IllegalArgumentException(
                    "O PDF do orçamento é obrigatório."
            );
        }

        try {

            MimeMessage mensagem = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            mensagem,
                            true,
                            StandardCharsets.UTF_8.name()
                    );

            /*
             * =====================================================
             * DESTINATÁRIO
             * =====================================================
             */

            helper.setTo(destinatario.trim());


            /*
             * =====================================================
             * ASSUNTO
             * =====================================================
             */

            helper.setSubject(
                    "Orçamento "
                            + numeroSeguro(numeroOrcamento)
                            + " — Rui Luís Soluções Digitais"
            );


            /*
             * =====================================================
             * CORPO DO E-MAIL
             * =====================================================
             */

            String corpo = construirCorpoEmail(
                    nomeCliente,
                    numeroOrcamento,
                    valor,
                    validade
            );

            helper.setText(
                    corpo,
                    false
            );


            /*
             * =====================================================
             * ANEXO PDF
             * =====================================================
             */

            String nomeFicheiro =
                    numeroSeguro(numeroOrcamento)
                            + ".pdf";

            ByteArrayResource recurso =
                    new ByteArrayResource(pdfBytes);

            helper.addAttachment(
                    nomeFicheiro,
                    recurso
            );


            /*
             * =====================================================
             * ENVIAR
             * =====================================================
             */

            mailSender.send(mensagem);

        } catch (MessagingException e) {

            throw new RuntimeException(
                    "Não foi possível enviar o orçamento por e-mail.",
                    e
            );
        }
    }


    /**
     * Constrói o texto do e-mail enviado ao cliente.
     */
    private String construirCorpoEmail(
            String nomeCliente,
            String numeroOrcamento,
            String valor,
            String validade
    ) {

        String nome =
                numeroSeguro(nomeCliente);

        return """
                Exmo.(a) Sr.(a) %s,

                Esperamos que se encontre bem.

                A Rui Luís — Soluções Digitais vem por este meio enviar
                o orçamento solicitado.

                ----------------------------------------
                DADOS DO ORÇAMENTO
                ----------------------------------------

                Orçamento: %s
                Valor: %s MT
                Validade: %s

                ----------------------------------------

                O orçamento encontra-se em anexo a este e-mail,
                em formato PDF.

                Caso tenha alguma questão, alteração ou necessidade
                de esclarecimento, estamos disponíveis para o ajudar.

                Agradecemos a sua preferência.

                Com os melhores cumprimentos,

                Rui Luís
                Soluções Digitais

                Tecnologia • Gestão • Soluções Digitais
                """.formatted(
                nome,
                numeroSeguro(numeroOrcamento),
                numeroSeguro(valor),
                numeroSeguro(validade)
        );
    }


    /**
     * Evita valores null no e-mail.
     */
    private String numeroSeguro(String valor) {

        if (valor == null || valor.trim().isEmpty()) {
            return "-";
        }

        return valor.trim();
    }
}