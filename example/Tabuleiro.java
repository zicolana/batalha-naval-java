package org.example;

import java.util.*;

public class Tabuleiro {
    private final List<String> opcoes = Arrays.asList(
            "a1", "a2", "a3", "a4", "a5", "a6",
            "b1", "b2", "b3", "b4", "b5", "b6",
            "c1", "c2", "c3", "c4", "c5", "c6"
    );

    private final Scanner entrada = new Scanner(System.in);
    private final Ranking ranking = new Ranking();
    private final String nomeJogador;


    public Tabuleiro(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public void jogar() {
        Jogador jogador = new Jogador();
        Bot bot = new Bot();

        System.out.println("\nPosicione seus navios (ex: a1, b3, c6)");
        jogador.posicionarNavios(opcoes);
        bot.posicionarNavios(opcoes);

        System.out.println("\n🚢 Navios posicionados! O jogo vai começar!\n");

        int jogadas = 0;
        boolean jogoAtivo = true;

        while (jogoAtivo) {
            System.out.print("Escolha uma posição para atacar: ");
            String tiroJogador = entrada.nextLine().trim().toLowerCase();

            while (!opcoes.contains(tiroJogador)) {
                System.out.print("Posição inválida! Tente novamente: ");
                tiroJogador = entrada.nextLine().trim().toLowerCase();
            }

            jogadas++;

            if (bot.receberAtaque(tiroJogador)) {
                System.out.println("💥 Você acertou um navio inimigo!");
            } else {
                System.out.println("🌊 Você atirou na água!");
            }

            if (bot.getNaviosRestantes() == 0) {
                System.out.println("\n🎉 Parabéns, comandante! Você venceu!");
                jogoAtivo = false;
                fimDePartida(true, jogadas);
                break;
            }

            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            String tiroBot = bot.atirar(opcoes);
            System.out.println("\n🤖 O bot atacou a posição: " + tiroBot);
            if (jogador.receberAtaque(tiroBot)) {
                System.out.println("💥 O bot acertou um dos seus navios!");
            } else {
                System.out.println("🌊 O bot errou!");
            }
            if (jogador.getNaviosRestantes() == 0) {
                System.out.println("\n☠️  O bot venceu! Seus navios foram destruídos.");
                jogoAtivo = false;
                fimDePartida(false, jogadas);
            }
            System.out.println("\n--- STATUS ---");
            System.out.println("Seus navios restantes: " + jogador.getNaviosRestantes());
            System.out.println("Navios inimigos restantes: " + bot.getNaviosRestantes());
            System.out.println("-----------------\n");

            imprimirTabuleiro("SEU TABULEIRO", jogador.getTabuleiro(),false);
            imprimirTabuleiro("TABULEIRO DO BOT (NAVIOS OCULTOS)", bot.getTabuleiro(),true);
        }
    }
    private void imprimirTabuleiro(String titulo, char[][] tab, boolean ocultarNavios) {
        System.out.println("\n=== " + titulo + " ===");
        System.out.println("    1  2  3  4  5  6");

        char linha = 'A';
        for (int i = 0; i < 3; i++) {
            System.out.print(linha + " | ");
            for (int j = 0; j < 6; j++) {
                char c = tab[i][j];
                if (ocultarNavios && c == 'O'){
                    System.out.print("~  ");
                } else {
                    System.out.print(c + "  ");
                }
            }
            System.out.println();
            linha++;
        }
        System.out.println();
    }
    private void fimDePartida(boolean venceu, int jogadas) {
        int opcao;
        do {
            System.out.println("\n=== ⚓ FIM DE PARTIDA ===");
            System.out.println("1 - Salvar resultado");
            System.out.println("2 - Jogar novamente");
            System.out.println("3 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            while (!entrada.hasNextInt()) {
                System.out.print("Digite um número válido: ");
                entrada.next();
            }

            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {
                case 1 -> salvarResultado(venceu, jogadas);
                case 2 -> jogar();
                case 3 -> new Menu(nomeJogador).iniciar();
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao < 1 || opcao > 3);
    }

    private void salvarResultado(boolean venceu, int jogadas) {
        ranking.salvarResultado(nomeJogador, jogadas, venceu);
        System.out.println("✅ Resultado salvo com sucesso, comandante " + nomeJogador + "!");
    }
}


