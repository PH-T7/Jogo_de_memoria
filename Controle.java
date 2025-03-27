
package Control;

public class Controle {
        
    private String jogador;
    private int pontuacao;
    private int rodada;
    private boolean iniciado;
    private String dificuldade;
    
    public Controle(String jogador, String dificuldade) {
        this.jogador = jogador;
        this.dificuldade = dificuldade;
        pontuacao=0;
        rodada=0;
        iniciado=false;
    }
    
    private  int[] gerarSequencia(int qtdSequencias){
        //O metodo irá gerar um vetor de int com a o tamanho igual
        //a qtdSequencia
        return new int[]{1,2,3,4,5};
    }
    
    
    
    
}
