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
import edu.propra.bomberman.gameengine.actions.PlayerDeadAction;
import edu.propra.bomberman.gameengine.actions.PlayerWonAction;
import edu.propra.bomberman.graphicengine.SGImage;
import edu.propra.bomberman.graphicengine.SGTransform;

public class Player extends GameObject implements Moveable {
	public static Area				collisionArea;
	public static Area				clipArea;
	public static BufferedImage[][]	images;
	public static BufferedImage		deathImage;
	private boolean					death		= false;
	private int						bombCounter	= 3;
	private MovingData				data;
	private int						bombMax		= 3;
	private String					name;

	public Player(int x, int y, String name, int type) {
		this.name = name;

		AffineTransform trans = new AffineTransform();
		trans.setToTranslation(x, y);

		// Construct Graphics Subgraph for Player Object
		this.go = new SGTransform();
		((SGTransform) this.go).getTransform().setTransform(trans);
		// SGAnimation leaf=new SGAnimation(images, 1000);
		// leaf.setRepeat(false);

		SGImage leaf = new SGImage(images[type][0]);
		// ToDo:
		// Zwischenvariable lastkey speichert die Bewegung /Action Objekt
		// das Blatt soll gezeichnet werden aus dem Animationsabschnitt welches
		// zu der Richtung zeigt
		leaf.setClipArea(clipArea);
		((SGTransform) this.go).addChild(leaf);

		// Construct Collision Object for Player Object
		this.co = new CollisionObject();
		co.setPrivot(this);

		leaf.debugColl = this.co;

		this.absTransform = (AffineTransform) trans.clone();
		// Initialize Data to make Player Object moveable
		data = new MovingData(this);
		data.setActTrans(leaf.getActTrans());
		data.setSpeed(5);

	}

	@Override
	public void collisionWith(Object a) {
		if (!death) {
			if (a instanceof Explosion) {
				this.data.block();
				SGameEngine.get().addAction(new PlayerDeadAction(this));
			} else if (a instanceof Exit) {
				SGameEngine.get().addAction(new PlayerWonAction(this));
			} else {
				// System.out.println("Collision between "+this.toString()+" and "+

			}
		}
	}

	@Override
	public MovingData getMovingData() {
		return this.data;
	}

	static {
		collisionArea = new Area(new Rectangle(13, 6, 13, 28));
		clipArea = new Area(new Rectangle(0, 0, 40, 40));
		images = new BufferedImage[2][4];

		try {
			images[0][0] = ImageIO.read(new File("src/resources/player_front.png"));
			images[0][1] = ImageIO.read(new File("src/resources/player_back.png"));
			images[0][2] = ImageIO.read(new File("src/resources/player_left.png"));
			images[0][3] = ImageIO.read(new File("src/resources/player_right.png"));
			images[1][0] = ImageIO.read(new File("src/resources/enemy_front.png"));
			images[1][1] = ImageIO.read(new File("src/resources/enemy_back.png"));
			images[1][2] = ImageIO.read(new File("src/resources/enemy_left.png"));
			images[1][3] = ImageIO.read(new File("src/resources/enemy_right.png"));
			deathImage = ImageIO.read(new File("src/resources/asche.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Bomb bombDown() {
		if (bombCounter > 0) {
			bombCounter--;
			data.now = true;
			int dir = this.data.getDirection();
			double x = 0, y = 0;
			return new Bomb(this, (int) (this.absTransform.getTranslateX() + collisionArea.getBounds().x + x), (int) (this.absTransform.getTranslateY() + collisionArea.getBounds().y + y));
		}
		return null;
	}

	public void bombUp() {
		if (bombCounter <= bombMax) bombCounter++;
	}

	@Override
	public void initializeCollisions() {
		if (this.isAbsIntialized) {
			this.co.setCollisionArea(collisionArea.createTransformedArea(this.absTransform));
			this.collisionsInitialized = true;
		} else {
			System.err.println("Player.initializeCollisions()");
			System.err.println("    AbsolutePositions are not initialized");
		}
	}

	@Override
	public void ConditionChanged(int cond) {

	}

	public void isDead(boolean b) {
		this.death = b;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void bombCountUp() {
		this.bombCounter++;
		this.bombMax++;
	}
}
