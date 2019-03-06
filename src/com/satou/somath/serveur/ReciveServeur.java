package com.satou.somath.serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import fr.univangers.pacman.controller.PacmanGameController;
import fr.univangers.pacman.model.Maze;
import fr.univangers.pacman.model.PacmanGame;
import fr.univangers.pacman.model.PacmanGame.Mode;
import fr.univangers.pacman.model.PacmanGame.StrategyGhost;
import fr.univangers.pacman.model.PacmanGame.StrategyPacman;
import fr.univangers.pacman.model.PositionAgent.Dir;
import fr.univangers.pacman.view.ViewCommande;
import fr.univangers.pacman.view.ViewGame;

public class ReciveServeur implements Runnable {
    String               msg;
    final Socket         so;
    final BufferedReader entree;
    final PrintWriter    sortie;
    // le jeux panmangame a terme mais pacmanwiew en attendant

    public ReciveServeur( Socket so2, BufferedReader entree2, PrintWriter sortie2 ) {
        // TODO Auto-generated constructor stub
        this.so = so2;
        this.entree = entree2;
        this.sortie = sortie2;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        File directory = new File( "res/layouts" );
        PacmanGame pacmanGame;
        try {
            pacmanGame = new PacmanGame( 200, new Maze( directory.listFiles()[0].toString() ),
                    StrategyPacman.BASIC, StrategyGhost.TRACKING,
                    Mode.ONEPLAYER );

            while ( so.isConnected() ) {
                msg = entree.readLine();
                // methode pour normaliser msg
                StringTokenizer st = new StringTokenizer( msg );
                // debug
                if ( msg != null ) {
                    System.out.println( msg );
                }
                switch ( st.nextToken() ) {
                /**
                 * refaire le swich pour les message client
                 * 
                 */
                case "game":
                    /*
                     * pacmanGame = new PacmanGame( 200, new Maze(
                     * directory.listFiles()[0].toString() ),
                     * StrategyPacman.BASIC, StrategyGhost.TRACKING,
                     * Mode.ONEPLAYER );
                     */

                    PacmanGameController pacmanGameController = new PacmanGameController( pacmanGame, sortie );
                    /**
                     * rajout méthode d'envoie des donnée d'initialisation coté
                     * serveur sendInitCS( getNbTurn(),
                     * ((File)listMaze.getSelectedItem() ).getPath() ,
                     * listStrategyGhost.getSelectedIndex() ,
                     * listStrategyPacman.getSelectedIndex() ,
                     * listMode.getSelectedIndex() )
                     */
                    ViewCommande viewCommande = new ViewCommande( pacmanGame );
                    viewCommande.setGameController( pacmanGameController );
                    new ViewGame( pacmanGame, pacmanGameController,
                            new Maze( directory.listFiles()[0].toString() ), "PacmanServeur" );
                    break;

                case "gauche":
                    pacmanGame.movePacmanPlayer1( Dir.WEST );
                    break;

                case "droite":
                    pacmanGame.movePacmanPlayer1( Dir.EAST );
                    break;

                case "haut":
                    pacmanGame.movePacmanPlayer1( Dir.NORTH );
                    break;

                case "bas":
                    pacmanGame.movePacmanPlayer1( Dir.SOUTH );
                    break;

                default:
                    System.out.println( "le client m'as envoyer un message mais je ne le comprends pas : " + msg );
                    break;

                }
            }
        } catch ( Exception e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
