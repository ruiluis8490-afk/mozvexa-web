package mz.ruiluis.solucoesdigitais.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import mz.ruiluis.solucoesdigitais.model.Orcamento;
import mz.ruiluis.solucoesdigitais.model.PedidoServico;

@Service
public class OrcamentoPdfService {

	// =========================================================
	// FORMATAÇÃO
	// =========================================================

	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final NumberFormat FORMATO_MOEDA = NumberFormat.getNumberInstance(new Locale("pt", "MZ"));

	static {
		FORMATO_MOEDA.setMinimumFractionDigits(2);
		FORMATO_MOEDA.setMaximumFractionDigits(2);
	}

	// =========================================================
	// FONTES PADRÃO PDFBOX
	// =========================================================

	private static final PDType1Font FONTE_NORMAL = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

	private static final PDType1Font FONTE_NEGRITO = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

	// =========================================================
	// CORES
	// =========================================================

	private static final float AZUL_R = 0.03f;
	private static final float AZUL_G = 0.08f;
	private static final float AZUL_B = 0.18f;

	private static final float CINZA_R = 0.40f;
	private static final float CINZA_G = 0.43f;
	private static final float CINZA_B = 0.47f;

	private static final float CINZA_CLARO_R = 0.95f;
	private static final float CINZA_CLARO_G = 0.96f;
	private static final float CINZA_CLARO_B = 0.97f;

	private static final float BRANCO_R = 1.0f;
	private static final float BRANCO_G = 1.0f;
	private static final float BRANCO_B = 1.0f;

	// =========================================================
	// GERAR PDF
	// =========================================================

