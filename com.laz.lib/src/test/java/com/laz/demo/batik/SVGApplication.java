package com.laz.demo.batik;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.apache.batik.bridge.Window;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherAdapter;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

public class SVGApplication {
	public static void main(String[] args) {
		new SVGApplication();
	}

	Frame frame;
	JSVGCanvas canvas;
	Document document;
	Window window;
	 private int GAME_WIDTH = 800;  
	    private int GAME_HEIGHT = 600;  
	    private Image offScreenImage;  
	public SVGApplication() {
		frame = new Frame() {
			Image offScreenImage;
			@Override
			public void update(Graphics g) {
		        paint(g);  

			}
		};
		canvas = new JSVGCanvas();
		// Forces the canvas to always be dynamic even if the current
		// document does not contain scripting or animation.
		canvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		canvas.addSVGLoadEventDispatcherListener(new SVGLoadEventDispatcherAdapter() {
			public void svgLoadEventDispatchStarted(
					SVGLoadEventDispatcherEvent e) {
				// At this time the document is available...
				document = canvas.getSVGDocument();
				// ...and the window object too.
				window = canvas.getUpdateManager().getScriptingEnvironment()
						.getWindow();
				// Registers the listeners on the document
				// just before the SVGLoad event is
				// dispatched.
				// It is time to pack the frame.
				frame.pack();
			}
		});

		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				// The canvas is ready to load the base document
				// now, from the AWT thread.
				System.out.println("--");
				canvas.setURI(this.getClass().getResource("3d-rotation.svg")
						.toString());
			}
		});

		frame.add(canvas);
		frame.setSize(800, 600);
		frame.show();
	}

	public void registerListeners() {
		// Gets an element from the loaded document.
		Element elt = document.getElementById("elt-id");
		EventTarget t = (EventTarget) elt;

		// Adds a 'onload' listener
		t.addEventListener("SVGLoad", new OnLoadAction(), false);

		// Adds a 'onclick' listener
		t.addEventListener("click", new OnClickAction(), false);
	}

	public class OnLoadAction implements EventListener {

		@Override
		public void handleEvent(org.w3c.dom.events.Event evt) {
			// Perform some actions here...

			// ...for example start an animation loop:

		}
	}

	public class OnClickAction implements EventListener {
		public void handleEvent(Event evt) {
			// Perform some actions here...

			// ...for example schedule an action for later:
		}
	}

	public class Animation implements Runnable {
		public void run() {
			// Insert animation code here...
		}
	}

	public class DelayedTask implements Runnable {
		public void run() {
			// Perform some actions here...

			// ...for example displays an alert dialog:
			window.alert("Delayed Action invoked!");
		}
	}
}
