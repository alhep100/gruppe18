/**
 * 
 */
package edu.propra.bomberman.graphicengine;

/**
 * @author Nadescha
 * 
 */
public class GraphicsEngineThread implements Runnable {

	public boolean			running;
	public GraphicEngine	ge;

	public GraphicsEngineThread(GraphicEngine ge) {
		this.ge = ge;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		running = true;
		while (running) {
			ge.getPanel().repaint();
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void stopRunning() {
		this.running=false;
	}

}
