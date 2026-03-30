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

public class jogoDaVelha {
    public static void main(String[] args){
        Scanner ler = new Scanner(System.in);

        int [][] estrutura = new int [3][3]; // ---Tabuleiro [linha][coluna]
        int jogadorAtual = 1; // ---Jogadores
        int numJogadas = 0; // ---Controle de número de jogadas
        boolean jogoAtivo = true; // ---Partida rolando

       while(jogoAtivo){
           // === Impressão do tabuleiro ===
           System.out.println("\n      0     1     2  "); // ---Coluna
           for(int i = 0; i < 3; i++){
               System.out.println("   +-----+-----+-----+");
               System.out.print(i + "  |"); // ---Linha
               for (int j = 0; j < 3; j++){
                   char simbolo = ' ';
                   // ---Definindo símbolo do jogador 1
                   if (estrutura[i][j] == 1){
                       simbolo = 'X';
                   // ---Definindo símbolo do jogador 2
                   } else if (estrutura[i][j] == 2){
                       simbolo = 'O';
                   }

                   System.out.print("  " + simbolo + "  |");
               }
               System.out.println();
           }
           System.out.println("   +-----+-----+-----+");

           // === Solicitando jogadas ===
           System.out.println("\nJOGADOR: " + jogadorAtual + " (" + (jogadorAtual == 1 ? "X" : "O") + ")"); // ---Exibe quem é o jogador
           System.out.print("Digite a LINHA (0, 1, 2): "); // ---Solicitando linha
           int linha = ler.nextInt();
           System.out.print("Digite a COLUNA (0, 1, 2): "); // ---Solicitando coluna
           int coluna = ler.nextInt();

           // === Validando jogada ===
           if(linha >= 0 && linha < 3 && coluna >= 0 && linha < 3 && estrutura[linha][coluna] == 0){
               estrutura[linha][coluna] = jogadorAtual; // ---Grava escolha do jogador
               numJogadas++;

               // === Verificando resultado ===
               boolean venceu = false;

               // ---Verifica vitória por horizontal ou vertical
               for(int i = 0; i < 3; i++){
                   //System.out.println(estrutura[i][0] + " " + estrutura[i][1] + " " + estrutura[i][2]);

                   if((estrutura[i][0] == jogadorAtual && estrutura[i][1] == jogadorAtual && estrutura[i][2] == jogadorAtual) ||
                           (estrutura[0][i] == jogadorAtual && estrutura[1][i] == jogadorAtual && estrutura[2][i] == jogadorAtual)){
                       venceu = true;
                   }
               }

               // ---Verifica vitória por diagonais
               if((estrutura[0][0] == jogadorAtual && estrutura[1][1] == jogadorAtual && estrutura[2][2] == jogadorAtual) ||
                       (estrutura[0][2] == jogadorAtual && estrutura[1][1] == jogadorAtual && estrutura[2][0] == jogadorAtual)){
                   venceu = true;
               }

               // ---Resultado
               if(venceu == true){
                   System.out.printf("\nVITÓRIA!!! O jogador %d venceu!", jogadorAtual);
                   jogoAtivo = false;
               } else if (numJogadas == 9){
                   System.out.println("\nEMPATE! Ambos os jogadores venceram!");
                   jogoAtivo = false;
               } else {
                   jogadorAtual = (jogadorAtual == 1) ? 2 : 1; // ---Passa para o próximo jogador
               }
           } else {
               System.out.println("\n[!] ERRO: Posição inválida ou ja ocupada. Tente novamente...");
           }
       }
    }
}
