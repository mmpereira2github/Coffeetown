package com.syncinfo.android.mvc.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syncinfo.mvc.view.ErrorHandler;
import com.syncinfo.mvc.view.ErrorHandlerProvider;
import com.syncinfo.mvc.view.impl.ThrowerErrorHandler;
import com.syncinfo.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mmartins on 2018-01-27.
 */

public abstract class ViewFragment extends Fragment {
    private ErrorHandler errorHandler = null;
    private Activity activity = null;
    private Map<Integer, ResultCallback> resultCallbackMap = new HashMap<>();
    private Integer nextResultCodeExpected = 1;

    protected abstract int getLayoutId();

    public ErrorHandler getErrorHandler() {
        return Assert.notNull(this.errorHandler, "ErrorHandler");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(ViewFragment.class.getSimpleName(), getClass().getSimpleName() + "::onAttach");
        if (context instanceof ErrorHandlerProvider) {
            this.errorHandler = (((ErrorHandlerProvider) context).getErrorHandler());
        } else {
            this.errorHandler = new ThrowerErrorHandler();
        }

        if (context instanceof Activity) {
            this.activity = (Activity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(ViewFragment.class.getSimpleName(), getClass().getSimpleName() + "::onCreateView");
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(ViewFragment.class.getSimpleName(), getClass().getSimpleName() + "::onActivityCreated");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.i(ViewFragment.class.getSimpleName(), getClass().getSimpleName() + "::onAttachFragment");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(ViewFragment.class.getSimpleName(), getClass().getSimpleName() + "::onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(ViewFragment.class.getSimpleName(), getClass().getSimpleName() + "::onResume");
    }

    protected <F extends Fragment> F addFragment(int layoutID, F fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutID, fragment);
        fragmentTransaction.commitNow();
        return (F) fragmentManager.findFragmentById(layoutID);
    }

    protected void addFragment(int layoutID, android.app.Fragment fragment) {
        if (null != this.activity) {
            android.app.FragmentManager fragmentManager = this.activity.getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(layoutID, fragment);
            fragmentTransaction.commit();
        } else {
            throw new IllegalStateException("There is no Activity to handle fragments");
        }
    }

    protected void start(Intent intent) {
        intent.setClassName(getContext(), "com.syncinfo.android.coffeetown.MainActivity");
        startActivity(intent);
    }

    protected void startForResult(Intent intent, ResultCallback resultCallback) {
        int resultCodeExpected = this.nextResultCodeExpected++;
        intent.putExtra("ResultCodeExpected", resultCodeExpected);
        this.resultCallbackMap.put(resultCodeExpected, resultCallback);
        intent.setClassName(getContext(), "com.syncinfo.android.coffeetown.MainActivity");
        startActivityForResult(intent, resultCodeExpected);
    }

    protected void returnResult(Intent output) {
        returnResult(output, getActivity().getIntent());
    }

    protected void returnResult(Intent output, Intent input) {
        int resultCodeExpected = input.getIntExtra("ResultCodeExpected", Activity.RESULT_OK);
        Log.i(getClass().getSimpleName(), "returning resultCodeExpected=" + resultCodeExpected);

        getActivity().setResult(resultCodeExpected, output);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(getClass().getSimpleName(), "receiving requestCode=" + requestCode);
        if (this.resultCallbackMap.containsKey(requestCode)) {
            Log.i(getClass().getSimpleName(), "invoking handler");
            this.resultCallbackMap.get(requestCode).handleResult(data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected interface ResultCallback {
        void handleResult(Intent intent);
    }
}