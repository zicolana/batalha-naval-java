package org.example;

import java.util.*;

public class Bot {
    private String[] navios = new String[3];
    private int naviosRestantes = 3;
    private final char[][] tabuleiro = new char[3][6];
    private Random random = new Random();

    public Bot() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                tabuleiro[i][j] = '~';
            }
        }
    }
    public void posicionarNavios(List<String> opcoes) {
        Set<String> naviosBot = new HashSet<>();

        while (naviosBot.size() < 3) {
            int index = random.nextInt(opcoes.size());
            naviosBot.add(opcoes.get(index));
        }

        navios = naviosBot.toArray(new String[0]);

        for (String pos : navios) {
            marcarNavioNoTabuleiro(pos);
        }

        System.out.println("🤖 O bot posicionou seus navios (posições secretas).");
    }

    private void marcarNavioNoTabuleiro(String pos) {
        int linha = pos.charAt(0) - 'a';
        int coluna = Integer.parseInt(pos.substring(1)) - 1;
        tabuleiro[linha][coluna] = 'O';
    }
    public boolean receberAtaque(String tiroJogador) {

        int linha = tiroJogador.charAt(0) - 'a';
        int coluna = Integer.parseInt(tiroJogador.substring(1)) - 1;

        for (int i = 0; i < navios.length; i++) {
            if (navios[i] != null && navios[i].equals(tiroJogador)) {
                navios[i] = null;
                naviosRestantes--;

                tabuleiro[linha][coluna] = 'X';
                return true;
            }
        }
        tabuleiro[linha][coluna] = '*';
        return false;
    }
    public String atirar(List<String> opcoes) {
        try {
            System.out.print("\n🤖 O bot está pensando");

            int tempoEspera = 1000 + random.nextInt(1000);
            for (int i = 0; i < 3; i++) {
                Thread.sleep(tempoEspera / 3);
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return opcoes.get(random.nextInt(opcoes.size()));
    }
    public int getNaviosRestantes() {
        return naviosRestantes;
    }

    public char[][] getTabuleiro() {
        return tabuleiro;
    }

}



