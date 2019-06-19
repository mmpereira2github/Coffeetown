package com.syncinfo.android.coffeetown.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.syncinfo.android.coffeetown.R;
import com.syncinfo.mvc.view.ErrorHandler;
import com.syncinfo.mvc.view.ErrorHandlerProvider;
import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-01-30.
 */

public class HostFragmentActivity extends FragmentActivity implements ErrorHandlerProvider, ErrorHandler {
    public static final String FRAGMENT_CLASS = "FragmentClass";

    private String fragmentId = null;
    private Fragment fragment = null;

    public HostFragmentActivity() {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("HostFragmentActivity", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("HostFragmentActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("HostFragmentActivity", "onStop");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_host);

        Intent intent = getIntent();
        Assert.notNull(intent, "HostFragmentActivity requires intent");

        String fragmentClassName = intent.getStringExtra(FRAGMENT_CLASS);
        Assert.notNull(fragmentClassName, "HostFragmentActivity requires " + FRAGMENT_CLASS);

        Class<? extends Fragment> fragmentClass = null;
        try {
            fragmentClass = (Class<? extends Fragment>) Class.forName(fragmentClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            this.fragment = fragmentClass.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        this.fragmentId = fragmentClassName;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, this.fragment);
        //fragmentTransaction.addToBackStack(this.fragmentId);
        fragmentTransaction.commit();
    }

    @Override
    public void onError(Exception ex, ErrorHandler.Context ctx) {
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
}
