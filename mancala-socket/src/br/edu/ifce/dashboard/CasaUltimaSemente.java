package br.edu.ifce.dashboard;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CasaUltimaSemente {
	private Integer casa;
	private Long idTabuleiro;
	private List<Integer> mapaJogada;
}
