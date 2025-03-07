package Controller;

import View.View;
import ConexaoDAO.OperationsDAO;
import javax.swing.JOptionPane;

public class Controller {

    private View view;

    public Controller(View view) {
        this.view = view;
    }

    public void cadastrarLivroFisico() {
        // Obtém os dados de entrada da view
        String titulo = JOptionPane.showInputDialog(view.getFrame(), "Título do Livro Físico:");
        String autor = JOptionPane.showInputDialog(view.getFrame(), "Autor do Livro Físico:");
        String precoStr = JOptionPane.showInputDialog(view.getFrame(), "Preço do Livro Físico:");
        String pesoStr = JOptionPane.showInputDialog(view.getFrame(), "Peso do Livro Físico:");

        try {
            // Converte as entradas de preço e peso para double
            double preco = Double.parseDouble(precoStr);
            double peso = Double.parseDouble(pesoStr);

            // Chama a operação para cadastrar o livro físico
            OperationsDAO.cadastrarLivroFisico(titulo, autor, preco, peso);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Preço ou peso inválido. Insira um valor numérico.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Erro ao cadastrar livro físico: " + e.getMessage());
        }
    }

    public void cadastrarEbook() {
        // Obtém os dados de entrada da view
        String titulo = JOptionPane.showInputDialog(view.getFrame(), "Título do Ebook:");
        String autor = JOptionPane.showInputDialog(view.getFrame(), "Autor do Ebook:");
        String precoStr = JOptionPane.showInputDialog(view.getFrame(), "Preço do Ebook:");
        String tamanhoStr = JOptionPane.showInputDialog(view.getFrame(), "Tamanho do Ebook (em MB):");

        try {
            // Converte as entradas de preço e tamanho para double
            double preco = Double.parseDouble(precoStr);
            double tamanho = Double.parseDouble(tamanhoStr);

            // Chama a operação para cadastrar o ebook
            OperationsDAO.cadastrarEbook(titulo, autor, preco, tamanho);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Preço ou tamanho inválido. Insira um valor numérico.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Erro ao cadastrar ebook: " + e.getMessage());
        }
    }

    public void listarLivros() {
        try {
            // Chama a operação para listar todos os livros
            OperationsDAO.listarLivros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Erro ao listar livros: " + e.getMessage());
        }
    }

    public void listarLivrosDisponiveis() {
        try {
            // Chama a operação para listar livros disponíveis
            OperationsDAO.listarLivrosDisponiveis();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Erro ao listar livros disponíveis: " + e.getMessage());
        }
    }

    public void listarLivrosIndisponiveis() {
        try {
            // Chama a operação para listar livros indisponíveis
            OperationsDAO.listarLivrosIndisponiveis();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Erro ao listar livros indisponíveis: " + e.getMessage());
        }
    }

    public void pegarEmprestado() {
        try {
            // Solicita o ID do livro para pegar emprestado
            int livroId = Integer.parseInt(JOptionPane.showInputDialog(view.getFrame(), "Informe o ID do livro para pegar emprestado:"));
            // Chama a operação para pegar o livro emprestado
            OperationsDAO.pegarEmprestado(livroId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "ID inválido. Insira um número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Erro ao pegar o livro emprestado: " + e.getMessage());
        }
    }

    public void atualizarLivros() {
        try {
            // Solicita o ID do livro para atualização
            int livroId = Integer.parseInt(JOptionPane.showInputDialog(view.getFrame(), "Informe o ID do livro para atualizar:"));
            // Chama a operação para atualizar o livro
            OperationsDAO.updateLivro(livroId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "ID inválido. Insira um número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Erro ao atualizar o livro: " + e.getMessage());
        }
    }

    public void deletarLivro() {
        try {
            // Solicita o ID do livro para deletar
            int livroId = Integer.parseInt(JOptionPane.showInputDialog(view.getFrame(), "Informe o ID do livro para deletar:"));
            // Chama a operação para deletar o livro
            OperationsDAO.deleteLivros(livroId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "ID inválido. Insira um número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Erro ao deletar o livro: " + e.getMessage());
        }
    }
}
