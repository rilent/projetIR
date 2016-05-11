package screens;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.spaceinvaders.MyGdxspaceinvaders;

import arbres.BinaryTree;
import arbres.TreePrinter;
import controller.WorldControllerMano;
import model.JoueurAI;
import model.TankJoueur;
import model.World;
import utils.Constants;
import utils.Individu;
import view.WorldRenderer;

public class GameScreenPourRepetition extends AbstractGameScreen{
	
	MyGdxspaceinvaders gam;
	WorldRenderer worldRenderer;
	WorldControllerMano worldController;
	private World world;
	private OrthographicCamera camera;
	int compteur;
	
	
	
	public GameScreenPourRepetition(MyGdxspaceinvaders game) {
		super(game);
		this.gam =  game;
		
		//population.get(instance).getTree()
		
		if(gam.isPremiereGeneration())
		{
			world = new World(new JoueurAI(new TankJoueur(new Vector2(Constants.POSITION_DEPART_TANK_X,Constants.POSITION_DEPART_TANK_Y), new Vector2(1,0))),null);
		}
		else if(gam.isEnModeRalenti())
		{
			//System.out.println("Je suis dans screen pour repetition \n");
			TreePrinter.print(gam.getIndividuQuonRevoie());
			world = new World(new JoueurAI(new TankJoueur(new Vector2(Constants.POSITION_DEPART_TANK_X,Constants.POSITION_DEPART_TANK_Y), new Vector2(1,0))),gam.getIndividuQuonRevoie().copyNode());
		}
		else
		{
			world = new World(new JoueurAI(new TankJoueur(new Vector2(Constants.POSITION_DEPART_TANK_X,Constants.POSITION_DEPART_TANK_Y), new Vector2(1,0))),gam.getPopulation().get(gam.getCalculNbIndividu()).getTree());
		}

		worldRenderer = new WorldRenderer(world);
		worldController = new WorldControllerMano(world);
		camera = new OrthographicCamera();
	}	


	@Override
	public void render(float deltaTime) {
		this.compteur++;
		//System.out.println(compteur);
		//System.out.println(world.getTank().gethitBox().x);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		worldController.update(deltaTime);
		worldRenderer.render(deltaTime);
		world.setCpt_render(world.getCpt_render()+1);

		
		
		if(world.isPartieTermine() == true)
		{
			
			
			if(gam.isEnModeRalenti())
			{
				//System.out.println("apres avoir refait le ralenti, son score est de : "+world.getScorePartie());
				gam.setScreen(new EndingScreen(gam));
			}
			else
			{
			
				if(gam.isPremiereGeneration())
				{
					gam.getPopulation().add(new Individu(world.getScorePartie(),world.getTree().root)); //on ajoute l'individu
				}
				else
				{
					gam.getPopulation().get(gam.getCalculNbIndividu()).setScore(world.getScorePartie()); //on mets à jour son score
				}
	
				gam.setCalculNbIndividu(gam.getCalculNbIndividu()+1);			
				
				if(gam.getCalculNbIndividu() < Constants.NB_INDIVIDU_PAR_GEN)
				{
					gam.setScreen(new GameScreenPourRepetition(gam));
				}
				else
				{
					gam.setScreen(new EndingScreen(gam));
				}
			}
		
		}	
		
	}
	
	
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

	
	
}
