package mz.ruiluis.solucoesdigitais.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mz.ruiluis.solucoesdigitais.model.EstadoOrcamento;
import mz.ruiluis.solucoesdigitais.model.Orcamento;
import mz.ruiluis.solucoesdigitais.service.OrcamentoPdfService;
import mz.ruiluis.solucoesdigitais.service.OrcamentoService;

@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

    private final OrcamentoService service;
    private final OrcamentoPdfService orcamentoPdfService;

    public OrcamentoController(
            OrcamentoService service,
            OrcamentoPdfService orcamentoPdfService) {

        this.service = service;
        this.orcamentoPdfService = orcamentoPdfService;
    }

    // =========================================================
    // CRIAR ORÇAMENTO
    // =========================================================

    @PostMapping
    public ResponseEntity<?> criarOrcamento(
            @RequestParam Long pedidoId,
            @RequestParam BigDecimal valor,
            @RequestParam String validade,
            @RequestParam String descricao,
            @RequestParam(required = false) String observacoes
    ) {

        try {

            Orcamento orcamento =
                    service.criarOrcamento(
                            pedidoId,
                            valor,
                            LocalDate.parse(validade),
                            descricao,
                            observacoes
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(orcamento);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            "Não foi possível criar o orçamento."
                    );
        }
    }


    // =========================================================
    // LISTAR ORÇAMENTOS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Orcamento>> listarOrcamentos() {

        return ResponseEntity.ok(
                service.listarOrcamentos()
        );
    }


    // =========================================================
    // PROCURAR POR ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> procurarPorId(
            @PathVariable Long id) {

        try {

            return ResponseEntity.ok(
                    service.procurarPorId(id)
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // PROCURAR POR PEDIDO
    // =========================================================

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<Orcamento>> procurarPorPedido(
            @PathVariable Long pedidoId) {

        return ResponseEntity.ok(
                service.procurarPorPedido(pedidoId)
        );
    }


    // =========================================================
    // EDITAR ORÇAMENTO
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> editarOrcamento(
            @PathVariable Long id,
            @RequestParam BigDecimal valor,
            @RequestParam String validade,
            @RequestParam String descricao,
            @RequestParam(required = false) String observacoes
    ) {

        try {

            Orcamento orcamento =
                    service.editarOrcamento(
                            id,
                            valor,
                            LocalDate.parse(validade),
                            descricao,
                            observacoes
                    );

            return ResponseEntity.ok(orcamento);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            "Não foi possível editar o orçamento."
                    );
        }
    }


    // =========================================================
    // ALTERAR ESTADO
    // =========================================================

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> alterarEstado(
            @PathVariable Long id,
            @RequestParam EstadoOrcamento estado) {

        try {

            Orcamento orcamento =
                    service.alterarEstado(
                            id,
                            estado
                    );

            return ResponseEntity.ok(
                    orcamento
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // GERAR PDF
    // =========================================================

    @GetMapping("/{id}/pdf")
    public ResponseEntity<?> gerarPdf(
            @PathVariable Long id) {

        try {

            Orcamento orcamento =
                    service.procurarPorId(id);

            byte[] pdf =
                    orcamentoPdfService
                            .gerarPdf(orcamento);

            String numero =
                    orcamento.getNumero();

            if (numero == null ||
                    numero.isBlank()) {

                numero =
                        "orcamento-" + id;
            }

            return ResponseEntity
                    .ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" +
                                    numero +
                                    ".pdf\""
                    )
                    .contentType(
                            MediaType.APPLICATION_PDF
                    )
                    .body(pdf);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .status(
                            HttpStatus.INTERNAL_SERVER_ERROR
                    )
                    .body(
                            "Não foi possível gerar o PDF do orçamento."
                    );
        }
    }


    // =========================================================
    // ELIMINAR
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @PathVariable Long id) {

        try {

            service.eliminarOrcamento(id);

            return ResponseEntity.ok(
                    "Orçamento eliminado com sucesso."
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}