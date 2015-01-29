package hr.foi.air.evolaris.skilift.smartwatch;

import android.content.Context;
import android.content.Intent;

import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;

public class ManagedControlExtension extends ControlExtension {

    public static final String EXTENSION_NO_HISTORY = "EXTENSION_NO_HISTORY";
    public static final String EXTENSION_OVERRIDES_BACK = "EXTENSION_OVERRIDES_BACK";
    private Intent mIntent;
    protected ControlManagerSmartWatch2 mControlManager;

    public ManagedControlExtension(Context context, String hostAppPackageName,
            ControlManagerSmartWatch2 controlManager, Intent intent) {
        super(context, hostAppPackageName);
        this.mIntent = intent;
        mControlManager = controlManager;
    }
    
    /**
     * @return Return the intent that started this controlExtension.
     */
    public Intent getIntent() {
        return mIntent;
    }

}
