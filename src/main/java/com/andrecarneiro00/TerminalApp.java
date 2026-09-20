package com.andrecarneiro00;

import com.andrecarneiro00.core.game.Game;
import com.andrecarneiro00.core.game.board.initializers.ClassicChessInitializer;
import com.andrecarneiro00.ui.terminal.TerminalUI;

public class TerminalApp {
    public static void main(String[] args) {
        Game game = new Game(8, new ClassicChessInitializer());
        TerminalUI ui = new TerminalUI();
        ui.start(game);
    }
}
