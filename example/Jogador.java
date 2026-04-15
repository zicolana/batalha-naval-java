package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Jogador {
    private String[] navios = new String[3];
    private Scanner entrada = new Scanner(System.in);
    private int naviosRestantes = 3;

    private final char[][] tabuleiro = new char[3][6];

    public Jogador() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                tabuleiro[i][j] = '~';
            }
        }
    }

    public void posicionarNavios(List<String> opcoes) {
        boolean invalido, repetido;


        do {
            System.out.println("Digite sua primeira casa: ");
            navios[0] = entrada.nextLine().trim().toLowerCase();

            System.out.println("Digite sua segunda casa: ");
            navios[1] = entrada.nextLine().trim().toLowerCase();

            System.out.println("Digite sua terceira casa: ");
            navios[2] = entrada.nextLine().trim().toLowerCase();

            invalido = !opcoes.contains(navios[0]) ||
                    !opcoes.contains(navios[1]) ||
                    !opcoes.contains(navios[2]);

            repetido = navios[0].equals(navios[1]) ||
                    navios[0].equals(navios[2]) ||
                    navios[1].equals(navios[2]);

            if (invalido) {
                System.out.println("❌ Uma das casas selecionadas não é válida!");
            } else if (repetido) {
                System.out.println("⚠️ Você não pode escolher a mesma casa mais de uma vez!");
            }

        } while (invalido || repetido);

        for (String pos : navios) {
            marcarNavioNoTabuleiro(pos);   // ex dps//
        }


        System.out.println("\n✅ Seus navios foram posicionados em: "
                + Arrays.toString(navios) + "\n");
    }
    private void marcarNavioNoTabuleiro(String pos) {
        int linha = pos.charAt(0) - 'a';
        int coluna = Integer.parseInt(pos.substring(1)) - 1; //
        tabuleiro[linha][coluna] = 'O';}

    public boolean receberAtaque(String tiroBot) {

        int linha = tiroBot.charAt(0) - 'a';
        int coluna = Integer.parseInt(tiroBot.substring(1)) - 1;

        for (int i = 0; i < navios.length; i++) {
            if (navios[i] != null && navios[i].equals(tiroBot)) {
                navios[i] = null;
                naviosRestantes--;

                tabuleiro[linha][coluna] = 'X';
                return true;

            }
        }
            tabuleiro[linha][coluna] = '*';
        return false;
    }
        public char[][] getTabuleiro() {
            return tabuleiro;
        }
        public int getNaviosRestantes() {
            return naviosRestantes;}


}

