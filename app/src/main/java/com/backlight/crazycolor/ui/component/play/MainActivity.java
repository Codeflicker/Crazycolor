package com.backlight.crazycolor.ui.component.play;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.backlight.crazycolor.R;
import com.backlight.crazycolor.databinding.ActivityMainBinding;
import com.backlight.crazycolor.ui.base.BaseActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private Handler handler;
    private PlayViewModel viewModel;
    private MaterialButton[] boxes;
    private int gray;
    private int[] boxColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initialize() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handler = new Handler();
        viewModel = new ViewModelProvider(MainActivity.this).get(PlayViewModel.class);
        boxes = new MaterialButton[]{
                binding.maButtonOrangeId,
                binding.maButtonBlueId,
                binding.maButtonYellowId,
                binding.maButtonGreenId
        };
        boxColor = new int[]{
                ContextCompat.getColor(this, R.color.colorOrange),
                ContextCompat.getColor(this, R.color.colorBlue),
                ContextCompat.getColor(this, R.color.colorYellow),
                ContextCompat.getColor(this, R.color.colorGreen),
                gray = ContextCompat.getColor(this, R.color.colorGrey)
        };
    }

    @Override
    protected void registerListener() {
        for( MaterialButton button : boxes){
            button.setOnClickListener(v->{
                onBoxClick(button);
            });
        }
    }

    @Override
    protected void sequence() {
        startGame();
    }


    private void startGame() {
        // Initialize the game and start the timer to change box colors.
        binding.maButtonOrangeId.setTag(boxColor[0]);
        binding.maButtonBlueId.setTag(boxColor[1]);
        binding.maButtonYellowId.setTag(boxColor[2]);
        binding.maButtonGreenId.setTag(boxColor[3]);

        binding.maButtonOrangeId.setBackgroundTintList(ColorStateList.valueOf(boxColor[0]));
        binding.maButtonBlueId.setBackgroundTintList(ColorStateList.valueOf(boxColor[1]));
        binding.maButtonYellowId.setBackgroundTintList(ColorStateList.valueOf(boxColor[2]));
        binding.maButtonGreenId.setBackgroundTintList(ColorStateList.valueOf(boxColor[3]));

        // reset config
        viewModel.score = 0;
        viewModel.originalColor = -1;
        viewModel.randomBoxIndex =-1;
        binding.maScoreTvId.setText(viewModel.getScoreText());
        viewModel.isActive = true;
        handler.postDelayed(this::changeBoxColor, 1000);
    }

    private void changeBoxColor() {
        viewModel.randomBoxIndex =viewModel.getRandomIndex();
         viewModel.originalColor =boxColor[viewModel.randomBoxIndex];

         // Change the box to gray
        boxes[viewModel.randomBoxIndex].setBackgroundTintList(ColorStateList.valueOf(gray));

        boxes[viewModel.randomBoxIndex].setTag(gray);
        /**
         * Game over if the user didn't tap the gray box
         * if game started and user failed to tap box i.e game over
         */

        handler.postDelayed(this::endGame, 1000);
    }

    private void onBoxClick(MaterialButton box) {
        if (/*box.getDrawingCacheBackgroundColor() == gray*/ (int)box.getTag() == gray) {
            viewModel.score++;
            binding.maScoreTvId.setText(viewModel.getScoreText());
            handler.removeCallbacksAndMessages(null);
            boxes[viewModel.randomBoxIndex].setBackgroundTintList(ColorStateList.valueOf(viewModel.originalColor));
            boxes[viewModel.randomBoxIndex].setTag(boxColor[viewModel.randomBoxIndex]);
            changeBoxColor();
        } else {
            endGame();
        }
    }

    private void endGame() {
        if(viewModel.isActive){
            viewModel.isActive = false;
            // Display a game over popup with the score and an option to restart the game.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Game Over. Your score is: " + viewModel.score)
                    .setPositiveButton("Restart", (dialog, which) -> {
                        startGame();
                    });
            builder.create().show();
        }
    }

    @Override
    public void onClick(View v) {

    }
}