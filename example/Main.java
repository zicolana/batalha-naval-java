package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.println(" =================== ");
        System.out.println(" || BATALHA NAVAL || ");
        System.out.println(" =================== ");

        System.out.println(" ");
        System.out.print("Digite seu nome, comandante: ");

        String nomeJogador = entrada.nextLine();

        Menu menu = new Menu(nomeJogador);
        menu.iniciar();
    }
}
