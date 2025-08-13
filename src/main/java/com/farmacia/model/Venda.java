package com.farmacia.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private Integer id;
    private Integer clienteId;
    private LocalDateTime dataVenda;
    private double total;
    private List<ItemVenda> itens = new ArrayList<>();

    public Venda() {}

    public Venda(Integer id, Integer clienteId, LocalDateTime dataVenda, double total) {
        this.id = id;
        this.clienteId = clienteId;
        this.dataVenda = dataVenda;
        this.total = total;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }
    public LocalDateTime getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDateTime dataVenda) { this.dataVenda = dataVenda; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
        total += item.getSubtotal();
    }
}
