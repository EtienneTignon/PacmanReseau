package fr.univangers.pacman.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.satou.somath.definition.Winner;

import fr.univangers.pacman.controller.GameController;
import fr.univangers.pacman.model.Game;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGameClient;
import fr.univangers.pacman.model.PacmanGameServeur;
import fr.univangers.pacman.model.PositionAgent.Dir;

/**
 * Classe ViewGame permet d'afficher le jeu dans une interface graphique
 */

public class ViewGame extends JFrame implements View, KeyListener {

    private static final long serialVersionUID = -2187636929128362263L;
    private Game              game;
    private GameController    gameController;
    private JPanel            panelInfo;
    private JLabel            labelCurrentTurn;
    private JLabel            labelScore;
    private JLabel            labelLife;
    private PanelPacmanGame   panelPacmanGame;
    private boolean           reset;

    public ViewGame( Game game, GameController gameController, Maze maze, String title ) {
        super();
        this.game = game;
        this.game.addView( this );
        this.gameController = gameController;
        this.reset = false;

        addKeyListener( this );

        setTitle( title );
        setSize( new Dimension( maze.getSizeX() * 20, maze.getSizeY() * 20 + 80 ) );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );

        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2 + 150;
        setLocation( dx, dy );

        panelInfo = new JPanel();
        panelInfo.setLayout( new GridLayout( 2, 1 ) );

        labelCurrentTurn = new JLabel( "Tour " + game.nbTurn(), SwingConstants.CENTER );
        panelInfo.add( labelCurrentTurn );
        if ( game instanceof PacmanGame ) {
            labelLife = new JLabel( "Vie " + ( (PacmanGame) game ).getNbLifePacmans(), SwingConstants.CENTER );
            panelInfo.add( labelLife );
            labelScore = new JLabel( "Score " + ( (PacmanGame) game ).score(), SwingConstants.CENTER );
            panelInfo.add( labelScore );
        }

        if ( game instanceof PacmanGameClient ) {
            labelLife = new JLabel( "Vie " + ( (PacmanGameClient) game ).getNbLifePacmans(), SwingConstants.CENTER );
            panelInfo.add( labelLife );
            labelScore = new JLabel( "Score " + ( (PacmanGameClient) game ).score(), SwingConstants.CENTER );
            panelInfo.add( labelScore );
        }

        if ( game instanceof PacmanGameServeur ) {
            labelLife = new JLabel( "Vie " + ( (PacmanGameServeur) game ).getNbLifePacmans(), SwingConstants.CENTER );
            panelInfo.add( labelLife );
            labelScore = new JLabel( "Score " + ( (PacmanGameServeur) game ).score(), SwingConstants.CENTER );
            panelInfo.add( labelScore );
        }
        add( panelInfo, BorderLayout.NORTH );

        panelPacmanGame = new PanelPacmanGame( maze );
        add( panelPacmanGame, BorderLayout.CENTER );

