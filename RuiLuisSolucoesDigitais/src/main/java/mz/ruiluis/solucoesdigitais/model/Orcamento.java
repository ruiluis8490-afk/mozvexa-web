package mz.ruiluis.solucoesdigitais.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "orcamentos")
public class Orcamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 30)
	private String numero;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pedido_id", nullable = false)
	private PedidoServico pedido;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal valor;

	@Column(nullable = false)
	private LocalDate dataEmissao;

	@Column(nullable = false)
	private LocalDate validade;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String descricao;

	@Column(columnDefinition = "TEXT")
	private String observacoes;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private EstadoOrcamento estado;

	@Column(nullable = false)
	private LocalDateTime dataCriacao;

	@Column(nullable = false)
	private LocalDateTime dataActualizacao;

	public Orcamento() {
	}

	@PrePersist
	public void prepararOrcamento() {

		if (dataEmissao == null) {

			dataEmissao = LocalDate.now();
		}

		if (dataCriacao == null) {

			dataCriacao = LocalDateTime.now();
		}

		if (dataActualizacao == null) {

			dataActualizacao = LocalDateTime.now();
		}

		if (estado == null) {

			estado = EstadoOrcamento.RASCUNHO;
		}
	}

	@PreUpdate
	public void actualizarData() {

		dataActualizacao = LocalDateTime.now();
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getNumero() {

		return numero;
	}

	public void setNumero(String numero) {

		this.numero = numero;
	}

	public PedidoServico getPedido() {

		return pedido;
	}

	public void setPedido(PedidoServico pedido) {

		this.pedido = pedido;
	}

	public BigDecimal getValor() {

		return valor;
	}

	public void setValor(BigDecimal valor) {

		this.valor = valor;
	}

	public LocalDate getDataEmissao() {

		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {

		this.dataEmissao = dataEmissao;
	}

	public LocalDate getValidade() {

		return validade;
	}

	public void setValidade(LocalDate validade) {

		this.validade = validade;
	}

	public String getDescricao() {

		return descricao;
	}

	public void setDescricao(String descricao) {

		this.descricao = descricao;
	}

	public String getObservacoes() {

		return observacoes;
	}

	public void setObservacoes(String observacoes) {

		this.observacoes = observacoes;
	}

	public EstadoOrcamento getEstado() {

		return estado;
	}

	public void setEstado(EstadoOrcamento estado) {

		this.estado = estado;
	}

	public LocalDateTime getDataCriacao() {

		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {

		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataActualizacao() {

		return dataActualizacao;
	}

	public void setDataActualizacao(LocalDateTime dataActualizacao) {

		this.dataActualizacao = dataActualizacao;
	}
}