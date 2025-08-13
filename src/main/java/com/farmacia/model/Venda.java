package com.farmacia.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class Venda {
    private Long id;
    private LocalDateTime dataHora;
    private Cliente cliente;
    private final List<ItemVenda> itens = new ArrayList<>();
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private boolean finalizada = false;

    public Venda() {
        this.dataHora = LocalDateTime.now();
    }

    public Venda(Long id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.dataHora = LocalDateTime.now();
    }

    // --------- Regras ---------
    public void adicionarItem(ItemVenda item) {
        if (finalizada) throw new IllegalStateException("Venda já finalizada");
        if (item == null) throw new IllegalArgumentException("Item não pode ser nulo");
        itens.add(item);
        recalcularTotal();
    }

    public void adicionarItem(Medicamento medicamento, int quantidade, BigDecimal precoUnitario) {
        adicionarItem(new ItemVenda(null, medicamento, quantidade, precoUnitario));
    }

    public void removerItemPorMedicamentoId(Long medicamentoId) {
        if (finalizada) throw new IllegalStateException("Venda já finalizada");
        if (medicamentoId == null) return;
        Iterator<ItemVenda> it = itens.iterator();
        while (it.hasNext()) {
            ItemVenda iv = it.next();
            if (iv.getMedicamento() != null && medicamentoId.equals(iv.getMedicamento().getId())) {
                it.remove();
            }
        }
        recalcularTotal();
    }

    public void finalizar() {
        if (itens.isEmpty()) throw new IllegalStateException("Não é possível finalizar venda sem itens");
        this.finalizada = true;
        this.dataHora = LocalDateTime.now();
        recalcularTotal();
    }

    private void recalcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemVenda iv : itens) {
            total = total.add(iv.getSubtotal());
        }
        this.valorTotal = total;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<ItemVenda> getItens() { return new ArrayList<>(itens); } // cópia defensiva

    public BigDecimal getValorTotal() { return valorTotal; }

    public boolean isFinalizada() { return finalizada; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venda)) return false;
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Venda{id=" + id +
                ", dataHora=" + dataHora +
                ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
                ", itens=" + itens.size() +
                ", valorTotal=" + valorTotal +
                ", finalizada=" + finalizada + '}';
    }
}

