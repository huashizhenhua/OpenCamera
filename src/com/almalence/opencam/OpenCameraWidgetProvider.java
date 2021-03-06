package com.almalence.opencam;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class OpenCameraWidgetProvider extends AppWidgetProvider
{
	public static String SETTING_BUTTON = "com.almalence.opencam.SETTING_BUTTON";
	
	@Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
    	if (SETTING_BUTTON.equals(intent.getAction()))
    	{
    		Log.e("OpenCameraWidgetProvider", "SETTING CLICK!");
    	}
        super.onReceive(context, intent);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_opencamera);
            
            /// set intent for widget service that will create the views
            Intent serviceIntent = new Intent(context, OpenCameraWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored
            remoteViews.setRemoteAdapter(appWidgetId, R.id.widgetGrid, serviceIntent);
            remoteViews.setEmptyView(R.id.widgetGrid, R.id.widgetEmptyView);
            
            remoteViews.setInt(R.id.widgetGrid, "setBackgroundColor", 
                    OpenCameraWidgetConfigureActivity.bgColor);
            
            // set intent for item click (opens main activity)
//            Intent viewIntent = new Intent(context, MainScreen.class);
//            viewIntent.setAction(Intent.ACTION_MAIN);
//            //viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//            //viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));
//            
//            PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
//            remoteViews.setPendingIntentTemplate(R.id.widgetGrid, viewPendingIntent);
            //remoteViews.setOnClickPendingIntent(R.id.widgetGrid, viewPendingIntent);
            
//            Intent intent = new Intent(OpenCameraWidgetProvider.SETTING_BUTTON);
//	        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//	        remoteViews.setOnClickPendingIntent(R.id.widgetGrid, pendingIntent );
            
            // update widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    public static RemoteViews buildRemoteViews(Context context, int appWidgetId)
    {            
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_opencamera);

        /// set intent for widget service that will create the views
        Intent serviceIntent = new Intent(context, OpenCameraWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored
        remoteViews.setRemoteAdapter(appWidgetId, R.id.widgetGrid, serviceIntent);
        remoteViews.setEmptyView(R.id.widgetGrid, R.id.widgetEmptyView);
        
        remoteViews.setInt(R.id.widgetGrid, "setBackgroundColor", 
                OpenCameraWidgetConfigureActivity.bgColor);
        
        // set intent for item click (opens main activity)
        Intent viewIntent = new Intent(context, MainScreen.class);
        viewIntent.setAction(Intent.ACTION_MAIN);
        //viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));
        
        PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
        remoteViews.setPendingIntentTemplate(R.id.widgetGrid, viewPendingIntent);
        //remoteViews.setOnClickPendingIntent(R.id.widgetGrid, viewPendingIntent);
        
        return remoteViews;   
    }
}