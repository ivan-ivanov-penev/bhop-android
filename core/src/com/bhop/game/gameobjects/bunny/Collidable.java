package com.bhop.game.gameobjects.bunny;

import com.bhop.game.gameobjects.PixelLocation;

import java.util.Set;

public interface Collidable
{
	
	public Set<PixelLocation> getImagePixelLocations();
	
	public float getX();
	
	public float getY();
	
	public int getImageWidth();

	public int getImageHeight();

}
