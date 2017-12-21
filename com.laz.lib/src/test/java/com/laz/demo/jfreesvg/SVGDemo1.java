package com.laz.demo.jfreesvg;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Orientation;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

public class SVGDemo1 {
	public static void main(String[] args) throws IOException {
		SVGGraphics2D g2 = new SVGGraphics2D(100, 200);
        File f = new File("SVGBarChartDemo1.svg"); 
        SVGUtils.writeToSVG(f, g2.getSVGElement());
	}
    
    
}
