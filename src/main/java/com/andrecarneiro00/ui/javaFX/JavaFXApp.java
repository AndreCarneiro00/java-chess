package com.andrecarneiro00.ui.javaFX;

import com.andrecarneiro00.core.game.Game;
import com.andrecarneiro00.core.game.board.initializers.ClassicChessInitializer;
import com.andrecarneiro00.ui.javaFX.controller.GameController;
import com.andrecarneiro00.ui.javaFX.view.BoardView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = new Game(8, new ClassicChessInitializer());

        BoardView boardView = new BoardView();
        GameController gameController = new GameController(game, boardView);

        Scene scene = new Scene(gameController.initializeView(), 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
