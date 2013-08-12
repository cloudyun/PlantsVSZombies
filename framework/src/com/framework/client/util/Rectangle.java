package com.framework.client.util;

public class Rectangle {

	public int x;

	public int y;

	public int width;

	public int height;

	public Rectangle() {
	}

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean contains(int x, int y) {
		return (x >= this.x) && (y >= this.y)
				&& ((x - this.x) < width && (y - this.y) < height);
	}

	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}

	public String toString() {
		return "left: " + x + " top: " + y + " width: " + width + " height: "
				+ height;
	}
}
