package tk.andrielson.verificaipexterno;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import org.jetbrains.annotations.Contract;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initStetho();
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    @Contract(pure = true)
    public static Context getContext() {
        return context;
    }
}
