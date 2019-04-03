package com.doubotis.staticmap.testing;

import java.awt.Color;
import java.io.File;

/*
 * Copyright (C) 2017 Admin
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

import com.doubotis.staticmap.StaticMap;
import com.doubotis.staticmap.geo.Location;
import com.doubotis.staticmap.geo.LocationPath;
import com.doubotis.staticmap.layers.TMSLayer;
import com.doubotis.staticmap.layers.components.LineString;

import org.junit.Test;

/**
 *
 * @author Admin
 */
public class StaticMapTesting {

    public StaticMapTesting() {
    }

    @Test
    public void testSmallMap() {

        try {

            StaticMap mp = new StaticMap(200, 200);
            mp.setLocation(50.5, 5.5);
            mp.setZoom(14);

            TMSLayer osmMap = new TMSLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png");
            mp.addLayer(osmMap);

            File f = new File(File.separator + "test.png");
            System.out.println("Printing small map in file " + f.getAbsolutePath() + "...");
            mp.drawInto(f);
            System.out.println("Printing done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMapOfCityWithOpacity() {

        try {

            StaticMap mp = new StaticMap(800, 1300);
            mp.setLocation(-8.046506880246506, -34.87865674486085);
            mp.setZoom(17);

            TMSLayer osmMap = new TMSLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png");
            osmMap.setOpacity(0.5f);
            mp.addLayer(osmMap);

            LocationPath path = new LocationPath();
            path.addLocation(new Location(-8.046506880246506, -34.87865674486085));
            
            LineString layer = new LineString(path);
            layer.strokeColor(Color.BLUE);
            layer.strokeWidth(16);
            layer.outlineWidth(8);
            mp.addLayer(layer);

            File f = new File("test2.png");
            System.out.println("Printing map of city with opacity in file " + f.getAbsolutePath() + "...");
            mp.drawInto(f);
            System.out.println("Printing done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
