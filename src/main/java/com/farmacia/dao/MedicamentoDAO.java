package com.farmacia.dao;
import com.farmacia.infrastructure.ConnectionFactory;
import com.farmacia.model.Medicamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class MedicamentoDAO {

    public Medicamento criar(Medicamento m) {
        final String sql = "INSERT INTO medicamentos (nome, quantidade, validade, preco) VALUES (?, ?, ?, ?)";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNome());
            ps.setInt(2, m.getQuantidade());
            if (m.getValidade() != null) {
                ps.setDate(3, Date.valueOf(m.getValidade()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setBigDecimal(4, m.getPreco());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getLong(1));
            }
            return m;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar medicamento: " + e.getMessage(), e);
        }
    }

    public void atualizar(Medicamento m) {
        final String sql = "UPDATE medicamentos SET nome=?, quantidade=?, validade=?, preco=? WHERE id=?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setInt(2, m.getQuantidade());
            if (m.getValidade() != null) {
                ps.setDate(3, Date.valueOf(m.getValidade()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setBigDecimal(4, m.getPreco());
            ps.setLong(5, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar medicamento: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        final String sql = "DELETE FROM medicamentos WHERE id=?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar medicamento: " + e.getMessage(), e);
        }
    }

    public Medicamento buscarPorId(Long id) {
        final String sql = "SELECT id, nome, quantidade, validade, preco FROM medicamentos WHERE id=?";
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar medicamento: " + e.getMessage(), e);
        }
    }

    public List<Medicamento> listarTodos() {
        final String sql = "SELECT id, nome, quantidade, validade, preco FROM medicamentos ORDER BY nome";
        List<Medicamento> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar medicamentos: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Medicamento> listarVencendoEmAteDias(int dias) {
        final String sql =
                "SELECT id, nome, quantidade, validade, preco " +
                        "FROM medicamentos " +
                        "WHERE validade IS NOT NULL AND validade <= DATE_ADD(CURDATE(), INTERVAL ? DAY) " +
                        "ORDER BY validade ASC";
        List<Medicamento> lista = new ArrayList<>();
        try (Connection c = ConnectionFactory.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, dias);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vencendo: " + e.getMessage(), e);
        }
        return lista;
    }

    public void baixarEstoque(Long medicamentoId, int quantidade) {
        final String consulta = "SELECT quantidade FROM medicamentos WHERE id=? FOR UPDATE";
        final String atualiza = "UPDATE medicamentos SET quantidade = quantidade - ? WHERE id=? AND quantidade >= ?";
        try (Connection c = ConnectionFactory.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement psSel = c.prepareStatement(consulta);
                 PreparedStatement psUpd = c.prepareStatement(atualiza)) {
                psSel.setLong(1, medicamentoId);
                try (ResultSet rs = psSel.executeQuery()) {
                    if (!rs.next()) throw new IllegalArgumentException("Medicamento não encontrado");
                }
                psUpd.setInt(1, quantidade);
                psUpd.setLong(2, medicamentoId);
                psUpd.setInt(3, quantidade);
                int alteradas = psUpd.executeUpdate();
                if (alteradas == 0) throw new IllegalArgumentException("Estoque insuficiente");
                c.commit();
            } catch (Exception e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao baixar estoque: " + e.getMessage(), e);
        }
    }

    private Medicamento map(ResultSet rs) throws SQLException {
        Medicamento m = new Medicamento();
        m.setId(rs.getLong("id"));
        m.setNome(rs.getString("nome"));
        m.setQuantidade(rs.getInt("quantidade"));
        Date d = rs.getDate("validade");
        m.setValidade(d != null ? d.toLocalDate() : null);
        m.setPreco(rs.getBigDecimal("preco") != null ? rs.getBigDecimal("preco") : new BigDecimal("0.00"));
        return m;
    }
}


