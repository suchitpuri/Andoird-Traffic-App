package com.androidMaps.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import com.android.R;
import com.androidMaps.controller.GPSController;
import com.androidMaps.interfaces.Constants;
import com.androidMaps.interfaces.GPSCallback;
import com.androidMaps.settings.AppSettings;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class MapScreen extends MapActivity implements GPSCallback {
    private GPSController gpsController = null;
    private double speed = 0.0;
    private int measurement_index = Constants.INDEX_KM;
    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        gpsController = new GPSController();
        gpsController.startListening(getApplicationContext());
        gpsController.setGPSCallback(this);
        measurement_index = AppSettings.getMeasureUnit(this);
        showToast(getString(R.string.info));

    }

    @Override
    public void onGPSUpdate(Location location) {
        location.getLatitude();
        location.getLongitude();
        speed = location.getSpeed();
        String speedString = "" + roundDecimal(convertSpeed(speed), 2);
        String unitString = measurementUnitString(measurement_index);
        showToast(speedString + " " + unitString);
    }

    @Override
    protected void onDestroy() {
        gpsController.stopListening();
        gpsController.setGPSCallback(null);
        gpsController = null;
        super.onDestroy();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.menu_about: {
                displayAboutDialog();

                break;
            }
            case R.id.unit_km: {
                measurement_index = 0;

                AppSettings.setMeasureUnit(this, 0);

                break;
            }
            case R.id.unit_miles: {
                measurement_index = 1;

                AppSettings.setMeasureUnit(this, 1);

                break;
            }
            default: {
                result = super.onOptionsItemSelected(item);

                break;
            }
        }

        return result;
    }

    private double convertSpeed(double speed) {
        return ((speed * Constants.HOUR_MULTIPLIER) * Constants.UNIT_MULTIPLIERS[measurement_index]);
    }

    private String measurementUnitString(int unitIndex) {
        String string = "";

        switch (unitIndex) {
            case Constants.INDEX_KM:
                string = "km/h";
                break;
            case Constants.INDEX_MILES:
                string = "mi/h";
                break;
        }

        return string;
    }

    private double roundDecimal(double value, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(value);

        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        value = bd.doubleValue();
        return value;
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    private void displayAboutDialog() {
        final LayoutInflater inflator = LayoutInflater.from(this);
        final View settingsview = inflator.inflate(R.layout.about, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.app_name));
        builder.setView(settingsview);

        builder.setPositiveButton(android.R.string.ok, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }
}