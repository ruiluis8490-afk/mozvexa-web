package mz.ruiluis.solucoesdigitais.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mz.ruiluis.solucoesdigitais.model.EstadoOrcamento;
import mz.ruiluis.solucoesdigitais.model.EstadoPedido;
import mz.ruiluis.solucoesdigitais.model.Orcamento;
import mz.ruiluis.solucoesdigitais.model.PedidoServico;
import mz.ruiluis.solucoesdigitais.repository.OrcamentoRepository;
import mz.ruiluis.solucoesdigitais.repository.PedidoServicoRepository;

@Service
public class OrcamentoService {

	private final OrcamentoRepository orcamentoRepository;
	private final PedidoServicoRepository pedidoRepository;

	public OrcamentoService(OrcamentoRepository orcamentoRepository, PedidoServicoRepository pedidoRepository) {

		this.orcamentoRepository = orcamentoRepository;
		this.pedidoRepository = pedidoRepository;
	}

	// =========================================================
	// CRIAR ORÇAMENTO
	// =========================================================

	@Transactional
	public Orcamento criarOrcamento(Long pedidoId, BigDecimal valor, LocalDate validade, String descricao,
			String observacoes) {

		if (pedidoId == null) {
			throw new IllegalArgumentException("O pedido é obrigatório.");
		}

		validarDadosOrcamento(valor, validade, descricao);

		PedidoServico pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));

		Orcamento orcamento = new Orcamento();

		orcamento.setPedido(pedido);
		orcamento.setValor(valor);
		orcamento.setValidade(validade);
		orcamento.setDescricao(descricao.trim());

		if (observacoes != null && !observacoes.trim().isEmpty()) {

			orcamento.setObservacoes(observacoes.trim());
		}

		orcamento.setEstado(EstadoOrcamento.RASCUNHO);

		String numero = gerarNumeroOrcamento();

		orcamento.setNumero(numero);

		return orcamentoRepository.save(orcamento);
	}

	// =========================================================
	// EDITAR ORÇAMENTO
	// =========================================================

	@Transactional
	public Orcamento editarOrcamento(Long id, BigDecimal valor, LocalDate validade, String descricao,
			String observacoes) {

		validarDadosOrcamento(valor, validade, descricao);

		Orcamento orcamento = procurarPorId(id);

		// -----------------------------------------------------
		// VERIFICAR SE O ORÇAMENTO PODE SER EDITADO
		// -----------------------------------------------------

		EstadoOrcamento estado = orcamento.getEstado();

		if (estado == EstadoOrcamento.ACEITE) {

			throw new IllegalArgumentException("Não é possível editar um orçamento que já foi aceite.");
		}

		if (estado == EstadoOrcamento.RECUSADO) {

			throw new IllegalArgumentException("Não é possível editar um orçamento que foi recusado.");
		}

		if (estado == EstadoOrcamento.CANCELADO) {

			throw new IllegalArgumentException("Não é possível editar um orçamento cancelado.");
		}

		if (estado == EstadoOrcamento.EXPIRADO) {

			throw new IllegalArgumentException("Não é possível editar um orçamento expirado.");
		}

		// -----------------------------------------------------
		// ACTUALIZAR DADOS
		// -----------------------------------------------------

		orcamento.setValor(valor);

		orcamento.setValidade(validade);

		orcamento.setDescricao(descricao.trim());

		if (observacoes != null && !observacoes.trim().isEmpty()) {

			orcamento.setObservacoes(observacoes.trim());

		} else {

			orcamento.setObservacoes(null);
		}

		// -----------------------------------------------------
		// O @PreUpdate DA ENTIDADE ACTUALIZA
		// dataActualizacao AUTOMATICAMENTE
		// -----------------------------------------------------

		return orcamentoRepository.save(orcamento);
	}

	// =========================================================
	// VALIDAÇÃO
	// =========================================================

	private void validarDadosOrcamento(BigDecimal valor, LocalDate validade, String descricao) {

		if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {

			throw new IllegalArgumentException("O valor do orçamento deve ser superior a zero.");
		}

		if (validade == null) {

			throw new IllegalArgumentException("A data de validade é obrigatória.");
		}

		if (validade.isBefore(LocalDate.now())) {

			throw new IllegalArgumentException("A validade do orçamento não pode ser uma data passada.");
		}

		if (descricao == null || descricao.trim().isEmpty()) {

			throw new IllegalArgumentException("A descrição do orçamento é obrigatória.");
		}
	}

	// =========================================================
	// GERAR NÚMERO
	// =========================================================

	private String gerarNumeroOrcamento() {

		LocalDate agora = LocalDate.now();

		long timestamp = System.currentTimeMillis() % 10000000000L;

		return String.format("ORC-%d-%010d", agora.getYear(), timestamp);
	}

	// =========================================================
	// LISTAR
	// =========================================================

	public List<Orcamento> listarOrcamentos() {

		return orcamentoRepository.findAllByOrderByDataCriacaoDesc();
	}

	// =========================================================
	// PROCURAR POR ID
	// =========================================================

	public Orcamento procurarPorId(Long id) {

		return orcamentoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Orçamento não encontrado."));
	}

	// =========================================================
	// PROCURAR POR PEDIDO
	// =========================================================

	public List<Orcamento> procurarPorPedido(Long pedidoId) {

		return orcamentoRepository.findByPedidoIdOrderByDataCriacaoDesc(pedidoId);
	}

	// =========================================================
	// ALTERAR ESTADO
	// =========================================================

	@Transactional
	public Orcamento alterarEstado(Long id, EstadoOrcamento novoEstado) {

		if (novoEstado == null) {

			throw new IllegalArgumentException("O estado é obrigatório.");
		}

		Orcamento orcamento = procurarPorId(id);

		orcamento.setEstado(novoEstado);

		PedidoServico pedido = orcamento.getPedido();

		if (novoEstado == EstadoOrcamento.ENVIADO) {

			pedido.setEstado(EstadoPedido.ORCAMENTO_ENVIADO);

			pedidoRepository.save(pedido);
		}

		if (novoEstado == EstadoOrcamento.ACEITE) {

			pedido.setEstado(EstadoPedido.APROVADA);

			pedidoRepository.save(pedido);
		}

		return orcamentoRepository.save(orcamento);
	}

	// =========================================================
	// ELIMINAR
	// =========================================================

	@Transactional
	public void eliminarOrcamento(Long id) {

		Orcamento orcamento = procurarPorId(id);

		orcamentoRepository.delete(orcamento);
	}

	// =========================================================
	// CONTADORES
	// =========================================================

	public long contarOrcamentos() {

		return orcamentoRepository.count();
	}

	public long contarPorEstado(EstadoOrcamento estado) {

		return orcamentoRepository.countByEstado(estado);
	}
}