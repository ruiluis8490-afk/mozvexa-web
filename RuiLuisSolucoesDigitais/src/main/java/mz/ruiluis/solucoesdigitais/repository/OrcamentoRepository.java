package mz.ruiluis.solucoesdigitais.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mz.ruiluis.solucoesdigitais.model.EstadoOrcamento;
import mz.ruiluis.solucoesdigitais.model.Orcamento;

public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {

	List<Orcamento> findAllByOrderByDataCriacaoDesc();

	List<Orcamento> findByEstadoOrderByDataCriacaoDesc(EstadoOrcamento estado);

	List<Orcamento> findByPedidoIdOrderByDataCriacaoDesc(Long pedidoId);

	Optional<Orcamento> findTopByOrderByIdDesc();

	long countByEstado(EstadoOrcamento estado);
}