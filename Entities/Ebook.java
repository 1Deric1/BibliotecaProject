package Entities;

// Subclasse Ebook
public class Ebook extends Livro {
    private double tamanhoArquivo; // Tamanho em MB

    public Ebook(String titulo, String autor, double preco, double tamanhoArquivo) {
        super(titulo, autor, preco); // Chamada ao construtor da classe base
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public double getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(double tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    // Sobrescrevendo o método detalhes (polimorfismo)
    @Override
    public String detalhes() {
        return "Ebook - " + apresentar() + ", Tamanho do arquivo: " + tamanhoArquivo + "MB";
    }
}