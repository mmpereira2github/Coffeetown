package com.syncinfo.android.coffeetown;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.syncinfo.mvc.view.ErrorHandler;
import com.syncinfo.mvc.view.ErrorHandlerProvider;
import com.syncinfo.util.Assert;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ErrorHandlerProvider, ErrorHandler {

    public static final String FRAGMENT_CLASS = "FragmentClass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (null == intent || intent.hasExtra(FRAGMENT_CLASS) == false) {
            showMainLayout();
        }
        else {
            showFragmentLayout(intent, true);
        }


    }

    private void showMainLayout() {
        /*
         * Change locale only
         */
        Configuration configuration = getResources().getConfiguration();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        configuration.setLocale(new Locale("pt", "BR"));
        getResources().updateConfiguration(configuration,displayMetrics);


        setContentView(R.layout.main);
    }

    private void showFragmentLayout(Intent intent, boolean firstFragment) {
        Assert.notNull(intent, "HostFragmentActivity requires intent");

        String fragmentClassName = intent.getStringExtra(FRAGMENT_CLASS);
        Assert.notNull(fragmentClassName, "HostFragmentActivity requires " + FRAGMENT_CLASS);

        Class<? extends Fragment> fragmentClass = null;
        try {
            fragmentClass = (Class<? extends Fragment>) Class.forName(fragmentClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Fragment fragment;

        try {
            fragment = fragmentClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        setContentView(R.layout.fragment_host);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        if (!firstFragment) {
            fragmentTransaction.addToBackStack(fragmentClassName);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onError(Exception ex, Context ctx) {
        onError(ex.getMessage(), ctx.toString());
    }

    @Override
    public void onError(String msg, Object... args) {
        AlertDialog.Builder dialogo = new
                AlertDialog.Builder(this);
        dialogo.setTitle("Erro");
        dialogo.setMessage(msg);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("MainActivity", "onActivityResult");
    }
}
