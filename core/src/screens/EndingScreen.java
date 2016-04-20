package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.spaceinvaders.MyGdxspaceinvaders;

import arbres.BinaryTree;
import arbres.Node;
import utils.Individu;

public class EndingScreen extends AbstractGameScreen{
		
		MyGdxspaceinvaders gam;
		Individu leMeilleurDeLaGenEnCours = null; //le meilleur de la population en cours
		private SpriteBatch sBatch;
		private BitmapFont font;
		Skin skin;
	    Stage stage;
	    
		public EndingScreen (MyGdxspaceinvaders game) {
			super(game);
			gam = game;
			sBatch = new SpriteBatch();
			font = new BitmapFont();
			font.setColor(Color.WHITE);
            stage=new Stage();
            
            Gdx.input.setInputProcessor(stage);
            

            //trouve le meilleur de la génération actuelle
            trouveLeMeilleur();
            
            
            skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));
            Table table = new Table();
            table.setSize(800,800);
            
            final TextButton revoir = new TextButton("Revoir le meileur", skin);
            table.add(revoir).width(200).height(50);
            table.row();
            
            final TextButton prochainepop = new TextButton("Lancer la population suivante", skin);
            table.add(prochainepop).width(200).height(50);
            
            stage.addActor(table);
            
            //on est sure d avoir fait au moins une generation si on arrive ici
            gam.setPremiereGeneration(false);
            
            revoir.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                revoir.addAction(Actions.fadeOut(0.7f));
                System.out.println("ET CEST LE RALENTI DU MEILLEUR!!");
				Gdx.graphics.setVSync(true);
				gam.setEnModeRalenti(true);
				gam.setIndividuQuonRevoie(leMeilleurDeLaGenEnCours.getTree());
				gam.setScreen(new GameScreenPourRepetition(gam));
                }
           });
            
            
            prochainepop.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                prochainepop.addAction(Actions.fadeOut(0.7f));
                System.out.println("POPULATION SUIVANTE!!");
                if(!gam.isEnModeRalenti()) //si on ne vient pas du mode ralenti
                {
                	gam.setCalculNbIterationPopulation(gam.getCalculNbIterationPopulation() + 1); //alors on tient les comptes dans les générations
                }
                gam.setEnModeRalenti(false); //on vire le mode ralenti
                gam.setCalculNbIndividu(0); //on reset le compteur des individus
                Gdx.graphics.setVSync(false);
				gam.setScreen(new GameScreenPourRepetition(gam));
                }
           });
            
		}	


		@Override
		public void render(float deltaTime) {

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(0, 0, 0, 0);
			
			
			sBatch.begin();
			
		    font.draw(sBatch,  "Nous en sommes à la génération numéro : " + gam.getCalculNbIterationPopulation(), 200,650);
		    font.draw(sBatch,  "Le meilleur score de cette génération est : " + leMeilleurDeLaGenEnCours.getScore(), 200,600);
			font.draw(sBatch,  "Les scores obtenus sont : ", 300, 500);
			for (int i = 0; i < gam.getPopulation().size(); i++) {
				if(gam.getPopulation().get(i).getScore() < 100)
					font.draw(sBatch,""+i+" : "+String.valueOf(0),50 + i,400-40*i);
					
				if(gam.getPopulation().get(i).getScore() >100)
				font.draw(sBatch,""+i+" : "+String.valueOf(gam.getPopulation().get(i).getScore()).subSequence(0,String.valueOf(gam.getPopulation().get(i).getScore()).length()-2),50 + i,400-40*i);
			}
			sBatch.end();
			

		      // let the stage act and draw
		      stage.act(deltaTime);
		      stage.draw();
			
			
		}
		
		
		
		
		public void trouveLeMeilleur()
		{
		    //on trouve le meilleur individu
			leMeilleurDeLaGenEnCours = new Individu(gam.getPopulation().get(0).getScore(), gam.getPopulation().get(0).getTree());
		    for (int i = 0; i < gam.getPopulation().size(); i++) {
		    	if(gam.getPopulation().get(i).getScore() > leMeilleurDeLaGenEnCours.getScore())
		    	{
		    		leMeilleurDeLaGenEnCours = new Individu(gam.getPopulation().get(i).getScore(), gam.getPopulation().get(i).getTree());
		    	}
				
			}
		}


		public Individu getLeMeilleurDeLaGenEnCours() {
			return leMeilleurDeLaGenEnCours;
		}


		public void setLeMeilleurDeLaGenEnCours(Individu leMeilleurDeLaGenEnCours) {
			this.leMeilleurDeLaGenEnCours = leMeilleurDeLaGenEnCours;
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
