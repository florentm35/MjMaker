package fr.florent.mjmaker;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;


public class AppContext extends Application implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public AndroidInjector androidInjector() {
        return dispatchingAndroidInjector;
    }

    @Component
    public interface ApplicationComponent {
    }

    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerMyApplicationComponent.create().inject(this);
        sApplication = this;
    }
}