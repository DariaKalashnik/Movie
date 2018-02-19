package com.project.stageone.movie.rest;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.project.stageone.movie.models.Movie;

import java.util.Locale;

public class Utils {

    private static Intent intent;

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showProgressBar(ProgressBar progressBar, RecyclerView recyclerView) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    public static void hideProgressBar(ProgressBar progressBar, RecyclerView recyclerView) {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private static void setTint(Drawable drawable, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
    }

    public static void showErrorDialogue(int color, Drawable icon, Context context, String title, String message, String btnMsg) {
        setTint(icon, color);
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(icon)
                .setPositiveButton(btnMsg, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    public static void setToolbar(AppCompatActivity context, Toolbar toolbar) {
        context.setSupportActionBar(toolbar);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static boolean checkConnection(Context context) {
        ConnectivityReceiver.isOnline(context);
        return true;
    }

    public static String setLanguage(String originalLng) {
        Locale locale = new Locale(originalLng);
        return locale.getDisplayLanguage(locale);
    }

    public static void startIntent(Context context, Class destination) {
        intent = new Intent(context, destination);
        context.startActivity(intent);
    }

    public static void startIntentWithExtra(Context context, Class destination, String key, Movie movie) {
        intent = new Intent(context, destination);
        intent.putExtra(key, movie);
        context.startActivity(intent);
    }
}
