/*
    === Realize a implementação do Jogo da Velha em Java. ===

    O Jogo da Velha é um jogo para dois jogadores e que deve ser jogado sobre um tabuleiro de 3x3 casas.
    Um dos jogadores escolhe uma casa e a marca com um círculo. Em seguida o outro escolhe outra casa e a marca com um xis.
    Os jogadores continuam se alternando desta forma, até que uma linha com os mesmos símbolos seja formada, na vertical, horizontal ou diagonal.
    No caso, o jogador que completou a linha, vence o jogo. O jogo também acaba se não houver mais jogadas possíveis, o que caracteriza um empate.

    Durante a execução do programa, cada jogador deve escrever sua jogada (coordenadas) na linha de comando, e o jogo deve imprimir o tabuleiro
    e esperar pela jogada do próximo jogador. O programa não deve permitir que o jogador tente marcar uma casa que já esteja marcada, nem que tente
    jogar em casas que não existam. A classe que encapsula a lógica do jogo deve conter métodos para testar se uma dada jogada é válida, que devem
    ser chamados antes de efetuar a jogada de fato. Ou pode-se fazer este tratamento de erro com exceções.
 */

import java.util.Scanner;
import java.util.InputMismatchException;

public class jogoDaVelha {

    // FUNÇÃO QUE EXIBE TABULEIRO
    public static void tabuleiro(int[][] estrutura){
        // === Impressão do tabuleiro ===
        System.out.println("\n      0     1     2  "); // ---Coluna
        for (int i = 0; i < 3; i++) {
            System.out.println("   +-----+-----+-----+");
            System.out.print(i + "  |"); // ---Linha
            for (int j = 0; j < 3; j++) {
                char simbolo = ' ';
                // ---Definindo símbolo do jogador 1
                if (estrutura[i][j] == 1) {
                    simbolo = 'X';
                    // ---Definindo símbolo do jogador 2
                } else if (estrutura[i][j] == 2) {
                    simbolo = 'O';
                }
                System.out.print("  " + simbolo + "  |");
            }
            System.out.println();
        }
        System.out.println("   +-----+-----+-----+");
    }

    // FUNÇÃO JOGO
    public static void jogar() {
        Scanner ler = new Scanner(System.in);

        int[][] estrutura = new int[3][3]; // ---Tabuleiro [linha][coluna]
        int jogadorAtual = 1; // ---Jogadores
        int numJogadas = 0; // ---Controle de número de jogadas
        boolean jogoAtivo = true; // ---Partida rolando

        while (jogoAtivo) {
            tabuleiro(estrutura);
            int linha = 0;
            int coluna = 0;
            boolean entradaValida = false; // ---Valida tratamento de erro

            while(!entradaValida) {
                try { // ---Tratamento de erro: caso o usuário não digite um número inteiro
                    // === Solicitando jogadas ===
                    System.out.println("\nJOGADOR: " + jogadorAtual + " (" + (jogadorAtual == 1 ? "X" : "O") + ")"); // ---Exibe quem é o jogador
                    System.out.print("Digite a LINHA (0, 1, 2): "); // ---Solicitando linha
                    linha = ler.nextInt();
                    System.out.print("Digite a COLUNA (0, 1, 2): "); // ---Solicitando coluna
                    coluna = ler.nextInt();

                    entradaValida = true; // ---Validação: tratamento de erro
                } catch (InputMismatchException e) {
                    System.out.println("\n[!] ERRO: Você deve apenas digitar números inteiros (0, 1, 2).\n");
                    ler.next(); // ---Descarta oq o usuário digitou
                }
            }
            // === Validando jogada ===
            if (linha >= 0 && linha < 3 && coluna >= 0 && linha < 3 && estrutura[linha][coluna] == 0) {
                estrutura[linha][coluna] = jogadorAtual; // ---Grava escolha do jogador
                numJogadas++; // ---A cada interação, incrementa o número de jogadas

                String status = resultado(estrutura, jogadorAtual, numJogadas); // ---Chama função: vitória, empate ou continue

                // ---Resultado
                if (status.equals("vitoria")) {
                    System.out.printf("\nVITÓRIA!!! O jogador %d venceu!\n\n", jogadorAtual);
                    jogoAtivo = false;
                } else if (status.equals("empate")) {
                    System.out.println("\nEMPATE! Ambos os jogadores venceram!\n\n");
                    jogoAtivo = false;
                } else {
                    jogadorAtual = (jogadorAtual == 1) ? 2 : 1; // ---Passa para o próximo jogador
                }
            } else {
                System.out.println("\n[!] ERRO: Posição inválida ou ja ocupada. Tente novamente...");
            }
        }
    }

