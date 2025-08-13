package com.farmacia.dao;

import com.farmacia.model.ItemVenda;
import com.farmacia.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaDAO {

    public void salvar(ItemVenda item) throws SQLException {
        String sql = "INSERT INTO itens_venda (id_venda, id_medicamento, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getIdVenda());
            stmt.setInt(2, item.getIdMedicamento());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnitario());
            stmt.executeUpdate();
        }
    }

    public List<ItemVenda> listarPorVenda(int idVenda) throws SQLException {
        List<ItemVenda> itens = new ArrayList<>();
        String sql = "SELECT * FROM itens_venda WHERE id_venda = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVenda);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItemVenda item = new ItemVenda(
                        rs.getInt("id"),
                        rs.getInt("id_venda"),
                        rs.getInt("id_medicamento"),
                        rs.getInt("quantidade"),
                        rs.getDouble("preco_unitario")
                );
                itens.add(item);
            }
        }
        return itens;
    }

    public void deletarPorVenda(int idVenda) throws SQLException {
        String sql = "DELETE FROM itens_venda WHERE id_venda = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVenda);
            stmt.executeUpdate();
        }
    }
}

