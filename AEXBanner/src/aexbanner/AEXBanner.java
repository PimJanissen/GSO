/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner;

import business.bannercontroller.BannerController;
import business.effectenbeurs.IEffectenbeurs;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author gebruiker-pc
 * @author Jeroen Janssen
 */
public class AEXBanner extends Application
{

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 100;
    public static final int NANO_TICKS = 20000000;
    private static int TEXT_MOVEMENT_SPEED = 6;
    // FRAME_RATE = 1000000000/NANO_TICKS = 50;

    // Set binding name for student administration
    private static final String BINDINGNAME = "MockEffectenbeurs";
    private static String ipAddress = null;
    private static int port = -1;

    // References to registry and student administration
    private Registry registry;
    public IEffectenbeurs iEffectenbeurs;

    private Text text;
    private double textLength;
    private double textPosition;
    private BannerController controller;
    private AnimationTimer animationTimer;

    @Override
    public void start(Stage primaryStage)
    {
        this.iEffectenbeurs = this.getEffectenbeurs();

        if (this.iEffectenbeurs != null)
        {
            try
            {
                controller = new BannerController(this);
            }
            catch (RemoteException ex)
            {
                Logger.getLogger(AEXBanner.class.getName()).log(Level.SEVERE, null, ex);
            }

            Font font = new Font("Arial", HEIGHT);
            text = new Text();

            text.setFont(font);

            text.setFill(Color.BLACK);

            Pane root = new Pane();

            root.getChildren()
                    .add(text);
            Scene scene = new Scene(root, WIDTH, HEIGHT);

            scene.addEventHandler(KeyEvent.KEY_PRESSED,
                    (key)
                    -> 
                    {
                        if (key.getCode() == KeyCode.LEFT)
                        {
                            if (TEXT_MOVEMENT_SPEED < 15)
                            {
                                TEXT_MOVEMENT_SPEED++;
                            }
                        }
                        if (key.getCode() == KeyCode.RIGHT)
                        {
                            if (TEXT_MOVEMENT_SPEED > 0)
                            {
                                TEXT_MOVEMENT_SPEED--;
                            }
                        }
            }
            );

            primaryStage.setTitle(
                    "AEX banner");
            primaryStage.setScene(scene);

            primaryStage.show();

            primaryStage.toFront();

            // Start animation: text moves from right to left
            animationTimer = new AnimationTimer()
            {
                private long prevUpdate;

                @Override
                public void handle(long now)
                {
                    long lag = now - prevUpdate;
                    if (lag >= NANO_TICKS)
                    {
                        // calculate new location of text

                        if (textPosition > -1 * text.getLayoutBounds().getWidth())
                        {
                            textPosition -= TEXT_MOVEMENT_SPEED;
                        }
                        else
                        {
                            textPosition = WIDTH;
                        }

                        text.relocate(textPosition, 0);
                        prevUpdate = now;
                    }
                }

                @Override
                public void start()
                {
                    prevUpdate = System.nanoTime();
                    textPosition = WIDTH;
                    text.relocate(textPosition, 0);

                    setKoersen("Nothing to display");
                    super.start();
                }
            };

            animationTimer.start();
        }
    }

    public void setKoersen(String koersen)
    {
        try
        {
            text.setText(koersen);
            textLength = text.getLayoutBounds().getWidth();
        }
        catch (Exception ex)
        {
        }
    }

    @Override
    public void stop()
    {
        animationTimer.stop();
    }

    private IEffectenbeurs getEffectenbeurs()
    {
        try
        {
            registry = LocateRegistry.getRegistry(ipAddress, port);

            if (registry != null)
            {
                return (IEffectenbeurs) registry.lookup(BINDINGNAME);
            }
        }
        catch (RemoteException | NotBoundException ex)
        {
            Logger.getLogger(AEXBanner.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {

        // Get ip address of server
        Scanner input = new Scanner(System.in);
        System.out.print("Client: Enter IP address of server: ");
        ipAddress = input.nextLine();

        // Get port number
        System.out.print("Client: Enter port number: ");
        port = input.nextInt();

        launch(args);
    }

}
