package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ContaCorrente;
import util.Conexao;

public class ContaDAO {

    // Inserir nova conta
    public void inserir(ContaCorrente conta) throws SQLException {
        String sql = "INSERT INTO contas (numero, titular, saldo) VALUES (?, ?, ?)";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, conta.getNumero());
            ps.setString(2, conta.getTitular());
            ps.setDouble(3, conta.getSaldo());
            ps.executeUpdate();
        }
    }

    // Listar todas as contas
    public List<ContaCorrente> listar() throws SQLException {
        List<ContaCorrente> contas = new ArrayList<>();
        String sql = "SELECT numero, titular, saldo FROM contas";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int numero = rs.getInt("numero");
                String titular = rs.getString("titular");
                double saldo = rs.getDouble("saldo");
                contas.add(new ContaCorrente(numero, titular, saldo));
            }
        }
        return contas;
    }

    // Buscar conta por número
    public ContaCorrente buscarPorNumero(int numero) throws SQLException {
        String sql = "SELECT numero, titular, saldo FROM contas WHERE numero = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ContaCorrente(
                        rs.getInt("numero"),
                        rs.getString("titular"),
                        rs.getDouble("saldo")
                    );
                }
            }
        }
        return null;
    }

    // Atualizar saldo
    public void atualizarSaldo(int numero, double novoSaldo) throws SQLException {
        String sql = "UPDATE contas SET saldo = ? WHERE numero = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, novoSaldo);
            ps.setInt(2, numero);
            ps.executeUpdate();
        }
    }

    // Remover conta
    public void remover(int numero) throws SQLException {
        String sql = "DELETE FROM contas WHERE numero = ?";
        try (Connection con = Conexao.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numero);
            ps.executeUpdate();
        }
    }
}
