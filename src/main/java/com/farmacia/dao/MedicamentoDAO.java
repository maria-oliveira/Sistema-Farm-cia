package com.farmacia.dao;

import com.farmacia.model.medicamento;
import com.farmacia.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {

    public void salvar(medicamento medicamento) throws SQLException {
        String sql = "INSERT INTO medicamentos (nome, quantidade, validade, preco) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medicamento.getNome());
            stmt.setInt(2, medicamento.getQuantidade());
            stmt.setDate(3, Date.valueOf(medicamento.getValidade()));
            stmt.setDouble(4, medicamento.getPreco());
            stmt.executeUpdate();
        }
    }

    public List<medicamento> listar() throws SQLException {
        List<medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                medicamento med = new medicamento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("quantidade"),
                        rs.getDate("validade").toLocalDate(),
                        rs.getDouble("preco")
                );
                medicamentos.add(med);
            }
        }
        return medicamentos;
    }

    public void atualizarQuantidade(int id, int novaQtd) throws SQLException {
        String sql = "UPDATE medicamentos SET quantidade = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novaQtd);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
}

