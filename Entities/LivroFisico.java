package Entities;

public class LivroFisico extends Livro {
    private double peso; // Peso do livro em kg

    public LivroFisico(String titulo, String autor, double preco, double peso) {
        super(titulo, autor, preco); // Chamada ao construtor da classe base
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    // Sobrescrevendo o método detalhes (polimorfisAo)
    @Override
    public String detalhes() {
        return "Livro Físico - " + apresentar() + ", Peso: " + peso + "kg";
    }
}