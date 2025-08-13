package com.farmacia.model;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemVenda {
    private Long id;
    private Medicamento medicamento;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemVenda() {}

    public ItemVenda(Long id, Medicamento medicamento, int quantidade, BigDecimal precoUnitario) {
        this.id = id;
        this.medicamento = medicamento;
        setQuantidade(quantidade);
        setPrecoUnitario(precoUnitario);
    }

    public BigDecimal getSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Medicamento getMedicamento() { return medicamento; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser > 0");
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) {
        if (precoUnitario == null || precoUnitario.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Preço unitário inválido");
        this.precoUnitario = precoUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemVenda)) return false;
        ItemVenda that = (ItemVenda) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "ItemVenda{id=" + id +
                ", medicamento=" + (medicamento != null ? medicamento.getNome() : "null") +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario + '}';
    }
}

