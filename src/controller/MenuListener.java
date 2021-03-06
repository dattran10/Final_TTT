package controller;
import model.AVAMode;
import model.HVAMode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.AVADeathMatchMode;
import model.Player;
import view.AVAMenu;
import view.HVAMenu;
import view.MainMenu;
import static view.AVAMenu.deathMatchCheckBox;
import static view.AVAMenu.playerTwoCheckBox;

public class MenuListener implements ActionListener {

    char remoteBoardArr[] = new char[9];
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
       
        if (source == HVAMenu.mainMenuButton || source == AVAMenu.mainMenuButton) {
            System.out.println("main  menu");
            Main.gameWindow.displayMainMenu();
        } else if (source == MainMenu.hvaButton) {
            System.out.println("Clicked hva");
            Main.gameWindow.displayHVAMenu();
            Main.gameManager.setState(new HVAMode());
            Main.gameManager.board = HVAMenu.hvaBoard;
            HVAMenu.statusBar.setText("Click <Start Game> to begin");
            System.out.println("Set game mode to: " + Main.gameManager.myState.toString());
        } else if (source == MainMenu.avaButton) {
            System.out.println("Clicked ava");
            Main.gameWindow.displayAVAMenu();
            Main.gameManager.setState(new AVAMode());
            Main.gameManager.board = AVAMenu.avaBoard;
            Main.networkManager.requestData();
            System.out.println("Set game mode to: " + Main.gameManager.myState.toString());
            System.out.println("Player AI vs AI");
        } else if (source == HVAMenu.playAgainButton) {
            Main.gameManager.board.resetBoard();
            Main.gameManager.playerTurn = Player.X;
            HVAMenu.startHVAGameButton.setEnabled(true);
            HVAMenu.statusBar.setText("");
            HVAMenu.timeCountDown.setText("10 seconds (default)");
            Main.gameManager.timer.stop();
        } else if (source == HVAMenu.startHVAGameButton) {   
            HVAMenu.playAgainButton.setEnabled(false);
            System.out.println("start the game");
            HVAMenu.hvaBoard.clearBoard();
            HVAMenu.hvaBoard.enableBoard();
            HVAMenu.statusBar.setText("");            
            HVAMenu.readThresholdTime.setEditable(false);
            if (!HVAMenu.readThresholdTime.getText().equalsIgnoreCase("")) {
                if (Integer.parseInt(HVAMenu.readThresholdTime.getText()) != 0) {
                    Main.gameManager.thresholdTime = Integer.parseInt(HVAMenu.readThresholdTime.getText());
                }
            }
            Main.gameManager.myState.doAction(Main.gameManager);
            Main.gameManager.startTimer();
        } else if (source == AVAMenu.makeMoveButton) { 
            Main.gameManager.myState.doAction(Main.gameManager);
        } else if (source == deathMatchCheckBox) {
            Main.gameManager.isDeathMatch = (Main.gameManager.isDeathMatch) ? false : true;
            if (Main.gameManager.isDeathMatch) {
                Main.gameManager.setState(new AVADeathMatchMode());
                System.out.println("Set game mode to: " + Main.gameManager.myState.toString());
            } else {
                Main.gameManager.setState(new AVAMode());
                System.out.println("Set game mode to: " + Main.gameManager.myState.toString());
            }
            System.out.println("Death match: " + Main.gameManager.isDeathMatch);
        } else if (source == playerTwoCheckBox) {
            if (GameManager.mainPlayer.equalsIgnoreCase(Player.X)) {
                GameManager.mainPlayer = Player.O;
            } else {
                GameManager.mainPlayer = Player.X;
            }
            System.out.println("Player counter is set to: " + GameManager.mainPlayer);
        } else if (source == AVAMenu.resetBoardButton) {
            Main.networkManager.sendMove(99);
            Main.networkManager.requestData();
        }
        
    } // End actionPerformed()
    
    
} // End class MenuListener
