package com.farmacia.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public class Medicamento {
    private Long id;
    private String nome;
    private int quantidade;          // em unidades
    private LocalDate validade;      // data de validade
    private BigDecimal preco;        // preço por unidade

    public Medicamento() {}

    public Medicamento(Long id, String nome, int quantidade, LocalDate validade, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        setQuantidade(quantidade);
        this.validade = validade;
        setPreco(preco);
    }

    public boolean isVencido() {
        return validade != null && validade.isBefore(LocalDate.now());
    }


    public long diasParaVencer() {
        if (validade == null) return Long.MAX_VALUE;
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), validade);
    }

    public void baixarEstoque(int qtd) {
        if (qtd <= 0) throw new IllegalArgumentException("Quantidade para baixa deve ser > 0");
        if (qtd > this.quantidade) throw new IllegalArgumentException("Estoque insuficiente");
        this.quantidade -= qtd;
    }


    public void reporEstoque(int qtd) {
        if (qtd <= 0) throw new IllegalArgumentException("Quantidade para repor deve ser > 0");
        this.quantidade += qtd;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) {
        if (quantidade < 0) throw new IllegalArgumentException("Quantidade não pode ser negativa");
        this.quantidade = quantidade;
    }

    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Preço não pode ser nulo ou negativo");
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicamento)) return false;
        Medicamento that = (Medicamento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Medicamento{id=" + id + ", nome='" + nome + '\'' +
                ", quantidade=" + quantidade + ", validade=" + validade +
                ", preco=" + preco + '}';
    }
}

