package mz.ruiluis.solucoesdigitais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mz.ruiluis.solucoesdigitais.model.EstadoPedido;
import mz.ruiluis.solucoesdigitais.model.PedidoServico;

public interface PedidoServicoRepository
        extends JpaRepository<PedidoServico, Long> {

    List<PedidoServico> findAllByOrderByDataPedidoDesc();

    List<PedidoServico> findByEstadoOrderByDataPedidoDesc(
            EstadoPedido estado
    );
}