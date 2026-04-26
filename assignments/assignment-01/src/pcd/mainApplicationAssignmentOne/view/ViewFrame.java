package pcd.mainApplicationAssignmentOne.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pcd.mainApplicationAssignmentOne.RenderSynch;
import pcd.mainApplicationAssignmentOne.controller.KickBallCmd;
import pcd.mainApplicationAssignmentOne.controller.MainLoop;
import pcd.mainApplicationAssignmentOne.controller.SimplePrintCmd;


public class ViewFrame extends JFrame implements KeyListener{
    
    private VisualiserPanel panel;
    private ViewModel model;
    private RenderSynch sync;

	//new code for input handling
	private MainLoop activeController;
	//end new code
	
    public ViewFrame(ViewModel model, int w, int h){ //da cancellare
    	this.model = model;
		this.activeController = null;
    	this.sync = new RenderSynch();
    	setTitle("GAME");
        setSize(w,h + 25);
        setResizable(false);
        panel = new VisualiserPanel(w,h);
        getContentPane().add(panel);
        /* new code */

		this.addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		requestFocusInWindow(); 

		/* end: new code */
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});
    }

    public ViewFrame(ViewModel model, MainLoop controller, int w, int h){
    	this.model = model;
		this.activeController = controller;
    	this.sync = new RenderSynch();
    	setTitle("GAME");
        setSize(w,h + 25);
        setResizable(false);
        panel = new VisualiserPanel(w,h);
        getContentPane().add(panel);
        /* new code */

		this.addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		requestFocusInWindow(); 

		/* end: new code */
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});
    }

     
    public void render(){
		long nf = sync.nextFrameToRender();
        panel.repaint();
		try {
			sync.waitForFrameRendered(nf);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
    }
        
    public class VisualiserPanel extends JPanel {
        private int ox;
        private int oy;
        private int delta;
        
        public VisualiserPanel(int w, int h){
            setSize(w,h + 25);
            ox = w/2;
            oy = h/2;
            delta = Math.min(ox, oy);
        }

        public void paint(Graphics g){
    		Graphics2D g2 = (Graphics2D) g;
    		
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    		          RenderingHints.VALUE_ANTIALIAS_ON);
    		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
    		          RenderingHints.VALUE_RENDER_QUALITY);
    		g2.clearRect(0,0,this.getWidth(),this.getHeight());
            
    		g2.setColor(Color.LIGHT_GRAY);
		    g2.setStroke(new BasicStroke(1));
    		g2.drawLine(ox,0,ox,oy*2);
    		g2.drawLine(0,oy,ox*2,oy);
    		g2.setColor(Color.BLACK);
			
			var countDrawn = 0;
    		//disegno palline
			countDrawn+=(model.getBalls().stream().filter(elem->elem.ballCollideWith()
				.isPresent())
				.filter(elem->elem.ballCollideWith().get().intValue() == 1)
				.toList()
				.size());

			g2.setColor(Color.GREEN);
			
			g2.setStroke(new BasicStroke(1));
			for (var b: model.getBalls().stream().filter(elem->elem.ballCollideWith()
				.isPresent())
				.filter(elem->elem.ballCollideWith().get().intValue() == 1)
				.toList()) {
				var p = b.pos();
				int x0 = (int)(ox + p.x()*delta);
				int y0 = (int)(oy - p.y()*delta);
				int radiusX = (int)(b.radius()*delta);
				int radiusY = (int)(b.radius()*delta);
				g2.drawOval(x0 - radiusX,y0 - radiusY,radiusX*2,radiusY*2);
			}
			countDrawn+=model.getBalls().stream().filter(elem->!elem.ballCollideWith()
				.isPresent())
				.toList()
				.size();
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(1));
			for (var b: model.getBalls().stream().filter(elem->!elem.ballCollideWith()
				.isPresent())
				.toList()) {
				var p = b.pos();
				int x0 = (int)(ox + p.x()*delta);
				int y0 = (int)(oy - p.y()*delta);
				int radiusX = (int)(b.radius()*delta);
				int radiusY = (int)(b.radius()*delta);
				g2.drawOval(x0 - radiusX,y0 - radiusY,radiusX*2,radiusY*2);
			}

			//fine disegno palline

			g2.setStroke(new BasicStroke(3));
			var pb = model.getPlayerBall();
			if (pb != null) {
				var p1 = pb.pos();
				int x0 = (int)(ox + p1.x()*delta);
				int y0 = (int)(oy - p1.y()*delta);
				int radiusX = (int)(pb.radius()*delta);
				int radiusY = (int)(pb.radius()*delta);
				g2.drawOval(x0 - radiusX,y0 - radiusY,radiusX*2,radiusY*2);
			}

			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(2));
			for (var b: model.getHoles()) {
				var p = b.pos();
				int x0 = (int)(ox + p.x()*delta);
				int y0 = (int)(oy - p.y()*delta);
				int radiusX = (int)(b.radius()*delta);
				int radiusY = (int)(b.radius()*delta);
				g2.drawOval(x0 - radiusX,y0 - radiusY,radiusX*2,radiusY*2);
			}

			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(1));
			g2.drawString("Num small balls: " + model.getBalls().size(), 20, 40);
			g2.drawString("Frame per sec: " + model.getFramePerSec(), 20, 60);
			g2.drawString("[Num. Balls Actually Drawn: " + countDrawn, 20, 90);
			sync.notifyFrameRendered();
    		
        }
        
    }

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		final double speed = 2.0;
		if (e.getExtendedKeyCode() == KeyEvent.VK_W) {
			activeController.notifyNewCmd(new KickBallCmd("UP", speed));
		} else if (e.getExtendedKeyCode() == KeyEvent.VK_A){
			activeController.notifyNewCmd(new KickBallCmd("LEFT", speed));
		} else if (e.getExtendedKeyCode() == KeyEvent.VK_D){
			activeController.notifyNewCmd(new KickBallCmd("RIGHT", speed));
		} else if (e.getExtendedKeyCode() == KeyEvent.VK_S){
			activeController.notifyNewCmd(new KickBallCmd("DOWN", speed));
		} else if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE){
			activeController.notifyNewCmd(new SimplePrintCmd("test msg ok "));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
