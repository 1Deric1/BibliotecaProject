package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {

    private JFrame frame;
    private JPanel panel;
    private JButton cadastrarLivroFisicoButton;
    private JButton cadastrarEbookButton;
    private JButton listarLivrosButton;
    private JButton atualizarLivrosButton;
    private JButton deletarLivroButton;
    private JButton listarLivrosDisponiveisButton;  // Adicionando novo botão
    private JButton listarLivrosIndisponiveisButton; // Adicionando novo botão
    private JButton pegarEmprestadoButton;  // Adicionando o botão para pegar emprestado

    public View() {
        frame = new JFrame("Menu da Livraria");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Criando o painel para os botões
        panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 10, 10)); // Alterando para 8 linhas (agora com novos botões)
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adicionando margens no painel

        // Criando os botões
        cadastrarLivroFisicoButton = new JButton("Cadastrar Livro Físico");
        cadastrarEbookButton = new JButton("Cadastrar Ebook");
        listarLivrosButton = new JButton("Listar Livros");
        listarLivrosDisponiveisButton = new JButton("Listar Livros Disponíveis");
        listarLivrosIndisponiveisButton = new JButton("Listar Livros Indisponíveis");
        atualizarLivrosButton = new JButton("Atualizar Livros");
        deletarLivroButton = new JButton("Deletar Livro");
        pegarEmprestadoButton = new JButton("Pegar Emprestado");

        // Adicionando os botões ao painel
        panel.add(cadastrarLivroFisicoButton);
        panel.add(cadastrarEbookButton);
        panel.add(listarLivrosButton);
        panel.add(listarLivrosDisponiveisButton);
        panel.add(listarLivrosIndisponiveisButton);
        panel.add(pegarEmprestadoButton);  // Adicionando o botão para pegar emprestado
        panel.add(atualizarLivrosButton);
        panel.add(deletarLivroButton);

        // Adicionando painel à janela
        frame.add(panel, BorderLayout.CENTER);

        // Ajustando o layout e exibindo a janela
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Métodos para adicionar os listeners nos botões
    public void addCadastrarLivroFisicoListener(ActionListener listener) {
        cadastrarLivroFisicoButton.addActionListener(listener);
    }

    public void addCadastrarEbookListener(ActionListener listener) {
        cadastrarEbookButton.addActionListener(listener);
    }

    public void addListarLivrosListener(ActionListener listener) {
        listarLivrosButton.addActionListener(listener);
    }

    public void addListarLivrosDisponiveisListener(ActionListener listener) {
        listarLivrosDisponiveisButton.addActionListener(listener);
    }

    public void addListarLivrosIndisponiveisListener(ActionListener listener) {
        listarLivrosIndisponiveisButton.addActionListener(listener);
    }

    public void addPegarEmprestadoListener(ActionListener listener) {  // Adicionando o listener para o novo botão
        pegarEmprestadoButton.addActionListener(listener);
    }

    public void addAtualizarLivrosListener(ActionListener listener) {
        atualizarLivrosButton.addActionListener(listener);
    }

    public void addDeletarLivroListener(ActionListener listener) {
        deletarLivroButton.addActionListener(listener);
    }

    // Método para retornar a janela principal
    public JFrame getFrame() {
        return frame;
    }
}
