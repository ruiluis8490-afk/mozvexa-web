package mz.ruiluis.solucoesdigitais.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mz.ruiluis.solucoesdigitais.model.EstadoPedido;
import mz.ruiluis.solucoesdigitais.model.PedidoServico;
import mz.ruiluis.solucoesdigitais.repository.PedidoServicoRepository;

@Service
public class PedidoServicoService {

	private final PedidoServicoRepository repository;

	public PedidoServicoService(PedidoServicoRepository repository) {

		this.repository = repository;
	}

	/*
	 * ========================================================= CRIAR PEDIDO
	 * =========================================================
	 */

	public PedidoServico criarPedido(PedidoServico pedido) {

		if (pedido.getNome() == null || pedido.getNome().trim().isEmpty()) {

			throw new IllegalArgumentException("O nome é obrigatório.");
		}

		if (pedido.getEmail() == null || pedido.getEmail().trim().isEmpty()) {

			throw new IllegalArgumentException("O email é obrigatório.");
		}

		if (pedido.getServico() == null || pedido.getServico().trim().isEmpty()) {

			throw new IllegalArgumentException("O serviço é obrigatório.");
		}

		if (pedido.getMensagem() == null || pedido.getMensagem().trim().isEmpty()) {

			throw new IllegalArgumentException("A mensagem é obrigatória.");
		}

		pedido.setEstado(EstadoPedido.NOVA);

		return repository.save(pedido);
	}

	/*
	 * ========================================================= LISTAR TODOS OS
	 * PEDIDOS =========================================================
	 */

	public List<PedidoServico> listarPedidos() {

		return repository.findAllByOrderByDataPedidoDesc();
	}

	/*
	 * ========================================================= PROCURAR PEDIDO POR
	 * ID =========================================================
	 */

	public PedidoServico procurarPorId(Long id) {

		return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado."));
	}

	/*
	 * ========================================================= ALTERAR ESTADO
	 * =========================================================
	 */

	public PedidoServico alterarEstado(Long id, EstadoPedido novoEstado) {

		PedidoServico pedido = procurarPorId(id);

		pedido.setEstado(novoEstado);

		return repository.save(pedido);
	}

	/*
	 * ========================================================= ELIMINAR PEDIDO
	 * =========================================================
	 */

	public void eliminarPedido(Long id) {

		PedidoServico pedido = procurarPorId(id);

		repository.delete(pedido);
	}

	/*
	 * ========================================================= ESTATÍSTICAS DO
	 * DASHBOARD =========================================================
	 */

	public long contarPedidos() {

		return repository.count();
	}

	public long contarPorEstado(EstadoPedido estado) {

		return repository.findByEstadoOrderByDataPedidoDesc(estado).size();
	}

}