	public byte[] gerarPdf(Orcamento orcamento) {

		if (orcamento == null) {
			throw new IllegalArgumentException("O orçamento é obrigatório.");
		}

		if (orcamento.getPedido() == null) {
			throw new IllegalArgumentException("O orçamento não possui um pedido associado.");
		}

		try (PDDocument documento = new PDDocument();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

			// =================================================
			// PÁGINA
			// =================================================

			PDPage pagina = new PDPage(PDRectangle.A4);
			documento.addPage(pagina);

			float largura = pagina.getMediaBox().getWidth();

			float altura = pagina.getMediaBox().getHeight();

			float margem = 48;

			// =================================================
			// CONTEÚDO
			// =================================================

			try (PDPageContentStream content = new PDPageContentStream(documento, pagina)) {

				float y;

				PedidoServico pedido = orcamento.getPedido();

				// =================================================
				// CABEÇALHO
				// =================================================

				desenharRectangulo(content, 0, altura - 112, largura, 112, AZUL_R, AZUL_G, AZUL_B);

				escreverTextoCor(content, "RUI LUIS", margem, altura - 55, 20, FONTE_NEGRITO, BRANCO_R, BRANCO_G,
						BRANCO_B);

				escreverTextoCor(content, "SOLUCOES DIGITAIS", margem, altura - 76, 10, FONTE_NORMAL, BRANCO_R,
						BRANCO_G, BRANCO_B);

				escreverTextoCor(content, "Tecnologia | Gestao | Solucoes Digitais", margem, altura - 94, 8,
						FONTE_NORMAL, 0.82f, 0.86f, 0.92f);

				// =================================================
				// TÍTULO À DIREITA
				// =================================================

				escreverTextoCor(content, "ORCAMENTO", largura - margem - 145, altura - 58, 19, FONTE_NEGRITO, BRANCO_R,
						BRANCO_G, BRANCO_B);

				escreverTextoCor(content, valorSeguro(orcamento.getNumero()), largura - margem - 145, altura - 80, 9,
						FONTE_NORMAL, 0.82f, 0.86f, 0.92f);

				// =================================================
				// ÁREA PRINCIPAL
				// =================================================

				y = altura - 145;

				// =================================================
				// INFORMAÇÕES DO ORÇAMENTO
				// =================================================

				desenharRectangulo(content, margem, y - 62, largura - (margem * 2), 62, CINZA_CLARO_R, CINZA_CLARO_G,
						CINZA_CLARO_B);

				escreverTextoCor(content, "DATA DE EMISSAO", margem + 15, y - 21, 7.5f, FONTE_NEGRITO, CINZA_R, CINZA_G,
						CINZA_B);

				escreverTexto(content, formatarData(orcamento.getDataEmissao()), margem + 15, y - 39, 10, FONTE_NORMAL);

				escreverTextoCor(content, "VALIDADE", margem + 145, y - 21, 7.5f, FONTE_NEGRITO, CINZA_R, CINZA_G,
						CINZA_B);

				escreverTexto(content, formatarData(orcamento.getValidade()), margem + 145, y - 39, 10, FONTE_NORMAL);

				escreverTextoCor(content, "ESTADO", margem + 290, y - 21, 7.5f, FONTE_NEGRITO, CINZA_R, CINZA_G,
						CINZA_B);

				escreverTexto(content, formatarEstado(orcamento.getEstado()), margem + 290, y - 39, 10, FONTE_NEGRITO);

				// =================================================
				// CLIENTE
				// =================================================

				y -= 92;

				escreverTextoCor(content, "CLIENTE", margem, y, 12, FONTE_NEGRITO, AZUL_R, AZUL_G, AZUL_B);

				desenharLinha(content, margem, y - 8, largura - margem);

				y -= 28;

				escreverTexto(content, "Nome", margem, y, 8, FONTE_NEGRITO);

				escreverTexto(content, valorSeguro(pedido.getNome()), margem + 55, y, 10, FONTE_NORMAL);

				y -= 19;

				escreverTexto(content, "E-mail", margem, y, 8, FONTE_NEGRITO);

				escreverTexto(content, valorSeguro(pedido.getEmail()), margem + 55, y, 10, FONTE_NORMAL);

				if (pedido.getTelefone() != null && !pedido.getTelefone().trim().isEmpty()) {

					y -= 19;

					escreverTexto(content, "Telefone", margem, y, 8, FONTE_NEGRITO);

					escreverTexto(content, pedido.getTelefone().trim(), margem + 55, y, 10, FONTE_NORMAL);
				}

				// =================================================
				// SERVIÇO
				// =================================================

				y -= 38;

				escreverTextoCor(content, "SERVICO", margem, y, 12, FONTE_NEGRITO, AZUL_R, AZUL_G, AZUL_B);

				desenharLinha(content, margem, y - 8, largura - margem);

				y -= 27;

				escreverTexto(content, valorSeguro(pedido.getServico()), margem, y, 10, FONTE_NEGRITO);

				// =================================================
				// DESCRIÇÃO
				// =================================================

				y -= 38;

				escreverTextoCor(content, "DESCRICAO DO ORCAMENTO", margem, y, 12, FONTE_NEGRITO, AZUL_R, AZUL_G,
						AZUL_B);

				desenharLinha(content, margem, y - 8, largura - margem);

				y -= 27;

				y = escreverTextoMultilinha(content, valorSeguro(orcamento.getDescricao()), margem, y,
						largura - (margem * 2), 10, 15, FONTE_NORMAL);

				// =================================================
				// VALOR TOTAL
				// =================================================

				y -= 28;

				float caixaAltura = 72;

				desenharRectangulo(content, margem, y - caixaAltura, largura - (margem * 2), caixaAltura, AZUL_R,
						AZUL_G, AZUL_B);

				escreverTextoCor(content, "VALOR TOTAL", margem + 18, y - 27, 9, FONTE_NEGRITO, 0.78f, 0.84f, 0.92f);

				escreverTextoCor(content, "TOTAL A PAGAR", margem + 18, y - 47, 8, FONTE_NORMAL, 0.68f, 0.76f, 0.87f);

				String valorTotal = formatarMoeda(orcamento.getValor()) + " MT";

				escreverTextoCor(content, valorTotal, largura - margem - 165, y - 39, 18, FONTE_NEGRITO, BRANCO_R,
						BRANCO_G, BRANCO_B);

				y -= caixaAltura + 30;

				// =================================================
				// OBSERVAÇÕES
				// =================================================

				if (orcamento.getObservacoes() != null && !orcamento.getObservacoes().trim().isEmpty()) {

					escreverTextoCor(content, "OBSERVACOES", margem, y, 11, FONTE_NEGRITO, AZUL_R, AZUL_G, AZUL_B);

					desenharLinha(content, margem, y - 8, largura - margem);

					y -= 26;

					y = escreverTextoMultilinha(content, orcamento.getObservacoes(), margem, y, largura - (margem * 2),
							9, 14, FONTE_NORMAL);

					y -= 15;
				}

				// =================================================
				// CONDIÇÕES
				// =================================================

				y -= 10;

				escreverTextoCor(content, "CONDICOES DO ORCAMENTO", margem, y, 11, FONTE_NEGRITO, AZUL_R, AZUL_G,
						AZUL_B);

				desenharLinha(content, margem, y - 8, largura - margem);

				y -= 25;

				String condicoes = "Este orcamento e valido ate " + formatarData(orcamento.getValidade())
						+ ". A execucao do servico esta " + "sujeita a aceitacao do presente "
						+ "orcamento e a confirmacao das " + "condicoes acordadas entre as partes.";

				y = escreverTextoMultilinha(content, condicoes, margem, y, largura - (margem * 2), 8.5f, 13,
						FONTE_NORMAL);

				// =================================================
				// ÁREA DE ACEITAÇÃO
				// =================================================

				y -= 22;

				float assinaturaY = y;

				desenharRectanguloBorda(content, margem, assinaturaY - 70, 220, 70);

				escreverTextoCor(content, "ACEITACAO DO CLIENTE", margem + 12, assinaturaY - 19, 8, FONTE_NEGRITO,
						CINZA_R, CINZA_G, CINZA_B);

				escreverTexto(content, "Nome / Assinatura", margem + 12, assinaturaY - 51, 8, FONTE_NORMAL);

				desenharRectanguloBorda(content, largura - margem - 220, assinaturaY - 70, 220, 70);

				escreverTextoCor(content, "RUI LUIS - SOLUCOES DIGITAIS", largura - margem - 208, assinaturaY - 19, 8,
						FONTE_NEGRITO, CINZA_R, CINZA_G, CINZA_B);

				escreverTexto(content, "Responsavel", largura - margem - 208, assinaturaY - 51, 8, FONTE_NORMAL);

				// =================================================
				// RODAPÉ
				// =================================================

				float rodapeY = 30;

				desenharLinha(content, margem, rodapeY + 13, largura - margem);

				escreverTextoCor(content, "Rui Luis - Solucoes Digitais", margem, rodapeY, 7.5f, FONTE_NEGRITO, CINZA_R,
						CINZA_G, CINZA_B);

				escreverTextoCor(content, "Documento gerado pelo sistema", largura - margem - 145, rodapeY, 7.5f,
						FONTE_NORMAL, CINZA_R, CINZA_G, CINZA_B);
			}

			// =====================================================
			// GUARDAR PDF
			// =====================================================

			documento.save(outputStream);

			return outputStream.toByteArray();

		} catch (IOException e) {

			throw new RuntimeException("Erro ao gerar o PDF do orçamento.", e);
		}
	}

