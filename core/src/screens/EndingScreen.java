package screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.spaceinvaders.MyGdxspaceinvaders;


import arbres.BinaryTree;
import utils.Individu;

public class EndingScreen extends AbstractGameScreen{
		
		private int instance = 0; //permets de compter le nombre d'individu, on s'arrête à 50
		MyGdxspaceinvaders gam;
		ArrayList<Individu> population;
		BinaryTree leMeilleur; //le meilleur de la population en cours
		
		public EndingScreen (MyGdxspaceinvaders game, ArrayList<Individu> pop) {
			super(game);
			gam = game;
			this.population = pop;
			
		}	


		@Override
		public void render(float deltaTime) {

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(0, 0, 0, 0);


			if(Gdx.input.isTouched())
			{
				System.out.println("touché");
				Gdx.app.exit();
				Gdx.
				gam.setScreen(new GameScreen(gam,0,population));
			}
			
			
		}
		
		
		
		

		public int getInstance() {
			return instance;
		}


		public void setInstance(int instance) {
			this.instance = instance;
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
