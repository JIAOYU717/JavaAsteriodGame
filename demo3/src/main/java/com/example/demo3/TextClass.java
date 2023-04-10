package com.example.demo3;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class TextClass extends Text {

//    public enum EventType {
//        PLAYER_DESTROYED,

//        SMALL_ASTEROID_DESTROYED,
//        LARGE_ASTEROID_DESTROYED,
//        MEDIUM_ASTEROID_DESTROYED,
//
//        ALIEN_SHIP_DESTROYED,
//        GAIN_EXTRA_LIFE,
//        LEVEL_COMPLETED
//    }


    int x;
    int y;
    int size;

//    private AtomicInteger lives = new AtomicInteger(3);
//    private AtomicInteger score;
//    private AtomicInteger gameLevel;

    public Text mytext = new Text();


    public TextClass(String textString, int x, int y, Color fillColor, int size) {
        this.x = x;
        this.y = y;
        this.mytext.setText(textString);
        this.size = size;
        this.mytext.setFill(fillColor);
        this.mytext.setX(this.x);
        this.mytext.setY(this.y);
        this.mytext.setFont(new Font(size));


        // Initialize the score and gameLevel here
        score = new AtomicInteger(0);
        gameLevel = new AtomicInteger(1);

    }


    public void SetText(String newTextString) {
        this.mytext.setText(newTextString);
    }

}


//    public void update() {
//
//        scoreText.SetText("Score: " + this.score);
//        liveText.SetText("lives: " + this.lives);
//        level.SetText("Level " + this.gameLevel);
//    }
//}




//    public void onGameEvent(EventType event) {
//        switch (event) {
//            case PLAYER_DESTROYED:
//                lives.decrementAndGet();
//                break;
//            case SMALL_ASTEROID_DESTROYED:
//                score.addAndGet(1000);
//                break;
//            case LARGE_ASTEROID_DESTROYED:
//                score.addAndGet(2000);
//                break;
//            case MEDIUM_ASTEROID_DESTROYED:
//                score.addAndGet(1500);
//                break;
//            case ALIEN_SHIP_DESTROYED:
//                score.addAndGet(5000);
//                break;
//            case GAIN_EXTRA_LIFE:
//                lives.incrementAndGet();
//                score.addAndGet(-10000);
//                break;
//            case LEVEL_COMPLETED:
//                gameLevel.incrementAndGet();
//                break;
//            default:
//                break;
//        }
//        update();
//    }
//}