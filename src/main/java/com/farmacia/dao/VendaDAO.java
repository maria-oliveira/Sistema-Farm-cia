package com.farmacia.dao;

import com.farmacia.model.Venda;
import com.farmacia.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class VendaDAO {

    public void registrarVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (id_cliente, id_medicamento, quantidade, data) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, venda.getIdCliente());
            stmt.setInt(2, venda.getIdMedicamento());
            stmt.setInt(3, venda.getQuantidade());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
        }
    }
}
