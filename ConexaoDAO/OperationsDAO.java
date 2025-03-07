package ConexaoDAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationsDAO {

    public static void cadastrarLivroFisico(String titulo, String autor, double precoStr, double pesoStr) {
        if (titulo == null || autor == null) {
            JOptionPane.showMessageDialog(null, "Título e autor não podem ser nulos.");
            return;
        }

        double preco = 0;
        double peso = 0;

        // Tratamento para exceção de formato inválido para preco e peso
        try {
            preco = Double.parseDouble(String.valueOf(precoStr));
            if (preco < 0) {
                JOptionPane.showMessageDialog(null, "Preço não pode ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido para o preço.");
            return;
        }

        try {
            peso = Double.parseDouble(String.valueOf(pesoStr));
            if (peso < 0) {
                JOptionPane.showMessageDialog(null, "Peso não pode ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido para o peso.");
            return;
        }

        try (Connection conn = DatabaseConnectionDAO.connect()) {
            String sqlLivro = "INSERT INTO livros (titulo, autor, preco, tipo, disponivel) VALUES (?, ?, ?, ?, ?) RETURNING id";
            try (PreparedStatement stmtLivro = conn.prepareStatement(sqlLivro)) {
                stmtLivro.setString(1, titulo);
                stmtLivro.setString(2, autor);
                stmtLivro.setDouble(3, preco);
                stmtLivro.setString(4, "LivroFisico");
                stmtLivro.setBoolean(5, true);

                ResultSet rs = stmtLivro.executeQuery();
                if (rs.next()) {
                    int livroId = rs.getInt("id");

                    String sqlDetalhesFisico = "INSERT INTO livros_fisicos (livro_id, peso) VALUES (?, ?)";
                    try (PreparedStatement stmtDetalhes = conn.prepareStatement(sqlDetalhesFisico)) {
                        stmtDetalhes.setInt(1, livroId);
                        stmtDetalhes.setDouble(2, peso);
                        stmtDetalhes.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Livro físico cadastrado com sucesso!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o livro físico.");
        }
    }
    
    public static void cadastrarEbook(String titulo, String autor, double preco, double tamanho) {
        if (titulo == null || autor == null) {
            JOptionPane.showMessageDialog(null, "Título e autor não podem ser nulos.");
            return;
        }

        try (Connection conn = DatabaseConnectionDAO.connect()) {
            String sqlLivro = "INSERT INTO livros (titulo, autor, preco, tipo, disponivel) VALUES (?, ?, ?, ?, ?) RETURNING id";
            try (PreparedStatement stmtLivro = conn.prepareStatement(sqlLivro)) {
                stmtLivro.setString(1, titulo);
                stmtLivro.setString(2, autor);
                stmtLivro.setDouble(3, preco);
                stmtLivro.setString(4, "Ebook");
                stmtLivro.setBoolean(5, true);

                ResultSet rs = stmtLivro.executeQuery();
                if (rs.next()) {
                    int livroId = rs.getInt("id");

                    String sqlDetalhesEbook = "INSERT INTO ebooks (livro_id, tamanho_arquivo) VALUES (?, ?)";
                    try (PreparedStatement stmtDetalhes = conn.prepareStatement(sqlDetalhesEbook)) {
                        stmtDetalhes.setInt(1, livroId);
                        stmtDetalhes.setDouble(2, tamanho);
                        stmtDetalhes.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Ebook cadastrado com sucesso!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void listarLivrosIndisponiveis() {
        try (Connection conn = DatabaseConnectionDAO.connect()) {
            String sql = "SELECT * FROM livros WHERE disponivel = false";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "Nenhum livro indisponível.");
                    return;
                }

                StringBuilder livrosListados = new StringBuilder("\nLista de Livros Indisponíveis:\n");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    double preco = rs.getDouble("preco");
                    String tipo = rs.getString("tipo");
                    livrosListados.append("ID: ").append(id).append(", Título: ").append(titulo)
                            .append(", Autor: ").append(autor).append(", Preço: R$ ").append(preco).append(", Tipo: ").append(tipo).append("\n");

                    if (tipo != null) {
                        if (tipo.equals("LivroFisico")) {
                            listarDetalhesFisicos(id, conn, livrosListados);
                        } else if (tipo.equals("Ebook")) {
                            listarDetalhesEbook(id, conn, livrosListados);
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, livrosListados.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listarLivrosDisponiveis() {
        try (Connection conn = DatabaseConnectionDAO.connect()) {
            String sql = "SELECT * FROM livros WHERE disponivel = true";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "Nenhum livro disponível.");
                    return;
                }

                StringBuilder livrosListados = new StringBuilder("\nLista de Livros Disponíveis:\n");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    double preco = rs.getDouble("preco");
                    String tipo = rs.getString("tipo");
                    livrosListados.append("ID: ").append(id).append(", Título: ").append(titulo)
                            .append(", Autor: ").append(autor).append(", Preço: R$ ").append(preco).append(", Tipo: ").append(tipo).append("\n");

                    if (tipo != null) {
                        if (tipo.equals("LivroFisico")) {
                            listarDetalhesFisicos(id, conn, livrosListados);
                        } else if (tipo.equals("Ebook")) {
                            listarDetalhesEbook(id, conn, livrosListados);
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, livrosListados.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pegarEmprestado(int livroId) {
        try (Connection conn = DatabaseConnectionDAO.connect()) {
            // Verifica se o livro existe
            String sqlVerificaExistencia = "SELECT id, disponivel FROM livros WHERE id = ?";
            try (PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerificaExistencia)) {
                stmtVerifica.setInt(1, livroId);
                ResultSet rs = stmtVerifica.executeQuery();

                if (rs.next()) {
                    boolean disponivel = rs.getBoolean("disponivel");

                    if (disponivel) {
                        // Atualiza a disponibilidade para false (livro emprestado)
                        String sqlUpdateDisponibilidade = "UPDATE livros SET disponivel = false WHERE id = ?";
                        try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateDisponibilidade)) {
                            stmtUpdate.setInt(1, livroId);
                            stmtUpdate.executeUpdate();
                        }
                        JOptionPane.showMessageDialog(null, "Livro emprestado com sucesso (indisponível).");
                    } else {
                        JOptionPane.showMessageDialog(null, "Este livro já está emprestado e aguardando devolução.");
                    }
                } else {
                    // Caso o livro não exista
                    JOptionPane.showMessageDialog(null, "Livro com ID " + livroId + " não encontrado.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao tentar pegar o livro emprestado.");
        }
    }

    public static void devolverLivro(int livroId) {
        try (Connection conn = DatabaseConnectionDAO.connect()) {
            String sqlVerificaDisponibilidade = "SELECT disponivel FROM livros WHERE id = ?";
            try (PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerificaDisponibilidade)) {
                stmtVerifica.setInt(1, livroId);
                ResultSet rs = stmtVerifica.executeQuery();

                if (rs.next()) {
                    boolean disponivel = rs.getBoolean("disponivel");

                    if (!disponivel) {
                        String sqlUpdateDisponibilidade = "UPDATE livros SET disponivel = true WHERE id = ?";
                        try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateDisponibilidade)) {
                            stmtUpdate.setInt(1, livroId);
                            stmtUpdate.executeUpdate();
                        }
                        JOptionPane.showMessageDialog(null, "Livro devolvido com sucesso (disponível novamente).");
                    } else {
                        JOptionPane.showMessageDialog(null, "Este livro já está disponível.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Método para listar todos os livros cadastrados
    public static void listarLivros() {
        try (Connection conn = DatabaseConnectionDAO.connect()) {
            String sql = "SELECT * FROM livros";
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "Nenhum livro cadastrado.");
                    return;
                }

                StringBuilder livrosListados = new StringBuilder("\nLista de Livros Cadastrados:\n");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    double preco = rs.getDouble("preco");
                    String tipo = rs.getString("tipo");
                    livrosListados.append("ID: ").append(id).append(", Título: ").append(titulo)
                            .append(", Autor: ").append(autor).append(", Preço: R$ ").append(preco).append(", Tipo: ").append(tipo).append("\n");

                    if (tipo != null) {
                        if (tipo.equals("LivroFisico")) {
                            listarDetalhesFisicos(id, conn, livrosListados);
                        } else if (tipo.equals("Ebook")) {
                            listarDetalhesEbook(id, conn, livrosListados);
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, livrosListados.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listarDetalhesFisicos(int livroId, Connection conn, StringBuilder livrosListados) throws SQLException {
        String sqlFisico = "SELECT * FROM livros_fisicos WHERE livro_id = ?";
        try (PreparedStatement stmtFisico = conn.prepareStatement(sqlFisico)) {
            stmtFisico.setInt(1, livroId);
            ResultSet rsFisico = stmtFisico.executeQuery();

            if (rsFisico.next()) {
                double peso = rsFisico.getDouble("peso");
                livrosListados.append("Peso: ").append(peso).append("kg\n");
            }
        }
    }



    private static void listarDetalhesEbook(int livroId, Connection conn, StringBuilder livrosListados) throws SQLException {
        String sqlEbook = "SELECT * FROM ebooks WHERE livro_id = ?";
        try (PreparedStatement stmtEbook = conn.prepareStatement(sqlEbook)) {
            stmtEbook.setInt(1, livroId);
            ResultSet rsEbook = stmtEbook.executeQuery();

            if (rsEbook.next()) {
                double tamanho = rsEbook.getDouble("tamanho_arquivo");
                livrosListados.append("Tamanho do Arquivo: ").append(tamanho).append("MB\n");
            }
        }
    }


    // Método para atualizar informações do livro
    public static void updateLivro(int livroId) {
        try (Connection conn = DatabaseConnectionDAO.connect()) {
            // Verifica se o livro com o ID fornecido existe
            String sqlVerificaExistencia = "SELECT * FROM livros WHERE id = ?";
            try (PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerificaExistencia)) {
                stmtVerifica.setInt(1, livroId);
                ResultSet rs = stmtVerifica.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Livro com ID " + livroId + " não encontrado.");
                    return; // Sai do método se o livro não existir
                }

                // Exibe os detalhes atuais do livro
                String tituloAtual = rs.getString("titulo");
                String autorAtual = rs.getString("autor");
                double precoAtual = rs.getDouble("preco");

                // Solicita as alterações do usuário
                String novoTitulo = JOptionPane.showInputDialog("Novo título (deixe vazio para manter o atual): ", tituloAtual);
                String novoAutor = JOptionPane.showInputDialog("Novo autor (deixe vazio para manter o atual): ", autorAtual);
                double novoPreco = Double.parseDouble(JOptionPane.showInputDialog("Novo preço (digite -1 para manter o atual): ", precoAtual));

                // SQL para atualizar as informações
                String sqlUpdate = "UPDATE livros SET titulo = COALESCE(NULLIF(?, ''), titulo), " +
                        "autor = COALESCE(NULLIF(?, ''), autor), preco = CASE WHEN ? >= 0 THEN ? ELSE preco END " +
                        "WHERE id = ?";
                try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                    stmtUpdate.setString(1, novoTitulo);
                    stmtUpdate.setString(2, novoAutor);
                    stmtUpdate.setDouble(3, novoPreco);
                    stmtUpdate.setDouble(4, novoPreco);
                    stmtUpdate.setInt(5, livroId); // Atualiza o livro com o ID fornecido
                    stmtUpdate.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Livro atualizado com sucesso!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar o livro.");
        }
    }


    // Método para deletar um livro
    public static void deleteLivros(int livroId) {
        try (Connection conn = DatabaseConnectionDAO.connect()) {
            // Verifica se o livro com o ID especificado existe
            String sqlVerificaExistencia = "SELECT id FROM livros WHERE id = ?";
            try (PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerificaExistencia)) {
                stmtVerifica.setInt(1, livroId);
                ResultSet rs = stmtVerifica.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Livro com ID " + livroId + " não encontrado.");
                    return; // Caso o livro não exista, sai do método
                }

                // Se o livro existe, realiza a exclusão
                String sqlDelete = "DELETE FROM livros WHERE id = ?";
                try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete)) {
                    stmtDelete.setInt(1, livroId);
                    stmtDelete.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Livro removido com sucesso!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao tentar remover o livro.");
        }
    }
}