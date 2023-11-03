package com.backlight.crazycolor.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected abstract void initialize();

    protected abstract void registerListener();

    protected abstract void sequence();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
        registerListener();
        sequence();
    }
}
