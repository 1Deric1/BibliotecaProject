package Application;

import Controller.Controller;
import View.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application {

    public static void main(String[] args) {
        // Cria a interface gráfica
        View view = new View();

        // Cria o controlador que vai intermediar as ações entre a View e o OperationsDAO
        Controller controller = new Controller(view);

        // Define os listeners para os botões
        view.addCadastrarLivroFisicoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cadastrarLivroFisico();
            }
        });

        view.addCadastrarEbookListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cadastrarEbook();
            }
        });

        view.addListarLivrosListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.listarLivros();
            }
        });

        view.addListarLivrosDisponiveisListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.listarLivrosDisponiveis();
            }
        });

        view.addListarLivrosIndisponiveisListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.listarLivrosIndisponiveis();
            }
        });

        view.addPegarEmprestadoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.pegarEmprestado();
            }
        });

        view.addAtualizarLivrosListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.atualizarLivros();
            }
        });

        view.addDeletarLivroListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deletarLivro();
            }
        });
    }
}
