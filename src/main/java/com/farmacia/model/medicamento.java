package com.farmacia.model;

import java.time.LocalDate;
import java.util.Objects;

public class medicamento {
    private Integer id;
    private String nome;
    private String fabricante;
    private double preco;
    private int quantidadeEstoque;
    private LocalDate dataValidade;
    private boolean controlado;

    public medicamento() {}

    public medicamento(Integer id, String nome, String fabricante, double preco, int quantidadeEstoque, LocalDate dataValidade, boolean controlado) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.dataValidade = dataValidade;
        this.controlado = controlado;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }
    public boolean isControlado() { return controlado; }
    public void setControlado(boolean controlado) { this.controlado = controlado; }

    public boolean estaVencido() { return dataValidade != null && dataValidade.isBefore(LocalDate.now()); }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", preco=" + preco +
                ", estoque=" + quantidadeEstoque +
                ", validade=" + dataValidade +
                ", controlado=" + controlado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        medicamento that = (medicamento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
