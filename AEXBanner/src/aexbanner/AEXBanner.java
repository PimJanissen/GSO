/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aexbanner;

import business.bannercontroller.BannerController;
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
 */
public class AEXBanner extends Application
{

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 100;
    public static final int NANO_TICKS = 20000000;
    private static int TEXT_MOVEMENT_SPEED = 6;
    // FRAME_RATE = 1000000000/NANO_TICKS = 50;

    private Text text;
    private double textLength;
    private double textPosition;
    private BannerController controller;
    private AnimationTimer animationTimer;

    @Override
    public void start(Stage primaryStage)
    {

        controller = new BannerController(this);

        Font font = new Font("Arial", HEIGHT);
        text = new Text();
        text.setFont(font);
        text.setFill(Color.BLACK);

        Pane root = new Pane();
        root.getChildren().add(text);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key)
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
        });

        primaryStage.setTitle("AEX banner");
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
        controller.stop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
