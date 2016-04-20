package com.mygdx.spaceinvaders;

import java.util.ArrayList;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import arbres.Node;
import screens.GameScreen;
import screens.GameScreenPourRepetition;
import utils.Individu;

public class MyGdxspaceinvaders extends Game {
	
	private static final String TAG = MyGdxspaceinvaders.class.getName();
	private int calculNbIterationPopulation = 0; //permets de compter le nombre de génération
	private boolean enModeRalenti = false;
	private int calculNbIndividu = 0; //permets de compter le nombre d'individu par génération
	private ArrayList<Individu> population = new ArrayList<Individu>();
	private boolean premiereGeneration = true;
	private Node individuQuonRevoie = null;
	@Override
	public void create () {

		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		setScreen(new GameScreenPourRepetition(this));
		
	}


	
	
	

	public Node getIndividuQuonRevoie() {
		return individuQuonRevoie;
	}






	public void setIndividuQuonRevoie(Node individuQuonRevoie) {
		this.individuQuonRevoie = individuQuonRevoie;
	}






	public boolean isPremiereGeneration() {
		return premiereGeneration;
	}




	public void setPremiereGeneration(boolean premiereGeneration) {
		this.premiereGeneration = premiereGeneration;
	}



	public int getCalculNbIterationPopulation() {
		return calculNbIterationPopulation;
	}



	public void setCalculNbIterationPopulation(int calculNbIterationPopulation) {
		this.calculNbIterationPopulation = calculNbIterationPopulation;
	}



	public int getCalculNbIndividu() {
		return calculNbIndividu;
	}



	public void setCalculNbIndividu(int calculNbIndividu) {
		this.calculNbIndividu = calculNbIndividu;
	}



	public boolean isEnModeRalenti() {
		return enModeRalenti;
	}

	public void setEnModeRalenti(boolean enModeRalenti) {
		this.enModeRalenti = enModeRalenti;
	}

	public ArrayList<Individu> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<Individu> population) {
		this.population = population;
	}
	
	
	
	

}
