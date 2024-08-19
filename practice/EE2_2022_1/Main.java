/*
 * Crie N jogadores e um vetor com N-1 cadeiras, cada cadeira é um tipo atômico. A cada rodada, todos os jogadores tentarão (aleatoriamente) 
 * sentar em alguma cadeira vazia. Eles continuam tentando até todas as cadeiras estarem ocupadas. Em seguida, é mostrado na tela o único jogador 
 * que não conseguiu sentar “O jogador X foi eliminado”, onde X é o seu nome. Todas as cadeiras ficam vagas novamente e uma é retirada do jogo. 
 * Inicia-se uma nova rodada. O programa acaba quando só sobrar 1 jogador, e deverá ser mostrado na tela “O jogador X foi o vencedor!”
 */

package practice.EE2_2022_1;

import java.util.*;

class Cadeiras{
    private int total;
    private int qtd_ocupadas;
    private int[] cadeiras; 
    private List<Jogador> jogadores_sentados;

    public Cadeiras(int total){
        this.total = total;
        this.qtd_ocupadas = 0;
        // o array cadeiras inicia com 0 em todas as posições
        this.cadeiras = new int[total];
        this.jogadores_sentados = new ArrayList<Jogador>();
    }

    public synchronized void sentar(Jogador jogador){
        String nome = jogador.getNome();
        if(total == 0){
            System.out.println("O jogador " + nome + " foi o vencedor!");
            jogador.interrupt();
        }
        // se o jogador ainda não sentou 
        else if(!jogadores_sentados.contains(jogador)){
            // ainda tem cadeiras disponíveis
            if(qtd_ocupadas < total){
                // tenta sentar em uma cadeira aleatória
                Random random = new Random();
                int num_cadeira = random.nextInt(total);
                if(cadeiras[num_cadeira] == 0){
                    cadeiras[num_cadeira] = 1;
                    qtd_ocupadas ++;
                    jogadores_sentados.add(jogador);
                    System.out.println("O jogador " + nome + " sentou na cadeira " + num_cadeira);
                } else {
                    System.out.println("O jogador " + nome + " tentou sentar na cadeira " + num_cadeira + " mas ela já estava ocupada");
                }
            } 
            // não tem mais cadeiras disponíveis
            else {
                System.out.println("O jogador " + nome + " foi eliminado!");
                // interrompe a thread do jogador
                jogador.interrupt();
                // reinicia tudo para a próxima rodada
                jogadores_sentados = new ArrayList<Jogador>();
                total--;
                qtd_ocupadas = 0;
                cadeiras = new int[total];
            }
        }
    }
}

class Jogador extends Thread{
    private String nome;
    private Cadeiras cadeiras;

    public Jogador(String nome, Cadeiras cadeiras){
        this.nome = nome;
        this.cadeiras = cadeiras;
    }

    public String getNome(){
        return this.nome;
    }

    public void run(){
        while(!isInterrupted()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
            this.cadeiras.sentar(this);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String[] nomes = {"Mateus", "Guilherme", "Amanda", "Ruy", "Carmen", "Vitor", "Paulo", "Sara", "Raquel", "Jeferson"};
        int qtd_jogadores = nomes.length;
        Cadeiras cadeiras = new Cadeiras(qtd_jogadores - 1);

        // cria os jogadores e inicia suas threads
        for(int i = 0; i < qtd_jogadores; i++){
            new Jogador(nomes[i], cadeiras).start();
        }
    }
}
