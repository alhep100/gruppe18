package edu.propra.bomberman.gameengine.objects;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.propra.bomberman.collisionengine.CollisionObject;
import edu.propra.bomberman.gameengine.SGameEngine;
import edu.propra.bomberman.gameengine.actions.BombUpAction;
import edu.propra.bomberman.graphicengine.SGAnimation;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class Bomb extends GameObject {
	public static Area			collisionArea	= null;
	public static Area			clipArea		= null;
	public static BufferedImage[]	images			= null;
	private BombUpAction		action;
	public Player				owner;

	public Bomb(Player owner, int x, int y) {
		this.owner = owner;
		AffineTransform trans = new AffineTransform();
		trans.setToTranslation(x, y);
		// Construct Graphics Subgraph for Player Object
		this.go = new SGTransform();
		((SGTransform) this.go).getTransform().setTransform(trans);
		SGAnimation leaf = new SGAnimation(images,2000);
		leaf.setClipArea(clipArea);
		((SGTransform) this.go).addChild(leaf);

		// Construct Collision Object for Player Object
		this.co = new CollisionObject();
		co.setPrivot(this);

		this.absTransform = (AffineTransform) trans.clone();
	}

	private boolean	done	= false;

	@Override
	public void collisionWith(Object a) {
		if (a instanceof Explosion) {
			SGameEngine.get().removeAction(this.action);
			this.action.action();
		} else if (a instanceof Bomb) {
			if (!removed) {
				if (this.action.getTime() < ((Bomb) a).action.getTime()) return;
				removed = true;
				SGameEngine.get().removeObject(this);
				SGameEngine.get().removeAction(this.action);
				this.owner.bombUp();
				done = true;
			}
		} else if (a instanceof FixedBlock) {
			if (!removed) repositionBomb(((FixedBlock) a).getCo().getCollisionArea());
		} else if (a instanceof IceBlock) {
			if (!removed) repositionBomb(((IceBlock) a).getCo().getCollisionArea());
		} else if (a instanceof Wall) {
			if (!removed) repositionBomb(((Wall) a).getCo().getCollisionArea());
		} else {
			// System.out.println("Collision between "+this.toString()+" and "+
			// a.toString());
		}
	}

	private boolean	removed			= false;
	private int		collisionCount	= 0;
	public boolean	playerOut		= false;

	public void repositionBomb(Area colArea) {
		Area intersection = (Area) this.getCo().getCollisionArea().clone();
		intersection.intersect(colArea);
		Rectangle intersBounds = intersection.getBounds();
		Rectangle myBounds = this.co.getCollisionArea().getBounds();
		collisionCount++;
		//
		double diffX = myBounds.getCenterX() - intersBounds.getCenterX();
		double diffY = myBounds.getCenterY() - intersBounds.getCenterY();
		if (diffY == 0 && diffX == 0) {
			// remove Bomb;
			removed = true;
			SGameEngine.get().removeObject(this);
			SGameEngine.get().removeAction(this.action);
			this.owner.bombUp();
			collisionCount = 0;
		} else if (Math.abs(diffX) < Math.abs(diffY) || diffX == 0) {
			// move vertical
			if (diffY > 0) {
				// move bomb up
				this.absTransform.translate(0, intersection.getBounds2D().getHeight());
				((SGTransform) this.go).getTransform().translate(0, intersection.getBounds2D().getHeight());
				this.co.setCollisionArea(Bomb.collisionArea.createTransformedArea(this.absTransform));
			} else {
				// move bomb down
				this.absTransform.translate(0, -intersection.getBounds2D().getHeight());
				((SGTransform) this.go).getTransform().translate(0, -intersection.getBounds2D().getHeight());
				this.co.setCollisionArea(Bomb.collisionArea.createTransformedArea(this.absTransform));
			}
		} else if (Math.abs(diffX) > Math.abs(diffY) || diffY == 0) {
			// move horizontal
			if (diffX > 0) {
				// move bomb left x--
				this.absTransform.translate(intersection.getBounds2D().getWidth(), 0);
				((SGTransform) this.go).getTransform().translate(intersection.getBounds2D().getWidth(), 0);
				this.co.setCollisionArea(Bomb.collisionArea.createTransformedArea(this.absTransform));
			} else {
				// move bomb right x++
				this.absTransform.translate(-intersection.getBounds2D().getWidth(), 0);
				((SGTransform) this.go).getTransform().translate(-intersection.getBounds2D().getWidth(), 0);
				this.co.setCollisionArea(Bomb.collisionArea.createTransformedArea(this.absTransform));
			}
		} else {
			// remove bomb
			removed = true;
			SGameEngine.get().removeObject(this);
			SGameEngine.get().removeAction(this.action);
			this.owner.bombUp();
			collisionCount = 0;
		}

	}

	@Override
	public void initializeCollisions() {
		if (this.isAbsIntialized) {
			this.co.setCollisionArea(collisionArea.createTransformedArea(this.absTransform));
			this.collisionsInitialized = true;
		} else {
			System.err.println("FixedBlock.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}

	}

	public BombUpAction getAction() {
		return action;
	}

	public void setAction(BombUpAction action) {
		this.action = action;
	}

	static {
		collisionArea = new Area(new Rectangle(8, 6, 29, 29));
		clipArea = new Area(new Rectangle(0, 0, 40, 40));
		images = new BufferedImage[52];
		try {
			for(int x=0;x<52;x++){
				images[x] = ImageIO.read(new File("src/resources/bombe"+x+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
