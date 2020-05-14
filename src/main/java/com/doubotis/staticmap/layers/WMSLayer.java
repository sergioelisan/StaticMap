/*
 * Copyright (C) 2017 doubotis
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.doubotis.staticmap.layers;

import java.awt.Graphics2D;

import com.doubotis.staticmap.StaticMap;
import com.doubotis.staticmap.geo.Location;
import com.doubotis.staticmap.geo.LocationBounds;
import com.doubotis.staticmap.geo.PointF;

/**
 *
 * @author Christophe
 */
public class WMSLayer extends TMSLayer {
    private boolean mIsPNG = true;
    private boolean mIsTransparent = true;
    protected int mMaxZoom = 11;
    protected float mOpacity = 1.0f;
    protected String mHost;
    protected String[] mLayers;
    protected String mFilter;
    private StaticMap mMapPicture;

    public WMSLayer(String host, String[] layers) {
        super(host);

        mHost = mPattern;
        mLayers = layers;
    }

    public void setLayers(String[] layers) {
        mLayers = layers;
    }

    @Override
    protected String buildURL(int tileX, int tileY, int tileZ) {

        var proj = mMapPicture.getProjection();

        var pattern = "";
        pattern += mHost;
        pattern += "?service=WMS&version=1.1.1&request=GetMap&Layers=";
        for (int i = 0; i < mLayers.length; i++) {
            pattern += mLayers[i];
            if (i < mLayers.length - 1)
                pattern += ",";
        }

        // Compute locations corners.
        double lat = latitudeFromTile(tileY, tileZ);
        double lon = longitudeFromTile(tileX, tileZ);
        var topLeftLocation = new Location(lat, lon);

        var topLeftCorner = proj.unproject(topLeftLocation, tileZ);
        var bottomRightCorner = new PointF(topLeftCorner.x + proj.getTileSize(), topLeftCorner.y + proj.getTileSize());
        var bottomRightLocation = proj.project(bottomRightCorner, tileZ);

        var bounds = new LocationBounds(topLeftLocation.getLongitude(), bottomRightLocation.getLongitude(),
                topLeftLocation.getLatitude(), bottomRightLocation.getLatitude());

        pattern += "&Styles=&SRS=EPSG:4326";
        pattern += "&BBOX=" + bounds.xmin + "," + bounds.ymax + "," + bounds.xmax + "," + bounds.ymin;
        pattern += "&width=" + proj.getTileSize();
        pattern += "&height=" + proj.getTileSize();
        pattern += (mIsPNG) ? "&format=image/png" : "&format=image/jpg";
        pattern += "&TRANSPARENT=" + ((mIsTransparent) ? "TRUE" : "FALSE");
        pattern += (mFilter == null) ? "" : "&cql_Filter=" + mFilter;

        return pattern;

    }

    @Override
    // public void draw(ImageProcessor ip, StaticMap mp) {
    public void draw(Graphics2D graphics, StaticMap mp) {
        mMapPicture = mp;
        super.draw(graphics, mp);
    }

}
