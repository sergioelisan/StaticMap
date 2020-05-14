/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doubotis.staticmap.layers.components;

import java.awt.Color;
import java.awt.Graphics2D;

import com.doubotis.staticmap.StaticMap;
import com.doubotis.staticmap.geo.Location;
import com.doubotis.staticmap.layers.Layer;

/**
 *
 * @author Christophe
 */
public class Dot implements Layer {
    private float mOpacity;
    private Location mLocation;
    private Color mOutlineColor = Color.WHITE;
    private Color mStrokeColor = Color.RED;
    private int mStrokeWidth = 2;
    private int mOutlineWidth = 2;

    public Dot(Location location) {
        mLocation = location;
    }

    public void opacity(float opacity) {
        mOpacity = opacity;
    }

    public float getOpacity() {
        return mOpacity;
    }

    public Location getLocation() {
        return mLocation;
    }

    public Dot strokeColor(Color strokeColor) {
        mStrokeColor = strokeColor;
        return this;
    }

    public Dot strokeWidth(int width) {
        mStrokeWidth = width;
        return this;
    }

    public Dot outlineColor(Color outlineColor) {
        mOutlineColor = outlineColor;
        return this;
    }

    public Dot outlineWidth(int width) {
        mOutlineWidth = width;
        return this;
    }

    @Override
    // public void draw(ImageProcessor ip, StaticMap mp) {
    public void draw(Graphics2D graphics, StaticMap mp) {
        var proj = mp.getProjection();

        var pixelsLocation = proj.unproject(mLocation, mp.getZoom());
        int x = (int) (pixelsLocation.x - mp.getOffset().x);
        int y = (int) (pixelsLocation.y - mp.getOffset().y);

        int center_x = x - ((mOutlineWidth + mStrokeWidth) / 2);
        int center_y = y - ((mOutlineWidth + mStrokeWidth) / 2);

        graphics.setColor(mOutlineColor);
        graphics.drawOval(center_x, center_y, mOutlineWidth + mStrokeWidth, mOutlineWidth + mStrokeWidth);
        graphics.fillOval(center_x, center_y, mOutlineWidth + mStrokeWidth, mOutlineWidth + mStrokeWidth);

        center_x = x - (mStrokeWidth / 2);
        center_y = y - (mStrokeWidth / 2);

        graphics.setColor(mStrokeColor);
        graphics.drawOval(center_x, center_y, mStrokeWidth, mStrokeWidth);
        graphics.fillOval(center_x, center_y, mStrokeWidth, mStrokeWidth);
    }

}
