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
import edu.propra.bomberman.gameengine.actions.PlayerBombUpAction;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class BombUpItem extends GameObject {
	public static Area			collisionArea	= null;
	public static Area			clipArea		= null;
	public static BufferedImage	image			= null;

	public BombUpItem(int x, int y) {

		AffineTransform trans = new AffineTransform();
		trans.setToTranslation(x, y);
		// Construct Graphics Subgraph for Player Object
		this.go = new SGTransform();
		((SGTransform) this.go).getTransform().setTransform(trans);
		SGImage leaf = new SGImage(image);
		leaf.setClipArea(clipArea);
		((SGTransform) this.go).addChild(leaf);

		// Construct Collision Object for Player Object
		this.co = new CollisionObject();
		co.setPrivot(this);

		this.absTransform = (AffineTransform) trans.clone();
	}
	
	
	@Override
	public void collisionWith(Object a) {
		if(a instanceof Player){
			SGameEngine.get().addAction(new PlayerBombUpAction((Player)a,this));
		}
	}

	@Override
	public void initializeCollisions() {
		if (this.isAbsIntialized) {
			this.co.setCollisionArea(collisionArea.createTransformedArea(this.absTransform));
			this.collisionsInitialized = true;
		} else {
			System.err.println("Item.initializeCollisions()");
			System.err.println("  Absolute positions are not initialized");
		}
		
	}
	
	static {
		collisionArea = new Area(new Rectangle(0, 0, 40, 40));
		clipArea = new Area(new Rectangle(0, 0, 40, 40));
		try {
			image = ImageIO.read(new File("src/resources/item.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}