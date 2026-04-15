package org.example;

import java.util.Scanner;

public class Menu {
    private final Scanner entrada = new Scanner(System.in);
    private final String nomeJogador;


    public Menu(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public void iniciar() {
        int opcao;

        do {
            System.out.println("\n=== ⚓ BATALHA NAVAL ===");
            System.out.println("Bem-vindo, comandante " + nomeJogador + "!");
            System.out.println("1 - Iniciar jogo");
            System.out.println("2 - Ver regras");
            System.out.println("3 - Sair");
            System.out.println("4 - Rank");
            System.out.print("Escolha uma opção: ");

            while (!entrada.hasNextInt()) {
                System.out.print("Digite um número válido: ");
                entrada.next();
            }

            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {
                case 1 -> new Tabuleiro(nomeJogador).jogar();
                case 2 -> mostrarRegras();
                case 3 -> System.out.println("Saindo... Até logo, comandante!");
                case 4 -> {
                    Ranking ranking = new Ranking();
                    ranking.mostrarRanking();
                }
                default -> System.out.println("Opção inválida, tente novamente!");
            }

        } while (opcao != 3);
    }

    private void mostrarRegras() {
        System.out.println("\n=== REGRAS ===");
        System.out.println("1️⃣ Cada jogador (você e o bot) tem 3 navios.");
        System.out.println("2️⃣ Os navios são posicionados em coordenadas (ex: a1, b3, c5) em um tabuleiro 3x6.");
        System.out.println("3️⃣ Cada turno, você escolhe uma posição para atacar.");
        System.out.println("4️⃣ O jogo termina quando todos os navios do jogador ou do bot forem destruídos.");
        System.out.println("Boa sorte, comandante!\n");
    }
}
