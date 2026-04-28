package pcd.mainApplicationAssignmentOne.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

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
	
    public ViewFrame(ViewModel model, int w, int h){
		
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
    
	private void drawBall(Graphics2D g2, int ox, int oy, int delta, BallViewInfo b) {
		if (b != null) {
			var p = b.pos();
			int x0 = (int)(ox + p.x()*delta);
			int y0 = (int)(oy - p.y()*delta);
			int radiusX = (int)(b.radius()*delta);
			int radiusY = (int)(b.radius()*delta);
			g2.drawOval(x0 - radiusX,y0 - radiusY,radiusX*2,radiusY*2);
		}
	}    

	private int countButDoNotDrawBalls(List<BallViewInfo> listBalls){
		return listBalls.size();
	}

	private int drawFromListOfBalls(Graphics2D g2,int ox,int oy,int delta,List<BallViewInfo> listBalls, Color color){
		
		g2.setColor(color);	
		g2.setStroke(new BasicStroke(1));
		for (var b: listBalls) {
			drawBall(g2, ox, oy, delta, b);
		}

		return listBalls.size();
	}

	private int drawAliveBallsOfPlayer(Graphics2D g2,int ox,int oy,int delta, int playerNumber){
		
		return drawFromListOfBalls(g2,
			ox,
			oy,
			delta,
			model.getAliveBalls()
				.stream()
				.filter(elem->elem.ballCollideWith().isPresent())
				.filter(elem->elem.ballCollideWith().get().intValue() == playerNumber)
				.toList(),
			(playerNumber==1) ? (Color.GREEN) : (Color.BLUE));
	}

	private int drawAliveBallsOfNoPlayer(Graphics2D g2,int ox,int oy,int delta){
		return drawFromListOfBalls(g2,
			ox,
			oy,
			delta,
			model.getAliveBalls()
				.stream()
				.filter(elem->!elem.ballCollideWith().isPresent())
				.toList(),
			Color.BLACK);
	}
	
	private int drawAliveBalls(Graphics2D g2,int ox,int oy,int delta){
		return (drawAliveBallsOfPlayer(g2,ox,oy,delta,1)
				+drawAliveBallsOfPlayer(g2, ox, oy, delta, 2)
				+drawAliveBallsOfNoPlayer(g2,ox,oy,delta));
	}

	private int drawKilledBallsByPlayer(Graphics2D g2,int ox,int oy,int delta, int playerNumber,boolean debug){
		
		var list = model.getDeadBallsForDebug().
					stream()
					.filter(elem->elem.ballCollideWith().isPresent())
					.filter(elem->elem.ballCollideWith().get().intValue() == playerNumber)
					.toList();
		
		if(debug)
			return drawFromListOfBalls(g2, ox, oy, delta,list, (playerNumber==1) ? (Color.RED) : (Color.ORANGE));

		return countButDoNotDrawBalls(list);
	}

	private int drawKilledBallsByNoPlayer(Graphics2D g2,int ox,int oy,int delta,boolean debug){
		var list = model.getDeadBallsForDebug()
				.stream()
				.filter(elem->!elem.ballCollideWith().isPresent())
				.toList();
		if(debug)
			return drawFromListOfBalls(g2, ox, oy, delta,list, Color.WHITE);
		return countButDoNotDrawBalls(list);
	}

    public class VisualiserPanel extends JPanel {
        private int ox;
        private int oy;
        private int delta;
		private ArrayList<HoleViewInfo> holes ;
        
        public VisualiserPanel(int w, int h){
			this.holes = model.getHoles();
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
			
			var countAliveBallsDrawn = drawAliveBalls(g2, ox, oy, delta);
			
			var countBallsKilledByPlayerDrawn = drawKilledBallsByPlayer(g2, ox, oy, delta, 1,true);
			var countBallsKilledByAIDrawn = drawKilledBallsByPlayer(g2, ox, oy, delta, 2,true);
			var countBallsSelfKilledDrawn = drawKilledBallsByNoPlayer(g2, ox, oy, delta,false);

			//fine disegno palline
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(3));
			var pb = model.getHumanBall();
			if (pb != null) {
				drawBall(g2, ox, oy, delta, pb);
			}

			var ai = model.getAiBall();
			if (ai != null) {
				drawBall(g2, ox, oy, delta, ai);
			}

			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(2));

			for (int i=0; i<holes.size(); i++) { //strano warning su questo gruppo di linee linea: Exception in thread "AWT-EventQueue-0" java.util.ConcurrentModificationException
				var h = holes.get(i);
				int x0 = (int)(ox + h.pos().x()*delta);
				int y0 = (int)(oy - h.pos().y()*delta);
				int radiusX = (int)(h.radius()*delta);
				int radiusY = (int)(h.radius()*delta);
				g2.drawOval(x0 - radiusX,y0 - radiusY,radiusX*2,radiusY*2);
			}

			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(1));
			g2.drawString("Num small balls: " + model.getAliveBalls().size(), 20, 40);
			g2.drawString("Frame per sec: " + model.getFramePerSec(), 20, 60);

			g2.drawString("# Num. Alive Balls drawn: " + countAliveBallsDrawn, 20, 80);
			g2.drawString("# Num dead balls: " + model.getDeadBallsForDebug().size(), 20, 120);
			
			g2.drawString("#---Num. killed by no player: " + countBallsSelfKilledDrawn, 20, 140);
			sync.notifyFrameRendered();
			g2.drawString("#---Num. killed by player1: " + countBallsKilledByPlayerDrawn, 20, 160);
			sync.notifyFrameRendered();
			g2.drawString("#---Num. killed by player1: " + countBallsKilledByAIDrawn, 20, 180);
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
