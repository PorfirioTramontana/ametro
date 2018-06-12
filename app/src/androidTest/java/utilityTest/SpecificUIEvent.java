package utilityTest;

import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

public class SpecificUIEvent extends GeneralEvent {

    public static final int INSTALL_MAP_BUENOS_AiRES_19 = 0;
    public static final int INSTALL_MAP_FROM_MENU_19 = 10;
    public static final int LOAD_MAP_BUENOS_AiRES_19 = 1;
    public static final int LOAD_MAP_BUENOS_AIRES_FROM_MENU_19 = 11;
    public static final int DELETE_MAP_BUENOS_AIRES_19 = 2;

    public static void execute(int ev) {
        switch (ev) {
            case INSTALL_MAP_BUENOS_AiRES_19:
                try {
                    Thread.sleep(TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    mDevice.findObject(new UiSelector().resourceId("org.ametro:id/logoImageView")).click();
                    mDevice.findObject(new UiSelector().text("Tap screen to show available maps.")).click();
                    mDevice.findObject(new UiSelector().text("Argentina")).click();
                    mDevice.findObject(new UiSelector().text("Buenos Aires")).click();
                    mDevice.pressBack();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                Log.d("TEST", "TEST: Click Install Map");
                break;
            case LOAD_MAP_BUENOS_AiRES_19:
                try {
                    Thread.sleep(TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    mDevice.findObject(new UiSelector().resourceId("org.ametro:id/logoImageView")).click();
                    mDevice.findObject(new UiSelector().text("Tap screen to show available maps.")).click();
                    mDevice.findObject(new UiSelector().text("Argentina")).click();
                    mDevice.findObject(new UiSelector().text("Buenos Aires")).click();
                    mDevice.pressBack();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                Log.d("TEST", "TEST: Click Install Map");
                break;
            case INSTALL_MAP_FROM_MENU_19:
                try {
                    Thread.sleep(TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    mDevice.findObject(new UiSelector().description("Navigation opened")).click();
                    mDevice.findObject(new UiSelector().text("Change map")).click();
                    mDevice.findObject(new UiSelector().description("Add")).click();
                    mDevice.findObject(new UiSelector().text("Argentina")).click();
                    mDevice.findObject(new UiSelector().text("Buenos Aires")).click();
                    mDevice.pressBack();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case LOAD_MAP_BUENOS_AIRES_FROM_MENU_19:
                try {
                    Thread.sleep(TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    mDevice.findObject(new UiSelector().description("Navigation opened")).click();
                    mDevice.findObject(new UiSelector().text("Change map")).click();
                    mDevice.findObject(new UiSelector().text("Buenos Aires")).click();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                Log.d("TEST", "TEST: Click Install Map");
                break;
            case DELETE_MAP_BUENOS_AIRES_19:
                try {
                    Thread.sleep(TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    mDevice.findObject(new UiSelector().description("Navigation opened")).click();
                    mDevice.findObject(new UiSelector().text("Change map")).click();
                    mDevice.findObject(new UiSelector().description("More options")).click();
                    mDevice.findObject(new UiSelector().text("Delete")).click();
                    mDevice.findObject(new UiSelector().text("Buenos Aires")).click();
                    mDevice.findObject(new UiSelector().description("Delete")).click();
                    mDevice.pressBack();
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
                Log.d("TEST", "TEST: Click Install Map");
                break;


        }
    }
}
