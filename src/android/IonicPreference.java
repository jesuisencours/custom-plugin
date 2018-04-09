package com.ionicframework.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.net.URL;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class IonicPreference extends CordovaPlugin {
  Context myContext = null;
  
  /**
   * Sets the context of the Command. This can then be used to do things like
   * get file paths associated with the Activity.
   *
   * @param cordova The context of the main Activity.
   * @param cWebView The CordovaWebView Cordova is running in.
   */
  public void initialize(CordovaInterface cordova, CordovaWebView cWebView) {
    super.initialize(cordova, cWebView);
    this.myContext = this.cordova.getActivity().getApplicationContext();
  }

  

  private void logMessage(String tag, String message) {
    Log.i("IONIC.DEPLOY." + tag, message);
  }
  
  private SharedPreferences getPreferences() {
    SharedPreferences prefs = this.myContext.getSharedPreferences("com.ionic.deploy.preferences", Context.MODE_PRIVATE);
    return prefs;
  }

  /**
   * Executes the request and returns PluginResult.
   *
   * @param action            The action to execute.
   * @param args              JSONArry of arguments for the plugin.
   * @param callbackContext   The callback id used when calling back into JavaScript.
   * @return                  True if the action was valid, false if not.
   */
  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    SharedPreferences pref = getPreferences();
    if (action.equals("set")) {
      SharedPreferences.Editor editor = pref.edit();
      editor.putString(args.getString(0), args.getString(1));
      editor.commit();      
      callbackContext.success("true");
      return true;
    } else if (action.equals("get")) {
      callbackContext.success(pref.getString(args.getString(0), "no key"));
      return true;
    } else {
      return false;
    }
  }
}
