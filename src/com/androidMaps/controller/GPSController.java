package com.androidMaps.controller;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.androidMaps.interfaces.GPSCallback;

import java.util.List;

public class GPSController
{
	private static final int gpsMinTime = 500;
	private static final int gpsMinDistance = 0;
	
	private static LocationManager locationManager = null;
	private static LocationListener locationListener = null;
	private static GPSCallback gpsCallback = null;
	
	public GPSController()
	{	
		GPSController.locationListener = new LocationListener()
		{
			@Override
            public void onLocationChanged(final Location location)
			{
				if (GPSController.gpsCallback != null)
				{
					GPSController.gpsCallback.onGPSUpdate(location);
				}
			}
			
			@Override
			public void onProviderDisabled(final String provider)
			{
			}
			
			@Override
			public void onProviderEnabled(final String provider)
			{
			}
			
			@Override
			public void onStatusChanged(final String provider, final int status, final Bundle extras)
			{
			}
		};
	}
	
	public GPSCallback getGPSCallback()
	{
		return GPSController.gpsCallback;
	}
	
	public void setGPSCallback(final GPSCallback gpsCallback)
	{
		GPSController.gpsCallback = gpsCallback;
	}
	
	public void startListening(final Context context)
	{
		if (GPSController.locationManager == null)
		{
			GPSController.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		}
		
		final Criteria criteria = new Criteria();
		
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setSpeedRequired(true);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		final String bestProvider = GPSController.locationManager.getBestProvider(criteria, true);
		
		if (bestProvider != null && bestProvider.length() > 0)
		{
			GPSController.locationManager.requestLocationUpdates(bestProvider, GPSController.gpsMinTime,
					GPSController.gpsMinDistance, GPSController.locationListener);
		}
		else
		{
			final List<String> providers = GPSController.locationManager.getProviders(true);
			
			for (final String provider : providers)
			{
				GPSController.locationManager.requestLocationUpdates(provider, GPSController.gpsMinTime,
						GPSController.gpsMinDistance, GPSController.locationListener);
			}
		}
	}
	
	public void stopListening()
	{
		try
		{
			if (GPSController.locationManager != null && GPSController.locationListener != null)
			{
				GPSController.locationManager.removeUpdates(GPSController.locationListener);
			}
			
			GPSController.locationManager = null;
		}
		catch (final Exception ex)
		{
			
		}
	}
}
