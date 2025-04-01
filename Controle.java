package Control;

import View.PintarBotoes;
import View.Tela;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Controle {
    private JButton[] sequenciaGerada;
    private int[] sequenciaNumerica;
    private int indiceCliqueAtual;
    private String[] ranking;
    private String jogador;
    private int pontuacao;
    private int rodada;
    private String dificuldade;
    private Tela telaPrincipal;
    private JButton[] listaBotoes;
    private JButton botaoIniciar;
    private boolean sequenciaAutomaticaIniciada;
    private boolean rodadaIniciada;
    private boolean jogoIniciado;
    int delay;
    
    public Controle(Tela telaPrincipal) {        
        this.telaPrincipal = telaPrincipal;
        this.listaBotoes = telaPrincipal.obterInstanciaBotoesAcao();
        this.botaoIniciar = telaPrincipal.obterInstaciaBotaoIniciar();
    }
    
    public void iniciarNovoJogo(String jogador, String dificuldade) {
        this.dificuldade = dificuldade;
        this.jogador = jogador;
        pontuacao = 0;
        rodada = 0;
        jogoIniciado = true;
        rodadaIniciada = false;
        sequenciaAutomaticaIniciada = false;
        
        switch (dificuldade) {
            case "Fácil": delay = 1000; break;
            case "Médio": delay = 700; break;
            case "Difícil": delay = 400; break;
        }
        
        ranking = new String[10];
        telaPrincipal.atualizarPontuacao(String.valueOf(pontuacao));
        telaPrincipal.atualizarRodada(String.valueOf(rodada));
        botaoIniciar.setEnabled(true);    
    }
    
    public void iniciarRodada() {
        rodada++;
        rodadaIniciada = true;
        sequenciaAutomaticaIniciada = true;
        botaoIniciar.setEnabled(false);
        
        sequenciaNumerica = gerarSequenciaNumerica(rodada + 1);
        sequenciaGerada = gerarSequenciaBotoes(rodada + 1);
        
        telaPrincipal.atualizarRodada(String.valueOf(rodada));
        pintarBotoesSequencia(sequenciaGerada);
    }
    
    public void acaoClick(java.awt.event.MouseEvent evt) {
        JButton botaoClicado = (JButton) evt.getSource();
        
        if (sequenciaAutomaticaIniciada || !rodadaIniciada || !jogoIniciado) {
            return;
        }
        
        PintarBotoes.piscarBotoes(botaoClicado, delay, () -> {
            if (validaSequencia(botaoClicado)) {
                sequenciaValida();
            } else {
                sequenciaInvalida();
            }
        });
    }
    
    private void pintarBotoesSequencia(JButton[] sequencia) {
        PintarBotoes.piscarBotoes(sequencia, delay, () -> callbackPintarBotoesSequencia());
    }
    
    private void callbackPintarBotoesSequencia() {
        sequenciaAutomaticaIniciada = false;
        indiceCliqueAtual = 0;
    }
    
    private boolean validaSequencia(JButton botaoClicado) {
        return botaoClicado.equals(sequenciaGerada[indiceCliqueAtual++]);
    }
    
    private void sequenciaValida() {
        if (indiceCliqueAtual == sequenciaGerada.length) {
            int pontosRodada = rodada * (dificuldade.equals("Fácil") ? 10 : dificuldade.equals("Médio") ? 20 : 30);
            pontuacao += pontosRodada;
            telaPrincipal.atualizarPontuacao(String.valueOf(pontuacao));
            
            rodadaIniciada = false;
            botaoIniciar.setEnabled(true);
            
            JOptionPane.showMessageDialog(null, "Correto! Próxima rodada?");
        }
    }
    
    private void sequenciaInvalida() {
        jogoIniciado = false;
        rodadaIniciada = false;
        
        String entradaRanking = jogador + ": " + pontuacao;
        for (int i = 0; i < ranking.length; i++) {
            if (ranking[i] == null) {
                ranking[i] = entradaRanking;
                break;
            }
        }
        telaPrincipal.atualizarRanking(ranking);
        
        botaoIniciar.setEnabled(true);
        JOptionPane.showMessageDialog(null, "Game Over! Pontuação final: " + pontuacao);
    }
    
    private JButton[] gerarSequenciaBotoes(int qtdSequencias) {
        sequenciaNumerica = gerarSequenciaNumerica(qtdSequencias);
        JButton[] sequencia = new JButton[qtdSequencias];
        for (int i = 0; i < qtdSequencias; i++) {
            sequencia[i] = listaBotoes[sequenciaNumerica[i]];
        }
        return sequencia;
    }
    
    private int[] gerarSequenciaNumerica(int qtdSequencias) {
        int[] sequencia = new int[qtdSequencias];
        for (int i = 0; i < qtdSequencias; i++) {
            sequencia[i] = (int) (Math.random() * 9);
        }
        return sequencia;
    }
}