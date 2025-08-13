package com.farmacia.model;

import java.util.Objects;

public class Cliente {
    private Long id;
    private String nome;
    private String cpf;       // formato simples (somente dígitos ou com máscara)
    private String telefone;
    private String email;

    public Cliente() {}

    public Cliente(Long id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public boolean cpfFormatoValido() {
        if (cpf == null) return false;
        String apenasDigitos = cpf.replaceAll("\\D", "");
        return apenasDigitos.length() == 11;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' + ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' + '}';
    }
}
