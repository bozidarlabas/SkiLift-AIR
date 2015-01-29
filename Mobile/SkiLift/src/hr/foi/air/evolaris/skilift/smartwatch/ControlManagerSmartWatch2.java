package hr.foi.air.evolaris.skilift.smartwatch;

import hr.foi.air.evolaris.skilift.LiftDataManager;
import hr.foi.air.evolaris.skilift.data.Lift;
import hr.foi.evolaris.skilift.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Stack;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.control.ControlListItem;
import com.sonyericsson.extras.liveware.extension.util.control.ControlObjectClickEvent;

public class ControlManagerSmartWatch2 extends ControlManagerBase {

	private Stack<Intent> mControlStack;
	LiftDataManager liftDataManager = LiftDataManager.getInstance();

	public ControlManagerSmartWatch2(Context context, String packageName, Intent intent) {
		super(context, packageName);
		mControlStack = new Stack<Intent>();
		// Create an initial control extension
		Intent initialListControlIntent = new Intent(mContext,
				SmartWatchUIOne.class);
		mCurrentControl = createControl(intent);
	}
	
	/**
	 * Get supported control width.
	 * 
	 * @param context
	 *            The context.
	 * @return the width.
	 */

	public static int getSupportedControlWidth(Context context) {
		return context.getResources().getDimensionPixelSize(
				R.dimen.smart_watch_2_control_width);
	}

	/**
	 * Get supported control height.
	 * 
	 * @param context
	 *            The context.
	 * @return the height.
	 */

	public static int getSupportedControlHeight(Context context) {
		return context.getResources().getDimensionPixelSize(
				R.dimen.smart_watch_2_control_height);
	}

	@Override
	public void onRequestListItem(int layoutReference, int listItemPosition) {
		if (mCurrentControl != null) {
			mCurrentControl
					.onRequestListItem(layoutReference, listItemPosition);
		}
	}

	@Override
	public void onListItemClick(ControlListItem listItem, int clickType,
			int itemLayoutReference) {
		if (mCurrentControl != null) {
			mCurrentControl.onListItemClick(listItem, clickType,
					itemLayoutReference);
		}
	}

	@Override
	public void onListItemSelected(ControlListItem listItem) {
		if (mCurrentControl != null) {
			mCurrentControl.onListItemSelected(listItem);
		}
	}

	@Override
	public void onListRefreshRequest(int layoutReference) {
		if (mCurrentControl != null) {
			mCurrentControl.onListRefreshRequest(layoutReference);
		}
	}

	@Override
	public void onObjectClick(ControlObjectClickEvent event) {
		if (mCurrentControl != null) {
			mCurrentControl.onObjectClick(event);
		}
	}

	@Override
	public void onKey(int action, int keyCode, long timeStamp) {
		if (action == Control.Intents.KEY_ACTION_RELEASE
				&& keyCode == Control.KeyCodes.KEYCODE_BACK) {
			onBack();
		} else if (mCurrentControl != null) {
			super.onKey(action, keyCode, timeStamp);
		}
	}

	@Override
	public void onMenuItemSelected(int menuItem) {
		if (mCurrentControl != null) {
			mCurrentControl.onMenuItemSelected(menuItem);
		}
	}

	/**
	 * Closes the currently open control extension. If there is a control on the
	 * back stack it is opened, otherwise extension is closed.
	 */

	public void onBack() {
		Log.v(SmartWatchService.LOG_TAG, "onBack");
		if (!mControlStack.isEmpty()) {
			Intent backControl = mControlStack.pop();
			ControlExtension newControl = createControl(backControl);
			startControl(newControl);
		} else {
			stopRequest();
		}
	}

	/**
	 * Start a new control. Any currently running control will be stopped and
	 * put on the control extension stack.
	 * 
	 * @param intent
	 *            the Intent used to create the ManagedControlExtension. The
	 *            intent must have the requested ManagedControlExtension as
	 *            component, e.g. Intent intent = new Intent(mContext,
	 *            CallLogDetailsControl.class);
	 */
	public void startControl(Intent intent) {
		//addCurrentToControlStack();
		ControlExtension newControl = createControl(intent);
		startControl(newControl);
	}

	public void addCurrentToControlStack() {
		if (mCurrentControl != null
				&& mCurrentControl instanceof ManagedControlExtension) {
			Intent intent = ((ManagedControlExtension) mCurrentControl)
					.getIntent();
			boolean isNoHistory = intent.getBooleanExtra(
					ManagedControlExtension.EXTENSION_NO_HISTORY, false);
			if (isNoHistory) {
				// Not adding this control to history
				Log.d(SmartWatchService.LOG_TAG,
						"Not adding control to history stack");
			} else {
				Log.d(SmartWatchService.LOG_TAG,
						"Adding control to history stack");
				mControlStack.add(intent);
			}
		} else {
			Log.w(SmartWatchService.LOG_TAG,
					"ControlManageronly supports ManagedControlExtensions");
		}
	}

	public ControlExtension createControl(Intent intent) {
		ComponentName component = intent.getComponent();
		ArrayList<Lift> liftsData = liftDataManager.getLiftDataWatch();
		try {
			String className = component.getClassName();
			
			Class<?> clazz = Class.forName(className);
			Log.d("ulazak",className);
			
			Constructor<?> ctor = clazz
					.getConstructor(Context.class, String.class,
							ControlManagerSmartWatch2.class, Intent.class, ArrayList.class);
			
			if (ctor == null) {
				return null;
			}
			Object object = ctor.newInstance(new Object[] { mContext,
					mHostAppPackageName, this, intent, liftsData });
			if (object instanceof ManagedControlExtension) {
				return (ManagedControlExtension) object;
			} else {
				Log.w(SmartWatchService.LOG_TAG,
						"Created object not a ManagedControlException");
			}

		} catch (SecurityException e) {
			Log.w(SmartWatchService.LOG_TAG,
					"ControlManager: Failed in creating control", e);
		} catch (NoSuchMethodException e) {
			Log.w(SmartWatchService.LOG_TAG,
					"ControlManager: Failed in creating control", e);
		} catch (IllegalArgumentException e) {
			Log.w(SmartWatchService.LOG_TAG,
					"ControlManager: Failed in creating control", e);
		} catch (InstantiationException e) {
			Log.w(SmartWatchService.LOG_TAG,
					"ControlManager: Failed in creating control", e);
		} catch (IllegalAccessException e) {
			Log.w(SmartWatchService.LOG_TAG,
					"ControlManager: Failed in creating control", e);
		} catch (InvocationTargetException e) {
			Log.w(SmartWatchService.LOG_TAG,
					"ControlManager: Failed in creating control", e);
		} catch (ClassNotFoundException e) {
			Log.w(SmartWatchService.LOG_TAG,
					"ControlManager: Failed in creating control", e);
		}
		return null;
	}

}