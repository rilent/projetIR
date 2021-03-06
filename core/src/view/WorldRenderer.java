package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.spaceinvaders.MyGdxspaceinvaders;

import model.Alien;
import model.Bloc;
import model.GameElement;
import model.Missile;
import model.Tank;
import model.World;
import utils.Constants;

public class WorldRenderer {

	private SpriteBatch sBatch;
	private World world;
	private Tank tank;
	private BitmapFont font;
	
	public WorldRenderer(World world)
	{
		this.world = world;
		this.tank = world.getTank();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		sBatch = new SpriteBatch();
		
	}
	
	public void render(float delta){	
        //createPacman();
		//Gdx.input.setInputProcessor(world.getListener());      
		sBatch.begin();
//		for(Alien alien : world.getListeAlien()){
//			if(alien.isActif())
//				{
//					sBatch.draw(alien.getTexture(),alien.getPosition().x,alien.getPosition().y);
//				}
//		}
//        
//		
//		
//		for(Bloc bloc : world.getListeBloc()){
//			if(!bloc.isDetruit())
//				{
//					sBatch.draw(bloc.getTexture(),bloc.getPosition().x,bloc.getPosition().y);
//				}
//		}
//		
//		
//		for(Missile missile : world.getListeMissile()){
//			sBatch.draw(missile.getTexture(),missile.getPosition().x,missile.getPosition().y);
//		}
//		
		
		for(GameElement ge : world.getListeGameElement())
		{
			sBatch.draw(ge.getTexture(),ge.getPosition().x,ge.getPosition().y);
		}
		
		//sBatch.draw(world.getTank().getTexture(),world.getTank().getPosition().x,world.getTank().getPosition().y);
		
        //update();
		
		/*for(GameElement element : world){
			renderElement(element);
		}
		*/
		
		
		renderGui();
		
		sBatch.end();
	}
	
	
	public void renderGui()
	{
		renderScore();
		int fps = Gdx.graphics.getFramesPerSecond();
		//System.out.println(fps);
		font.draw(sBatch,String.valueOf(fps),Constants.VIEWPORT_GUI_HEIGHT-100,Constants.VIEWPORT_GUI_HEIGHT-100);
		
		font.draw(sBatch,"Generation num : "+String.valueOf(MyGdxspaceinvaders.getNumGenEnCours()),Constants.VIEWPORT_GUI_HEIGHT-700,Constants.VIEWPORT_GUI_HEIGHT-100);
		
	}
	
	public void renderScore()
	{
		if(world.getScorePartie() < 100)
			font.draw(sBatch,String.valueOf(0),Constants.VIEWPORT_GUI_HEIGHT-50,Constants.VIEWPORT_GUI_HEIGHT-100);
			
		if(world.getScorePartie()>100)
		font.draw(sBatch,String.valueOf(world.getScorePartie()).subSequence(0,String.valueOf(world.getScorePartie()).length()-2),Constants.VIEWPORT_GUI_HEIGHT-50,Constants.VIEWPORT_GUI_HEIGHT-100);
		
		
		//font.draw(sBatch,String.valueOf(world.getScorePartie()),Constants.VIEWPORT_GUI_HEIGHT-50,Constants.VIEWPORT_GUI_HEIGHT-50);
		
	}
	
	
}
