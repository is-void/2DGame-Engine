package com.game.display.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;

import com.game.Assets;
import com.game.Game;
import com.game.GameState;
import com.game.display.menu.Menu;
import com.game.display.ui.Bar.BarType;
import com.game.sprites.Animator;

public class UIManager 
{
	Bar loadingBar;
	Game game;
	
	Menu startMenu;
	
	Button startButton;
	Button titleButton;
	
	Menu pauseMenu;
	Menu loadingMenu;
	Menu gameMenu;
	
	
	public boolean isLoaded = false;
	enum UIState
	{
		LOADING_UI,
		START_MENU,
		PAUSE_MENU,
		GAME_UI
	}
	UIState state;
	
	HashMap<UIState, Menu> uiMode = new HashMap<UIState, Menu>();
	
	public UIManager(Game game)
	{
		this.game = game;
		
		try {
			startButton = new Button(new Animator(Assets.startButton), this, Game.WIDTH/2 - 5 * Assets.startButton.getWidth(), Game.HEIGHT/2 - 5 * Assets.startButton.getHeight() + 200 , Assets.startButton.getWidth() * 10, Assets.startButton.getHeight() * 10, GameState.class.getMethod("setStateToRunning", UIWidget.class));
			startButton.animator.scale = 10;
			titleButton = new Button(new Animator(Assets.titleButton), this, Game.WIDTH/2 - 1 * Assets.titleButton.getWidth(), Game.HEIGHT/2 - 1 * Assets.titleButton.getHeight() - 200 , Assets.titleButton.getWidth() * 2, Assets.titleButton.getHeight() * 2, null);
			titleButton.animator.scale = 2;
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UIElement[] startMenuElements = {titleButton, startButton};
		startMenu = new Menu(startMenuElements);
		loadingBar = new Bar(game, Game.WIDTH/2 - 60, Game.HEIGHT/2 - 90, 100, 30, BarType.LOADING);
		state = UIState.LOADING_UI;
		isLoaded = true;
	}
	
	public void checkState()
	{
		switch(game.state)
		{
			case LOADING:
				state = UIState.LOADING_UI;
				break;
			case PAUSED:
				state = UIState.PAUSE_MENU;
				break;
			case RUNNING:
				state = UIState.GAME_UI;
				break;
			case START:
				state = UIState.START_MENU;
				break;
			default:
				break;
		
		}
	}
	
	public void update()
	{
		switch(state)
		{
			case GAME_UI:
				break;
			case LOADING_UI:
				loadingBar.update();
				break;
			case PAUSE_MENU:
				break;
			case START_MENU:
				startMenu.updateMenu();
				break;
			default:
				break;
			
		}
	}
	
	public void render(Graphics g)
	{
		
		switch(state)
		{
		case GAME_UI:
			break;
		case LOADING_UI:
			g.setColor(Color.black);
			g.drawString("Loading" , Game.WIDTH/2 - 30, Game.HEIGHT/2 -20);
			loadingBar.render(g);
			
			break;
		case PAUSE_MENU:
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			g.setFont(new Font("TimesRoman", Font.BOLD, 50)); 
			g.setColor(Color.black);
			g.drawString("PAUSED" , Game.WIDTH/2 - 100, Game.HEIGHT/2 -200);
			break;
		case START_MENU:
			startMenu.displayMenu(g);
			break;
		default:
			break;
			
		}
	}
}
