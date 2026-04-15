package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ranking {

    private static final String URL = "jdbc:mysql://localhost:3306/SEU_BANCO";
    private static final String USER = "SEU_USUARIO"; // Ex: root
    private static final String PASSWORD = "SUA_SENHA";

    public void salvarResultado(String nomeJogador, int jogadas, boolean vitoria) {

        LocalDateTime now = LocalDateTime.now();
        String dataJogo = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String horaJogo = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        int vit = vitoria ? 1 : 0;
        int der = vitoria ? 0 : 1;

        String sql = "INSERT INTO Dados_Jogo (USUARIO, VITORIAS, DERROTAS, DATA_JOGO, HORA) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeJogador);
            pstmt.setInt(2, vit);
            pstmt.setInt(3, der);
            pstmt.setString(4, dataJogo);
            pstmt.setString(5, horaJogo);

            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("\n✅ Resultado de " + nomeJogador + " salvo no Ranking!");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar resultado no banco de dados: " + e.getMessage());
        }
    }

    public void mostrarRanking() {
        System.out.println("\n=== 🏆 RANKING GERAL ===");

        String sql = """
            SELECT
                USUARIO,
                SUM(VITORIAS) AS TotalVitorias,
                SUM(DERROTAS) AS TotalDerrotas
            FROM
                Dados_Jogo
            GROUP BY
                USUARIO
            ORDER BY
                TotalVitorias DESC, TotalDerrotas ASC;
            """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-15s | %-8s | %-8s%n", "JOGADOR", "VITÓRIAS", "DERROTAS");
            System.out.println("------------------------------------");

            int rank = 1;
            while (rs.next()) {
                String usuario = rs.getString("USUARIO");
                int vitorias = rs.getInt("TotalVitorias");
                int derrotas = rs.getInt("TotalDerrotas");

                System.out.printf("%-15s | %-8d | %-8d%n", usuario, vitorias, derrotas);
                rank++;
            }

            if (rank == 1) {
                System.out.println("Nenhum dado de jogo encontrado no ranking.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar ranking: " + e.getMessage());
            System.err.println("Verifique se o driver JDBC e a conexão estão corretos.");
        }
    }
}