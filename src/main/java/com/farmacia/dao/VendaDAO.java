package com.farmacia.dao;

import com.farmacia.infrastructure.ConnectionFactory;
import com.farmacia.model.ItemVenda;
import com.farmacia.model.Venda;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class VendaDAO {

    public Venda criarVendaComItens(Venda venda) {
        final String insVenda = "INSERT INTO vendas (data_hora, cliente_id, valor_total, finalizada) VALUES (?, ?, ?, ?)";
        final String insItem  = "INSERT INTO itens_venda (venda_id, medicamento_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        final String baixaEstoque = "UPDATE medicamentos SET quantidade = quantidade - ? WHERE id=? AND quantidade >= ?";

        try (Connection c = ConnectionFactory.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement psVenda = c.prepareStatement(insVenda, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psItem  = c.prepareStatement(insItem);
                 PreparedStatement psBaixa = c.prepareStatement(baixaEstoque)) {

                // 1) inserir venda base
                psVenda.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                if (venda.getCliente() != null) {
                    psVenda.setLong(2, venda.getCliente().getId());
                } else {
                    psVenda.setNull(2, Types.BIGINT);
                }
                psVenda.setBigDecimal(3, BigDecimal.ZERO); // calcula depois
                psVenda.setBoolean(4, false);
                psVenda.executeUpdate();

                try (ResultSet rs = psVenda.getGeneratedKeys()) {
                    if (rs.next()) venda.setId(rs.getLong(1));
                }


                BigDecimal total = BigDecimal.ZERO;
                List<ItemVenda> itens = venda.getItens();
                for (ItemVenda iv : itens) {
                    // baixa de estoque
                    psBaixa.setInt(1, iv.getQuantidade());
                    psBaixa.setLong(2, iv.getMedicamento().getId());
                    psBaixa.setInt(3, iv.getQuantidade());
                    int ok = psBaixa.executeUpdate();
                    if (ok == 0) throw new IllegalArgumentException("Estoque insuficiente para medicamento id=" + iv.getMedicamento().getId());

                    // item
                    psItem.setLong(1, venda.getId());
                    psItem.setLong(2, iv.getMedicamento().getId());
                    psItem.setInt(3, iv.getQuantidade());
                    psItem.setBigDecimal(4, iv.getPrecoUnitario());
                    psItem.executeUpdate();

                    total = total.add(iv.getPrecoUnitario().multiply(BigDecimal.valueOf(iv.getQuantidade())));
                }

                // 3) finalizar venda (atualiza total e finalizada=true)
                final String updVenda = "UPDATE vendas SET valor_total=?, finalizada=? WHERE id=?";
                try (PreparedStatement ps = c.prepareStatement(updVenda)) {
                    ps.setBigDecimal(1, total);
                    ps.setBoolean(2, true);
                    ps.setLong(3, venda.getId());
                    ps.executeUpdate();
                }

                c.commit();

                return venda;
            } catch (Exception e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar venda: " + e.getMessage(), e);
        }
    }

    public List<Venda> listarResumo() {
        final String sql = "SELECT v.id, v.data_hora, v.cliente_id, v.valor_total, v.finalizada FROM vendas v ORDER BY v.id DESC";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            java.util.ArrayList<Venda> vendas = new java.util.ArrayList<>();
            while (rs.next()) {
                Venda v = new Venda();
                v.setId(rs.getLong("id"));
                v.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                v.setCliente(null);
                vendas.add(v);
            }
            return vendas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas: " + e.getMessage(), e);
        }
    }
}
