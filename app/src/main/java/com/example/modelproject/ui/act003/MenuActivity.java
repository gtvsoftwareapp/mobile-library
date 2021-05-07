package com.example.modelproject.ui.act003;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.modelproject.R;
import com.example.modelproject.ui.act001.LoginActivity;

public class MenuActivity extends AppCompatActivity {

    public Context context;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private Fragment mFOption;
    private Fragment mFContent;

    private FragmentManager mFragmentManager;

    private long mBackPressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        inicializarVariaveis();
        inicializarAcoes();
    }

    private void inicializarVariaveis() {

        context = MenuActivity.this;

        mFragmentManager = getSupportFragmentManager();

        mFOption = mFragmentManager.findFragmentById(R.id.f_option_menu);

        mFContent = mFragmentManager.findFragmentById(R.id.f_content_menu);

        mDrawerLayout = findViewById(R.id.dl_menu);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }

    private void inicializarAcoes() {

        ajustarTela(context);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + 2000 > System.currentTimeMillis())
            abrirLogin(context);
        else {
            Toast.makeText(getBaseContext(), "Pressione mais de uma vez para fechar o aplicativo!", Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        }
    }

    private void ajustarTela(Context context) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.verde)));
        getSupportActionBar().setTitle("Menu");

        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.verde));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

    }

    private void abrirLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

}
