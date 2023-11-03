package com.backlight.crazycolor.ui.component.play;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class PlayViewModel extends ViewModel {


    public int score = 0;
    public int randomBoxIndex = 0;
    public  int originalColor = -1;
    public int lastRandIndex = 0;
    private Random random = new Random();

    public boolean isActive = true;
    public int getRandomIndex(){
        int index;
        do {
            index = random.nextInt(4);
        } while (index == lastRandIndex);

        lastRandIndex = index; // Update the last random index
        return index;
    }

    public String getScoreText(){
        return "Score : "+score;
    }

}
