package org.ametro.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.ametro.R;
import org.ametro.ui.tasks.MapInstallerAsyncTask;
import org.ametro.ui.tasks.MapLoadAsyncTask;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Semaphore;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UIAutomatorAsyncTaskTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = "org.ametro";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;


    @Test
    public void PrimoTest() throws InterruptedException, UiObjectNotFoundException, RemoteException {
        // SEQUENZA UI->TASK1->TASK2->Sort


        // Definisco i semafori, uno per ogni task, eventualmente settando il numero di task possibili
        MapInstallerAsyncTask.task_MapInstallerAsync_Start = new Semaphore(1);
        MapInstallerAsyncTask.task_MapInstallerAsync_Finish = new Semaphore(1);
        MapLoadAsyncTask.task_MapLoadAsync_Start = new Semaphore(1);
        MapLoadAsyncTask.task_MapLoadAsync_Finish = new Semaphore(1);


        //Il test mette rosso i semafori, in modo da poterne determinare autonomamente lo sblocco
        Log.d("TEST", "Il test prova ad acquisire i semafori");
        MapInstallerAsyncTask.task_MapInstallerAsync_Start.acquire();
        MapInstallerAsyncTask.task_MapInstallerAsync_Finish.acquire();
        MapLoadAsyncTask.task_MapLoadAsync_Start.acquire();
        MapLoadAsyncTask.task_MapLoadAsync_Finish.acquire();

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        Log.d("TEST", "TEST: Avvio la activity");
        //Log.d("TEST","TEST: valori dei semafori: Download="+DownloadMezziTask.taskDownload.availablePermits()+" meteo="+LeggiMeteoTask.taskMeteo.availablePermits());
        //Thread.sleep(5000);
        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT * 100);
        Log.d("TEST", "TEST: Fine before");
        //La app è stata avviata


        Thread.sleep(2000);
        Log.d("TEST", "TEST: Il test sblocca l'avvio del task meteo");
        MapInstallerAsyncTask.task_MapInstallerAsync_Start.release();
        Log.d("TEST", "TEST: Rilasciato il semaforo meteo");

        Thread.sleep(1000);
        Log.d("TEST", "TEST: Il test sblocca la terminazione del task meteo");
        MapInstallerAsyncTask.task_MapInstallerAsync_Finish.release();
        //Log.d("TEST", "TEST: Rilasciato il semaforo meteo");

        Thread.sleep(2000);
        Log.d("TEST", "TEST: Il test sblocca l'avvio del task download");
        MapLoadAsyncTask.task_MapLoadAsync_Start.release();
        //Log.d("TEST", "TEST: Rilasciato il semaforo download");

        Thread.sleep(1000);
        Log.d("TEST", "TEST: Il test sblocca la terminazione del task download");
        MapLoadAsyncTask.task_MapLoadAsync_Finish.release();

        mDevice.pressHome();
        Thread.sleep(2000);
        mDevice.pressRecentApps();
        Thread.sleep(1000);
        mDevice.pressRecentApps();

        Thread.sleep(1000);
/*
        UiObject ui = mDevice.findObject(new UiSelector().text("SORT"));
        ui.click();
*/
        mDevice.findObject(new UiSelector().resourceId("org.ametro.ui.activities:id/logoImageView")).click();

        Thread.sleep(2000);
    }

}