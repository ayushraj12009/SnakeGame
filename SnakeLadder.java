package com.example.snakeladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SnakeLadder extends Application {


        // variable created
    public static final int tileSize = 40, width = 10, height = 10,

            buttonLine = height*tileSize + 50, infoLine = height*tileSize+20;




    Player playerFirst,playerSecond;

    boolean firstPlayerTurn = true , gameStart =  false;


    int diceValue;

    private Pane createContent() throws FileNotFoundException {
        Pane root = new Pane();
        root.setPrefSize(width*tileSize, height*tileSize+100);

        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(i*tileSize);
                tile.setTranslateY(j*tileSize);
                root.getChildren().addAll(tile);
            }
        }

        // putting image on the board
        FileInputStream inputstream = new FileInputStream("C:\\Users\\Rohit\\IdeaProjects\\SnakeLadder\\src\\main\\java\\com\\example\\snakeladder\\img_3.png");
        Image img = new Image(inputstream);
        ImageView boarImage = new ImageView();
        boarImage.setFitWidth(width*tileSize);
        boarImage.setFitHeight(height*tileSize);
        boarImage.setImage(img);

       // root.getChildren().addAll(boarImage);


        //Buttons and information

        Button startButton = new Button("Start");
        startButton.setTranslateX(180);
        startButton.setTranslateY(buttonLine);

        // Player One Button
        Button playerOneButton = new Button("Player One");
        playerOneButton.setTranslateX(10);
        playerOneButton.setTranslateY(buttonLine);

        // Player Two Button
        Button playerTwoButton = new Button("Player Two");
        playerTwoButton.setTranslateX(300);
        playerTwoButton.setTranslateY(buttonLine);


        // diceLabel
        Label diceLabel = new Label("Start the Game");
        diceLabel.setTranslateX(180);
        diceLabel.setTranslateY(infoLine);


        // Player

        playerFirst = new Player("Ayush" , Color.WHITE, tileSize/2);
        playerSecond = new Player("Raj", Color.BLACK, tileSize/2-5);


        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(firstPlayerTurn){
                        diceValue = rollDice();
                        diceLabel.setText("Dice :" + diceValue);
                        playerFirst.movePlayer(diceValue);

                        firstPlayerTurn = !firstPlayerTurn;

                        if(playerFirst.checkWinner()){
                            diceLabel.setText("Winner is " + playerFirst.getName());
                            startButton.setText("Restart");
                            startButton.setDisable(false);
                            firstPlayerTurn=true;
                            gameStart = false;
                        }
                    }
                }
            }
        });


        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(!firstPlayerTurn){
                        diceValue = rollDice();
                        diceLabel.setText("Dice :" + diceValue);
                        playerSecond.movePlayer(diceValue);

                        firstPlayerTurn = !firstPlayerTurn;

                        if(playerSecond.checkWinner()){
                            diceLabel.setText("Winner is " + playerSecond.getName());
                            startButton.setText("Restart");
                            startButton.setDisable(false);
                            firstPlayerTurn=true;

                            gameStart = false;
                        }
                    }
                }
            }
        });


        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStart = true;
                startButton.setDisable(true);
                playerFirst.setStart();
                playerSecond.setStart();
            }
        });




        root.getChildren().addAll(boarImage,startButton, playerOneButton,
                playerTwoButton,diceLabel, playerFirst.getCoin(),playerSecond.getCoin()
        );


        return root;

    }


    private int rollDice(){
        return (int)(Math.random()*6+1);
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake & Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}