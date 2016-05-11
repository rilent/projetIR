package com.mygdx.spaceinvaders;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import arbres.Node;
import screens.GameScreen;
import screens.GameScreenPourRepetition;
import utils.Individu;

public class MyGdxspaceinvaders extends Game {
	
	private static final String TAG = MyGdxspaceinvaders.class.getName();
	private static int calculNbIterationPopulation = 0; //permets de compter le nombre de génération
	private boolean enModeRalenti = false;
	private int calculNbIndividu = 0; //permets de compter le nombre d'individu par génération
	private ArrayList<Individu> population = new ArrayList<Individu>();
	private ArrayList<Individu> prochainePopulation = new ArrayList<Individu>();
	private boolean premiereGeneration = true;
	private Node individuQuonRevoie = null;
	private ArrayList<Integer> moyenneScore = new ArrayList<Integer>();
	private ArrayList<Integer> historiqueDesDestruction = new ArrayList<Integer>();

	
	private int nbGenDepuisDerniereDstruction = 0;
	
	public void augmenteNbGenDepuisDerniereDestruction() {
		this.nbGenDepuisDerniereDstruction++;
	}
		
	
	public int getNombreDestructions() {
		return this.getHistoriqueDesDestruction().size();
	}
	
	public int getNbGenDepuisDerniereDstruction() {
		return nbGenDepuisDerniereDstruction;
	}
	public void setNbGenDepuisDerniereDstruction(int nbGenDepuisDerniereDstruction) {
		this.nbGenDepuisDerniereDstruction = nbGenDepuisDerniereDstruction;
	}
	


	
	@Override
	public void create () {

		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		setScreen(new GameScreenPourRepetition(this));
		
	}


	
	
	public static int getNumGenEnCours()
	{
		return calculNbIterationPopulation;
	}
	
	public ArrayList<Integer> getHistoriqueDesDestruction() {
		return historiqueDesDestruction;
	}






	public void setHistoriqueDesDestruction(
			ArrayList<Integer> historiqueDesDestruction) {
		this.historiqueDesDestruction = historiqueDesDestruction;
	}






	public ArrayList<Integer> getMoyenneScore() {
		return moyenneScore;
	}






	public void setMoyenneScore(ArrayList<Integer> moyenneScore) {
		this.moyenneScore = moyenneScore;
	}






	public Node meilleurDuTournoi()
	{
		
		//
		return null;
	}
	

	public Node getIndividuQuonRevoie() {
		return individuQuonRevoie;
	}






	public ArrayList<Individu> getProchainePopulation() {
		return prochainePopulation;
	}





	public void setProchainePopulation(ArrayList<Individu> prochainePopulation) {
		this.prochainePopulation = prochainePopulation;
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
