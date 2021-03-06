package screens;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.spaceinvaders.MyGdxspaceinvaders;

import arbres.BinaryTree;
import controller.WorldControllerMano;
import model.JoueurAI;
import model.TankJoueur;
import model.World;
import utils.Constants;
import utils.Individu;
import view.WorldRenderer;

public class GameScreen extends AbstractGameScreen{
	
	MyGdxspaceinvaders gam;
	WorldRenderer worldRenderer;
	WorldControllerMano worldController;
	private World world;
	private OrthographicCamera camera;
	int compteur;
	
	
	
	public GameScreen(MyGdxspaceinvaders game) {
		super(game);
		this.gam =  game;
		//world = new World(new JoueurHumain(new TankJoueur(new Vector2(Constants.POSITION_DEPART_TANK_X,Constants.POSITION_DEPART_TANK_Y), new Vector2(1,0))));
		world = new World(new JoueurAI(new TankJoueur(new Vector2(Constants.POSITION_DEPART_TANK_X,Constants.POSITION_DEPART_TANK_Y), new Vector2(1,0))),null);
		
		//world = new World(new TankJoueur(new Vector2(Constants.POSITION_DEPART_TANK_X,Constants.POSITION_DEPART_TANK_Y), new Vector2(1,0)));
		
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

			gam.getPopulation().add(new Individu(world.getScorePartie(),world.getTree().root));
			gam.setCalculNbIndividu(gam.getCalculNbIndividu()+1);
			
			if(gam.getCalculNbIndividu() < 5)
			{
				gam.setScreen(new GameScreen(gam));
			}
			else
			{
				gam.setScreen(new EndingScreen(gam));
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
