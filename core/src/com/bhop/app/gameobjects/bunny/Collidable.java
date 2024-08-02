package com.bhop.app.gameobjects.bunny;

import com.bhop.app.gameobjects.PixelLocation;

import java.util.Set;

public interface Collidable
{
	
	public Set<PixelLocation> getImagePixelLocations();
	
	public float getX();
	
	public float getY();
	
	public int getImageWidth();

	public int getImageHeight();

}
