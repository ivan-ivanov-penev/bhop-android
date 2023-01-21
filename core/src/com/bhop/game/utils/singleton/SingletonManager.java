package com.bhop.game.utils.singleton;

import com.bhop.game.gameobjects.bunny.Bunny;

import static com.bhop.game.gameobjects.bunny.BunnyIsHitEventWatcher.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Ivan Penev
 *
 */
public final class SingletonManager
{

	private static final Map<Class<?>, Singleton> SINGLETONS = new HashMap<Class<?>, Singleton>();

	private SingletonManager() {}
	
	@SuppressWarnings("unchecked")
    public static <T extends Singleton>T createAndRegisterSingleotnIfMissingInMap(Class<T> clazz) throws Exception
	{
		if (!SINGLETONS.containsKey(clazz))
		{
			Constructor<T> constructor =  clazz.getDeclaredConstructor();
			
			constructor.setAccessible(true);
			
			SINGLETONS.put(clazz, constructor.newInstance());
		}
		
		return (T) SINGLETONS.get(clazz);
	}
	
	public static <T extends Singleton>T getSingleton(Class<T> clazz)
	{
		try
        {
	        return createAndRegisterSingleotnIfMissingInMap(clazz);
        }
        catch (Exception e)
        {
	        e.printStackTrace();
	        
	        return null;
        }
	}
	
	public static void clearSingletons()
	{
		SINGLETONS.clear();
		
		clearWatchers();
	}
	
}
