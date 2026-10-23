package mz.ruiluis.solucoesdigitais.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mz.ruiluis.solucoesdigitais.dto.PedidoDTO;
import mz.ruiluis.solucoesdigitais.model.EstadoPedido;
import mz.ruiluis.solucoesdigitais.model.PedidoServico;
import mz.ruiluis.solucoesdigitais.service.PedidoServicoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoServicoController {

    private final PedidoServicoService service;

    public PedidoServicoController(PedidoServicoService service) {
        this.service = service;
    }

    // =========================================================
    // CRIAR PEDIDO (aceita PedidoDTO)
    // =========================================================

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody PedidoDTO dto) {

        try {

            PedidoServico pedido = new PedidoServico();

            pedido.setNome(dto.getNome());
            pedido.setEmail(dto.getEmail());
            pedido.setTelefone(dto.getTelefone());
            pedido.setServico(dto.getServico());

            // Constrói a mensagem final juntando todos os campos
            pedido.setMensagem(construirMensagem(dto));

            PedidoServico novoPedido = service.criarPedido(pedido);

            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // =========================================================
    // CONSTRUIR MENSAGEM A PARTIR DO DTO
    // =========================================================

    private String construirMensagem(PedidoDTO dto) {

        StringBuilder sb = new StringBuilder();

        if (dto.getTitulo() != null && !dto.getTitulo().isBlank()) {
            sb.append("TÍTULO DO PROJECTO: ").append(dto.getTitulo()).append("\n\n");
        }

        if (dto.getEmpresa() != null && !dto.getEmpresa().isBlank()) {
            sb.append("EMPRESA: ").append(dto.getEmpresa()).append("\n\n");
        }

        if (dto.getDescricao() != null && !dto.getDescricao().isBlank()) {
            sb.append("DESCRIÇÃO:\n").append(dto.getDescricao()).append("\n\n");
        }

        if (dto.getOrcamento() != null && !dto.getOrcamento().isBlank()) {
            sb.append("ORÇAMENTO ESTIMADO: ").append(dto.getOrcamento()).append("\n\n");
        }

        if (dto.getPrazo() != null && !dto.getPrazo().isBlank()) {
            sb.append("PRAZO PRETENDIDO: ").append(dto.getPrazo()).append("\n\n");
        }

        if (dto.getObservacoes() != null && !dto.getObservacoes().isBlank()) {
            sb.append("OBSERVAÇÕES:\n").append(dto.getObservacoes()).append("\n");
        }

        String resultado = sb.toString().trim();

        // Se o utilizador só veio do index.html (sem titulo/descricao),
        // aceita o campo "descricao" como mensagem principal
        if (resultado.isEmpty()) {
            resultado = "(sem descrição)";
        }

        return resultado;
    }

    // =========================================================
    // LISTAR
    // =========================================================

    @GetMapping
    public ResponseEntity<List<PedidoServico>> listarPedidos() {

        return ResponseEntity.ok(service.listarPedidos());
    }

    // =========================================================
    // PROCURAR
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> procurarPedido(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(service.procurarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // =========================================================
    // ALTERAR ESTADO
    // =========================================================

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> alterarEstado(@PathVariable Long id, @RequestParam EstadoPedido estado) {

        try {
            PedidoServico pedido = service.alterarEstado(id, estado);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // =========================================================
    // ELIMINAR
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {

        try {
            service.eliminarPedido(id);
            return ResponseEntity.ok("Pedido eliminado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}