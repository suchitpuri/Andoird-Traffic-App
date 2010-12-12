package com.androidMaps.utilities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import com.android.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class PointOverlay extends com.google.android.maps.Overlay
    {
        GeoPoint p;
        private Resources resources;
        private Speed speed;

        public PointOverlay(Location location, Resources resources, Speed speed) {
            this.speed = speed;
            p= new GeoPoint((int)(location.getLatitude()*1000000),(int)(location.getLongitude()*1000000));
            this.resources = resources;
        }


        @Override
        public boolean draw(Canvas canvas, MapView mapView,
        boolean shadow, long when)
        {
            super.draw(canvas, mapView, shadow);

            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(p, screenPts);

            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
                    resources, speed.getIcon());
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null);
            return true;
        }
    }