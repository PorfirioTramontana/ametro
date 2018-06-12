package org.ametro.ui.activities;


import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.ametro.ui.tasks.MapInstallerAsyncTask;
import org.ametro.ui.tasks.MapLoadAsyncTask;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import utilityTest.GeneralEvent;
import utilityTest.SpecificUIEvent;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestZero {

    @BeforeClass
    public static void startup() {
        GeneralEvent.setTime(GeneralEvent.NORMAL);
    }

    @Test
    public void ZeroTest() throws InterruptedException {
        GeneralEvent.declareandSetSemaphore(MapInstallerAsyncTask.sem);
        GeneralEvent.declareandSetSemaphore(MapLoadAsyncTask.sem);
        GeneralEvent.start(MapInstallerAsyncTask.sem);
        GeneralEvent.finish(MapInstallerAsyncTask.sem);
        GeneralEvent.start(MapLoadAsyncTask.sem);
        GeneralEvent.finish(MapLoadAsyncTask.sem);

        GeneralEvent.startApp("org.ametro");
        GeneralEvent.doubleRotation();

        SpecificUIEvent.execute(SpecificUIEvent.INSTALL_MAP_FROM_MENU_19);
        SpecificUIEvent.execute(SpecificUIEvent.LOAD_MAP_BUENOS_AIRES_FROM_MENU_19);
        SpecificUIEvent.execute(SpecificUIEvent.DELETE_MAP_BUENOS_AIRES_19);
        GeneralEvent.pause19();
        GeneralEvent.resume19();
        SpecificUIEvent.execute(SpecificUIEvent.INSTALL_MAP_FROM_MENU_19);
        SpecificUIEvent.execute(SpecificUIEvent.LOAD_MAP_BUENOS_AIRES_FROM_MENU_19);
        SpecificUIEvent.execute(SpecificUIEvent.DELETE_MAP_BUENOS_AIRES_19);


    }


    @After
    public void tearDown() {
        Log.d("TEST", "End test");
    }


}
