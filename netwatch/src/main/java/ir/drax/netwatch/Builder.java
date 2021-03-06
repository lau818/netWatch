package ir.drax.netwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;

import ir.drax.netwatch.cb.NetworkChangeReceiver_navigator;

/**
 * NetWatch Builder class
 * Initiates  NetworkChangeReceiver
 * Registers receiver and sets configuration
 * Requests unregister
 */
public class Builder {
    private static Builder instance;
    private NetworkChangeReceiver receiver;
    private IntentFilter filter = new IntentFilter();
    private Activity activity;
    private Context context;

    static Builder getInstance(Activity activity){
        if (instance==null)
            instance = new Builder(activity);

        return instance;
    }

    static Builder getInstance(Context context){
        if (instance==null)
            instance = new Builder(context);

        return instance;
    }

    private Builder(Activity activity) {
        try {
            this.activity = activity;
            receiver = new NetworkChangeReceiver();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Builder(Context context) {
        try {

            this.context = context;
            receiver = new NetworkChangeReceiver();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Builder setIcon(int icon){
        NetworkChangeReceiver.setNotificationIcon(icon);
        return this;
    }
    public void build(){
        try{
            if (context==null) {
                activity.startService(new Intent(activity, OnKillApp.class));
                activity.registerReceiver(receiver, filter);

                NetworkChangeReceiver.checkState(activity);
            }else{
                context.startService(new Intent(context, OnKillApp.class));
                context.registerReceiver(receiver, filter);

                NetworkChangeReceiver.checkState(context);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Builder setCallBack(NetworkChangeReceiver_navigator uiNavigator){
        NetworkChangeReceiver.setUiNavigator(uiNavigator);
        return this;
    }

    public void setMessage(String message){
        NetworkChangeReceiver.setMessage(message);
    }

    public Builder setNotificationCancelable(boolean autoCancel) {
        NetworkChangeReceiver.setCancelable(autoCancel);
        return this;
    }
    public Builder setNotificationEnabled(boolean enabled) {
        NetworkChangeReceiver.setNotificationEnabled(enabled);
        return this;
    }

    public Builder setNotificationBuilder(NotificationCompat.Builder mBuilder) {
        NetworkChangeReceiver.setNotificationBuilder(mBuilder);
        return this;
    }

    public int getNotificationsId() {
        return NetworkChangeReceiver.getNotificationsId();
    }

    public static void setNotificationsId(int notificationsId) {
        NetworkChangeReceiver.setNotificationsId(notificationsId);
    }

    public static String getMessage() {
        return NetworkChangeReceiver.getMessage();
    }

    public static boolean isCancelable() {
        return NetworkChangeReceiver.isCancelable();
    }

    public static boolean isNotificationEnabled() {
        return NetworkChangeReceiver.isNotificationEnabled();
    }

    void unregister() {
        if (context==null)
            receiver.unregister(activity);
        else
            receiver.unregister(context);
    }

    Activity getActivity() {
        return context==null?activity: (Activity) context;
    }

    boolean isConnected() {
        return NetworkChangeReceiver.isConnected();
    }

    public boolean bannerTypeIsDialog() {
        return NetworkChangeReceiver.bannerTypeDialog();
    }
    public Builder setBannerTypeDialog(boolean bannerTypeDialog){
        NetworkChangeReceiver.setBannerTypeDialog(bannerTypeDialog);
        return this;
    }

    public boolean logsEnabled() {
        return NetworkChangeReceiver.isLogsEnabled();
    }
    public Builder setLogsEnabled(boolean logsEnabled){
        NetworkChangeReceiver.setLogsEnabled(logsEnabled);
        return this;
    }

    public Builder setMaxDelay(int maxDelay){
        NetworkChangeReceiver.setGeneralPingIntervalMaxDelay(maxDelay);
        return this;
    }

    public Builder setMinDelay(int minDelay){
        NetworkChangeReceiver.setGeneralPingIntervalMinDelay(minDelay);
        return this;
    }

    public int getMaxDelay(){
        return NetworkChangeReceiver.getGeneralPingIntervalMaxDelay();
    }

    public int getMinDelay(){
        return NetworkChangeReceiver.getGeneralPingIntervalMinDelay();
    }

    public int getSensitivity(){
        return NetworkChangeReceiver.getSensitivity();
    }

    public Builder setSensitivity(int sensitivity){
        NetworkChangeReceiver.setSensitivity(sensitivity);
        return this;
    }
}
