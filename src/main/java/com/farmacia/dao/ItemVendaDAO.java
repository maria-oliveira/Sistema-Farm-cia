package com.farmacia.dao;


import com.farmacia.infrastructure.ConnectionFactory;
import com.farmacia.model.ItemVenda;
import com.farmacia.model.Medicamento;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaDAO {

    public List<ItemVenda> listarPorVenda(Long vendaId) {
        final String sql = "SELECT iv.id, iv.quantidade, iv.preco_unitario, m.id AS med_id, m.nome " +
                "FROM itens_venda iv JOIN medicamentos m ON m.id = iv.medicamento_id " +
                "WHERE iv.venda_id=? ORDER BY iv.id";
        List<ItemVenda> itens = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, vendaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ItemVenda iv = new ItemVenda();
                    iv.setId(rs.getLong("id"));
                    iv.setQuantidade(rs.getInt("quantidade"));
                    BigDecimal pu = rs.getBigDecimal("preco_unitario");
                    iv.setPrecoUnitario(pu);

                    Medicamento m = new Medicamento();
                    m.setId(rs.getLong("med_id"));
                    m.setNome(rs.getString("nome"));
                    iv.setMedicamento(m);

                    itens.add(iv);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens da venda: " + e.getMessage(), e);
        }
        return itens;
    }
}


