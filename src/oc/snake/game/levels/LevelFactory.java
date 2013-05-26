package oc.snake.game.levels;

public class LevelFactory {
	public static int numLevels() {
		return 6;
	}
	
	public static Level getLevel(int num) {
		switch (num) {
			case 1:
				return new Level1();
			case 2:
				return new Level2();
			default:
				return new Level1();
		}
	}
}
