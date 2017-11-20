package com.laz.demo.concurrency;

import jdk.nashorn.internal.ir.annotations.Immutable;

import com.laz.demo.concurrency.*;

/**
 * Point
 * <p/>
 * Immutable Point class used by DelegatingVehicleTracker
 *
 * @author Brian Goetz and Tim Peierls
 */
@Immutable
public class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