        setVisible( true );
    }

    @Override
    public void update() {

        labelCurrentTurn.setText( "Tour " + game.nbTurn() );

        if ( game instanceof PacmanGame ) {
            labelLife.setText( "Vie " + ( (PacmanGame) game ).getNbLifePacmans() );
            labelScore.setText( "Score " + ( (PacmanGame) game ).score() );
            panelPacmanGame.setGhostsScarred( ( (PacmanGame) game ).ghostsScarred() );
            panelPacmanGame.setPacmans_pos( ( (PacmanGame) game ).positionPacmans() );
            panelPacmanGame.setGhosts_pos( ( (PacmanGame) game ).positionGhosts() );

            Winner winner = ( (PacmanGame) game ).winner();
            if ( !reset && winner != Winner.NOWINNER ) {
                String print = "";
                if ( winner == Winner.PACMANWINNER ) {
                    print = "Les pacmans ont gagné";
                } else if ( winner == Winner.GHOSTWINNER ) {
                    print = "Les fantômes ont gagné";
                }
                int answer = JOptionPane.showConfirmDialog( this,
                        print + "\nRelancer ?", "Fin de partie", JOptionPane.YES_NO_OPTION );
                if ( answer == JOptionPane.YES_OPTION ) {
                    reset = true;
                    gameController.restart();
                } else {
                    System.exit( NORMAL );
                }
            } else
                reset = false;
        }

        if ( game instanceof PacmanGameServeur ) {
            labelLife.setText( "Vie " + ( (PacmanGameServeur) game ).getNbLifePacmans() );
            labelScore.setText( "Score " + ( (PacmanGameServeur) game ).score() );
            panelPacmanGame.setGhostsScarred( ( (PacmanGameServeur) game ).ghostsScarred() );
            panelPacmanGame.setPacmans_pos( ( (PacmanGameServeur) game ).positionPacmans() );
            panelPacmanGame.setGhosts_pos( ( (PacmanGameServeur) game ).positionGhosts() );

            Winner winner = ( (PacmanGameServeur) game ).winner();
            if ( !reset && winner != Winner.NOWINNER ) {
                String print = "";
                if ( winner == Winner.PACMANWINNER ) {
                    print = "Les pacmans ont gagné";
                } else if ( winner == Winner.GHOSTWINNER ) {
                    print = "Les fantômes ont gagné";
                }
                int answer = JOptionPane.showConfirmDialog( this,
                        print + "\nRelancer ?", "Fin de partie", JOptionPane.YES_NO_OPTION );
                if ( answer == JOptionPane.YES_OPTION ) {
                    reset = true;
                    gameController.restart();
                } else {
                    System.exit( NORMAL );
                }
            } else
                reset = false;
        }

        if ( game instanceof PacmanGameClient ) {
            labelLife.setText( "Vie " + ( (PacmanGameClient) game ).getNbLifePacmans() );
            labelScore.setText( "Score " + ( (PacmanGameClient) game ).score() );
            panelPacmanGame.setGhostsScarred( ( (PacmanGameClient) game ).ghostsScarred() );
            panelPacmanGame.setPacmans_pos( ( (PacmanGameClient) game ).positionPacmans() );
            panelPacmanGame.setGhosts_pos( ( (PacmanGameClient) game ).positionGhosts() );

            Winner winner = ( (PacmanGameClient) game ).winner();
            if ( !reset && winner != Winner.NOWINNER ) {
                String print = "";
                if ( winner == Winner.PACMANWINNER ) {
                    print = "Les pacmans ont gagné";
                } else if ( winner == Winner.GHOSTWINNER ) {
                    print = "Les fantômes ont gagné";
                }
                int answer = JOptionPane.showConfirmDialog( this,
                        print + "\nRelancer ?", "Fin de partie", JOptionPane.YES_NO_OPTION );
                if ( answer == JOptionPane.YES_OPTION ) {
                    reset = true;
                    gameController.restart();
                } else {
                    System.exit( NORMAL );
                }
            } else
                reset = false;
        }
        panelPacmanGame.repaint();
    }

    @Override
    public void keyPressed( KeyEvent keyEvent ) {
        int code = keyEvent.getKeyCode();
        switch ( code ) {
        case KeyEvent.VK_LEFT:
            gameController.send( "gauche" );
            // gameController.movePlayer1( Dir.WEST );
            break;
        case KeyEvent.VK_UP:
            gameController.send( "haut" );
            // gameController.movePlayer1( Dir.NORTH );
            break;
        case KeyEvent.VK_DOWN:
            gameController.send( "bas" );
            // gameController.movePlayer1( Dir.SOUTH );
            break;
        case KeyEvent.VK_RIGHT:
            gameController.send( "droite" );
            // gameController.movePlayer1( Dir.EAST );
            break;
        case KeyEvent.VK_Q:
            gameController.movePlayer2( Dir.WEST );
            break;
        case KeyEvent.VK_Z:
            gameController.movePlayer2( Dir.NORTH );
            break;
        case KeyEvent.VK_S:
            gameController.movePlayer2( Dir.SOUTH );
            break;
        case KeyEvent.VK_D:
            gameController.movePlayer2( Dir.EAST );
            break;
        case KeyEvent.VK_P:
            gameController.pause();
            break;
        default:
            break;
        }

    }

    @Override
    public void keyReleased( KeyEvent arg0 ) {

    }

    @Override
    public void keyTyped( KeyEvent arg0 ) {

    }

}