	// =========================================================
	// ESCREVER TEXTO NORMAL
	// =========================================================

	private void escreverTexto(PDPageContentStream content, String texto, float x, float y, float tamanho,
			PDType1Font fonte) throws IOException {

		escreverTextoCor(content, texto, x, y, tamanho, fonte, 0.10f, 0.12f, 0.16f);
	}

	// =========================================================
	// ESCREVER TEXTO COM COR
	// =========================================================

	private void escreverTextoCor(PDPageContentStream content, String texto, float x, float y, float tamanho,
			PDType1Font fonte, float r, float g, float b) throws IOException {

		if (texto == null) {
			texto = "";
		}

		content.beginText();

		content.setNonStrokingColor(r, g, b);

		content.setFont(fonte, tamanho);

		content.newLineAtOffset(x, y);

		content.showText(texto);

		content.endText();
	}

	// =========================================================
	// TEXTO MULTILINHA
	// =========================================================

	private float escreverTextoMultilinha(PDPageContentStream content, String texto, float x, float y,
			float larguraMaxima, float tamanho, float espacamento, PDType1Font fonte) throws IOException {

		if (texto == null || texto.trim().isEmpty()) {
			return y;
		}

		String[] palavras = texto.trim().split("\\s+");

		StringBuilder linha = new StringBuilder();

		for (String palavra : palavras) {

			String teste;

			if (linha.length() == 0) {
				teste = palavra;
			} else {
				teste = linha + " " + palavra;
			}

			float larguraTexto = fonte.getStringWidth(teste) / 1000 * tamanho;

			if (larguraTexto > larguraMaxima) {

				if (linha.length() > 0) {

					escreverTexto(content, linha.toString(), x, y, tamanho, fonte);

					y -= espacamento;
				}

				linha = new StringBuilder(palavra);

			} else {

				linha = new StringBuilder(teste);
			}
		}

		if (linha.length() > 0) {

			escreverTexto(content, linha.toString(), x, y, tamanho, fonte);

			y -= espacamento;
		}

		return y;
	}

