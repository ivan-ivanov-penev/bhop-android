package com.bhop.game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.Collections;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageInputStreamSpi;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.stream.ImageInputStream;

import sun.awt.AppContext;
import sun.security.action.GetPropertyAction;

/**
 * Created by Ivan on 5/2/2016.
 */
public final class ImageFileUtils
{
    private ImageFileUtils() {}

    private static final IIORegistry theRegistry = IIORegistry.getDefaultInstance();

    public static BufferedImage read(InputStream input) throws IOException
    {
        if (input == null)
        {
            throw new IllegalArgumentException("input == null!");
        }

        ImageInputStream stream = createImageInputStream(input);
        BufferedImage bi = read(stream);
        if (bi == null)
        {
            stream.close();
        }
        return bi;
    }
    public static ImageInputStream createImageInputStream(Object input)
            throws IOException
    {
        if (input == null)
        {
            throw new IllegalArgumentException("input == null!");
        }

        Iterator iter;
        // Ensure category is present
        try
        {
            iter = theRegistry.getServiceProviders(ImageInputStreamSpi.class, true);
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }

        boolean usecache = getUseCache() && hasCachePermission();

        while (iter.hasNext())
        {
            ImageInputStreamSpi spi = (ImageInputStreamSpi) iter.next();
            if (spi.getInputClass().isInstance(input))
            {
                try
                {
                    return spi.createInputStreamInstance(input, usecache, getCacheDirectory());
                }
                catch (IOException e)
                {
                    throw new IIOException("Can't create cache file!", e);
                }
            }
        }

        return null;
    }

    public static File getCacheDirectory() {
        return getCacheInfo().getCacheDirectory();
    }

    private static boolean hasCachePermission() {
        Boolean hasPermission = getCacheInfo().getHasPermission();

        if (hasPermission != null) {
            return hasPermission.booleanValue();
        } else {
            try {
                SecurityManager security = System.getSecurityManager();
                if (security != null) {
                    File cachedir = getCacheDirectory();
                    String cachepath;

                    if (cachedir != null) {
                        cachepath = cachedir.getPath();
                    } else {
                        cachepath = getTempDir();

                        if (cachepath == null || cachepath.isEmpty()) {
                            getCacheInfo().setHasPermission(Boolean.FALSE);
                            return false;
                        }
                    }

                    // we have to check whether we can read, write,
                    // and delete cache files.
                    // So, compose cache file path and check it.
                    String filepath = cachepath;
                    if (!filepath.endsWith(File.separator)) {
                        filepath += File.separator;
                    }
                    filepath += "*";

                    security.checkPermission(new FilePermission(filepath, "read, write, delete"));
                }
            } catch (SecurityException e) {
                getCacheInfo().setHasPermission(Boolean.FALSE);
                return false;
            }

            getCacheInfo().setHasPermission(Boolean.TRUE);
            return true;
        }
    }

    private static String getTempDir() {
        GetPropertyAction a = new GetPropertyAction("java.io.tmpdir");
        return (String) AccessController.doPrivileged(a);
    }

    private static synchronized CacheInfo getCacheInfo() {
        AppContext context = AppContext.getAppContext();
        CacheInfo info = (CacheInfo)context.get(CacheInfo.class);
        if (info == null) {
            info = new CacheInfo();
            context.put(CacheInfo.class, info);
        }
        return info;
    }

    static class CacheInfo {
        boolean useCache = true;
        File cacheDirectory = null;
        Boolean hasPermission = null;

        public CacheInfo() {}

        public boolean getUseCache() {
            return useCache;
        }

        public void setUseCache(boolean useCache) {
            this.useCache = useCache;
        }

        public File getCacheDirectory() {
            return cacheDirectory;
        }

        public void setCacheDirectory(File cacheDirectory) {
            this.cacheDirectory = cacheDirectory;
        }

        public Boolean getHasPermission() {
            return hasPermission;
        }

        public void setHasPermission(Boolean hasPermission) {
            this.hasPermission = hasPermission;
        }
    }

    public static boolean getUseCache() {
        return getCacheInfo().getUseCache();
    }

    public static BufferedImage read(ImageInputStream stream)
            throws IOException {
        if (stream == null) {
            throw new IllegalArgumentException("stream == null!");
        }

        Iterator iter = getImageReaders(stream);
        if (!iter.hasNext()) {
            return null;
        }

        ImageReader reader = (ImageReader)iter.next();
        ImageReadParam param = reader.getDefaultReadParam();
        reader.setInput(stream, true, true);
        BufferedImage bi;
        try {
            bi = reader.read(0, param);
        } finally {
            reader.dispose();
            stream.close();
        }
        return bi;
    }

    public static Iterator<ImageReader> getImageReaders(Object input) {
        if (input == null) {
            throw new IllegalArgumentException("input == null!");
        }
        Iterator iter;
        // Ensure category is present
        try {
            iter = theRegistry.getServiceProviders(ImageReaderSpi.class,
                    new CanDecodeInputFilter(input),
                    true);
        } catch (IllegalArgumentException e) {
            return Collections.emptyIterator();
        }

        return new ImageReaderIterator(iter);
    }

    static class ImageReaderIterator implements Iterator<ImageReader> {
        // Contains ImageReaderSpis
        public Iterator iter;

        public ImageReaderIterator(Iterator iter) {
            this.iter = iter;
        }

        public boolean hasNext() {
            return iter.hasNext();
        }

        public ImageReader next() {
            ImageReaderSpi spi = null;
            try {
                spi = (ImageReaderSpi)iter.next();
                return spi.createReaderInstance();
            } catch (IOException e) {
                // Deregister the spi in this case, but only as
                // an ImageReaderSpi
                theRegistry.deregisterServiceProvider(spi, ImageReaderSpi.class);
            }
            return null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    static class CanDecodeInputFilter
            implements ServiceRegistry.Filter {

        Object input;

        public CanDecodeInputFilter(Object input) {
            this.input = input;
        }

        public boolean filter(Object elt) {
            try {
                ImageReaderSpi spi = (ImageReaderSpi)elt;
                ImageInputStream stream = null;
                if (input instanceof ImageInputStream) {
                    stream = (ImageInputStream)input;
                }

                // Perform mark/reset as a defensive measure
                // even though plug-ins are supposed to take
                // care of it.
                boolean canDecode = false;
                if (stream != null) {
                    stream.mark();
                }
                canDecode = spi.canDecodeInput(input);
                if (stream != null) {
                    stream.reset();
                }

                return canDecode;
            } catch (IOException e) {
                return false;
            }
        }
    }
}
