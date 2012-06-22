package edu.propra.bomberman.graphicengine;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class SGTransform extends SGNode implements IParent {
	private SGNode			child;
	private AffineTransform	transform;

	public SGTransform() {
		transform = new AffineTransform();
	}

	@Override
	public void PaintRecursive(AffineTransform transform, Graphics2D g2d) {
		transform.concatenate(this.transform);
		if (child != null) child.PaintRecursive(transform, g2d);
	}

	public SGNode getChild() {
		return child;
	}

	public AffineTransform getTransform() {
		return transform;
	}

	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}

	@Override
	public void addChild(Object child) {
		if (this.child != null) this.removeChild(null);
		this.child = (SGNode) child;
		this.child.setParent(this);
	}

	@Override
	public void removeChild(Object child) {
		this.child.setParent(null);
		this.child = null;
	}

	@Override
	public void releaseAll() {
		if(this.child!=null)this.child.releaseAll();
		this.child.setParent(null);
		this.child=null;
	}

}