    // FUNÇÃO DE RETORNO DE RESULTADO
    public static String resultado(int[][] estrutura, int jogador, int jogadas){
        // ---Verifica vitória por horizontal ou vertical
        for (int i = 0; i < 3; i++) {
            if ((estrutura[i][0] == jogador && estrutura[i][1] == jogador && estrutura[i][2] == jogador) ||
                    (estrutura[0][i] == jogador && estrutura[1][i] == jogador && estrutura[2][i] == jogador)) {
                return "vitoria";
            }
        }
        // ---Verifica vitória por diagonais
        if ((estrutura[0][0] == jogador && estrutura[1][1] == jogador && estrutura[2][2] == jogador) ||
                (estrutura[0][2] == jogador && estrutura[1][1] == jogador && estrutura[2][0] == jogador)) {
            return "vitoria";
        }
        // ---Verifica se não terminou em "velha" (todas as posições preenchidas, nenhuma vitória)
        if(jogadas == 9){
            return "empate";
        }
        // ---Caso contrário, próxima jogada
        return "continue";
    }

    // FUNÇÃO MENU REGRAS
    public static void regras(){
        System.out.println("\n--- REGRAS: JOGO DA VELHA ---");
        System.out.println("1. O jogo é para 2 jogadores.");
        System.out.println("2. O jogador 1 joga com X e o jogador 2 com O.");
        System.out.println("3. Os jogadores se alternam, escolhendo uma linha (0, 1 ou 2) e coluna (0, 1 ou 2) para marcar sua casa.");
        System.out.println("4. Vence o jogador que completar a mesma linha, coluna ou diagonal.");
        System.out.println("5. Empate quando todas as 9 casas estao preenchidas e nenhum jogador venceu.");
        System.out.println("6. Nao eh permitido jogar em uma casa ja ocupada ou invalida");
        System.out.println("\n--- BOA SORTE E DIVIRTA-SE!!! ---\n");
    }

    // FUNÇÃO MENU CREDITOS (APRESENTAÇÃO DEV)
    public static void creditos(){
        System.out.println("\n--- CREDITOS: JOGO DA VELHA ---");
        System.out.println("Desenvolvedor: 4rt3misTK");
        System.out.println("Nome real: Gabriela P. S. Pontes");
        System.out.println("\n--- OBJETIVO DO PROJETO ---");
        System.out.println("Este projeto foi desenvolvido para demonstrar a evolucao");
        System.out.println("na linguagem Java apos a conclusao da primeira etapa do curso,");
        System.out.println("especificamente no modulo de Logica de Programacao.");
        System.out.println("\n--- LINKS DO DESENVOLVEDOR ---");
        System.out.println("LinkedIn: https://www.linkedin.com/in/gabriela-pedroso-8a419726a/");
        System.out.println("GitHub:   https://github.com/4rt3mis4D4");
        System.out.println("\n--- OBRIGADA POR JOGAR! ---\n");
    }

    public static void main (String[]args){
        Scanner ler = new Scanner(System.in);
        int opcao = 0; // ---Opção de menu
        boolean entradaValida = false; // ---Valida tratamento de erro

        do{
            // ---Exibindo Menu
            while(!entradaValida) {
                // ---Tratamento de erro: caso o usuário não digite um número inteiro
                try {
                    System.out.println("+-+-+ JOGO DA VELHA +-+-+");
                    System.out.println("      1. JOGAR");
                    System.out.println("      2. REGRAS");
                    System.out.println("      3. CREDITOS");
                    System.out.println("      0. SAIR");
                    System.out.print("\nDigite aqui: ");
                    opcao = ler.nextInt(); // ---Captura da opção que usuário digitou

                    entradaValida = true; // ---Validação: tratamento de erro
                } catch (InputMismatchException e) {
                    System.out.println("\n[!] ERRO: Você deve apenas digitar números inteiros (0, 1, 2).\n");
                    ler.next(); // ---Descarta oq o usuário digitou
                }
            }
            // ---Switch para escolha do usuário
            switch (opcao) {
                case 1:
                    jogar(); // ---1. JOGAR
                    break;
                case 2:
                    regras(); // ---2. REGRAS
                    break;
                case 3:
                    creditos(); // ---3. CREDITOS
                    break;
                case 0: // ---0. SAIR
                    System.out.println("\n--- OBRIGADA POR JOGAR! ---");
                    System.out.println("Volte sempre para mais partidas de Jogo da Velha!");
                    System.out.println("Encerrando sistema...\n");
                    break;
                default: // ---Nenhuma das opções do Menu
                    System.out.println("\n--- OPÇÃO INVÁLIDA! ---");
                    System.out.println("Digite apenas 0, 1, 2 ou 3.");
                    System.out.println("Tente novamente...\n");
            }
        } while (opcao!=0); // ---Para o laço de repetição quando usuário digitar 0
    }
}
