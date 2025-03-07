package Entities;

import java.util.ArrayList;
import java.util.Scanner;

//  Classe base Livro (demonstra encapsulamento e herança)
public abstract class Livro {
    private String titulo;
    private String autor;
    private double preco;

    // Construtor
    public Livro(String titulo, String autor, double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
    }

    // Métodos getter e setter (encapsulamento)

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    // Método abstrato para sobrescrever nas subclasses
    public abstract String detalhes();

    // Método comum para exibir informações básicas
    public String apresentar() {
        return "Título: " + titulo + ", Autor: " + autor + ", Preço: R$ " + preco;
    }
}
