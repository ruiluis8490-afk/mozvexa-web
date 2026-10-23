package mz.ruiluis.solucoesdigitais.dto;

public class PedidoDTO {

    private String nome;
    private String telefone;
    private String email;
    private String empresa;
    private String servico;
    private String titulo;
    private String descricao;
    private String orcamento;
    private String prazo;
    private String observacoes;
    private Boolean aceitar;

    public PedidoDTO() {
    }

    // =========================================================
    // GETTERS E SETTERS
    // =========================================================

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public String getServico() { return servico; }
    public void setServico(String servico) { this.servico = servico; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getOrcamento() { return orcamento; }
    public void setOrcamento(String orcamento) { this.orcamento = orcamento; }

    public String getPrazo() { return prazo; }
    public void setPrazo(String prazo) { this.prazo = prazo; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Boolean getAceitar() { return aceitar; }
    public void setAceitar(Boolean aceitar) { this.aceitar = aceitar; }
}