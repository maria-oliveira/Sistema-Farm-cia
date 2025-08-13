package com.farmacia.model;

public class ItemVenda {
    private int id;
    private int vendaId;
    private int medicamentoId;
    private int quantidade;
    private double precoUnitario;

    public ItemVenda() {
    }

    public ItemVenda(int id, int vendaId, int medicamentoId, int quantidade, double precoUnitario) {
        this.id = id;
        this.vendaId = vendaId;
        this.medicamentoId = medicamentoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public ItemVenda(int vendaId, int medicamentoId, int quantidade, double precoUnitario) {
        this.vendaId = vendaId;
        this.medicamentoId = medicamentoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVendaId() {
        return vendaId;
    }

    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
    }

    public int getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(int medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
