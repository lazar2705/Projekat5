public class RectangleCollider implements Collidable {
	
    private double x;
    private double y;
    private double width;
    private double height;
    

public RectangleCollider (double x ,double y, double width, double height) {
	
	if (width <= 0) {
		throw new IllegalArgumentException();
	}
	if (height <= 0) {
		throw new IllegalArgumentException();
	}
	this.x=x;
	this.y=y;
	this.width=width;
	this.height=height;
	
}


public double getX() {
	return x;
}
public void setX(double x) {this.x = x;}
public double getY() {return y;}
public void setY(double y) {this.y = y;}
public double getWidth() {return width;}
public void setWidth(double width) {
	if (width<=0) {
		throw new IllegalArgumentException("Sirina mora biti veca od nule!");
	}
	this.width=width;
}
public double getHeight() {return height;}
public void setHeight(double height) {
	if (height<=0) {
		throw new IllegalArgumentException("Visina mora biti veca od nule!");
	}
	this.height=height;
}
@Override
public boolean intersects(Collidable other) {
    if (other instanceof RectangleCollider rectangle) {
        return this.x < rectangle.x + rectangle.width &&
               this.x + this.width > rectangle.x &&
               this.y < rectangle.y + rectangle.height &&
               this.y + this.height > rectangle.y;
    } else if (other instanceof CircleCollider) {
        return other.intersects(this);
    }
    return false;
}

@Override
public String toString() {
    return String.format("Rectangle[%.1f,%.1f %.1fx%.1f]", x, y, width, height);
}


}