	// =========================================================
	// RECTÂNGULO PREENCHIDO
	// =========================================================

	private void desenharRectangulo(PDPageContentStream content, float x, float y, float largura, float altura, float r,
			float g, float b) throws IOException {

		content.setNonStrokingColor(r, g, b);

		content.addRect(x, y, largura, altura);

		content.fill();

		content.setNonStrokingColor(0, 0, 0);
	}

	// =========================================================
	// RECTÂNGULO COM BORDA
	// =========================================================

	private void desenharRectanguloBorda(PDPageContentStream content, float x, float y, float largura, float altura)
			throws IOException {

		content.setStrokingColor(0.78f, 0.80f, 0.84f);

		content.addRect(x, y, largura, altura);

		content.stroke();

		content.setStrokingColor(0, 0, 0);
	}

	// =========================================================
	// LINHA
	// =========================================================

	private void desenharLinha(PDPageContentStream content, float x1, float y, float x2) throws IOException {

		content.setStrokingColor(0.82f, 0.84f, 0.87f);

		content.moveTo(x1, y);

		content.lineTo(x2, y);

		content.stroke();

		content.setStrokingColor(0, 0, 0);
	}

	// =========================================================
	// FORMATAR DATA
	// =========================================================

	private String formatarData(LocalDate data) {

		if (data == null) {
			return "-";
		}

		return data.format(FORMATO_DATA);
	}

	// =========================================================
	// FORMATAR MOEDA
	// =========================================================

	private String formatarMoeda(BigDecimal valor) {

		if (valor == null) {
			return "0,00";
		}

		return FORMATO_MOEDA.format(valor);
	}

	// =========================================================
	// FORMATAR ESTADO
	// =========================================================

	private String formatarEstado(Enum<?> estado) {

		if (estado == null) {
			return "RASCUNHO";
		}

		return estado.name().replace("_", " ");
	}

	// =========================================================
	// VALOR SEGURO
	// =========================================================

	private String valorSeguro(String valor) {

		if (valor == null || valor.trim().isEmpty()) {
			return "-";
		}

		return removerAcentos(valor.trim());
	}

	// =========================================================
	// REMOVER ACENTOS
	// =========================================================

	private String removerAcentos(String texto) {

		return java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD).replaceAll("\\p{M}", "");
	}
}