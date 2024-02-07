package net.sf.packtag.cache.provider;

import jakarta.servlet.ServletContext;
import net.sf.packtag.cache.Resource;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.util.HashSet;
import java.util.Set;

public class JCacheProvider extends AbstractCacheProvider {

    private String resourcesAbsolutePathCacheName;
    private String resourcesMappedPathCacheName;

    private Cache<String, Resource> resourcesAbsolutePath;
    private Cache<String, Resource> resourcesMappedPath;

    @Override
    public void init(final ServletContext context) {
        super.init(context);

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        String prefix = "net.sf.packtag.cache.provider.JCacheProvider";

        this.resourcesAbsolutePathCacheName = String.format("%s.%s", prefix, "packtagResourcesAbsolutePath");
        this.resourcesMappedPathCacheName = String.format("%s.%s", prefix, "packtagResourcesMappedPath");

        this.resourcesAbsolutePath = cacheManager.createCache(this.resourcesAbsolutePathCacheName, new MutableConfiguration<String, Resource>());
        this.resourcesMappedPath = cacheManager.createCache(this.resourcesMappedPathCacheName, new MutableConfiguration<String, Resource>());
    }

    @Override
    public Set getAbsolutePathKeys() {
        Set<String> set = new HashSet<String>();
        for (Cache.Entry<String, Resource> item : resourcesAbsolutePath){
            set.add(item.getKey());
        }
        return set;
    }

    @Override
    public Set getMappedPathKeys() {
        Set<String> set = new HashSet<String>();
        for (Cache.Entry<String, Resource> item : resourcesMappedPath){
            set.add(item.getKey());
        }
        return set;
    }

    @Override
    public Resource getResourceByAbsolutePath(String absolutePath) {
        boolean exists = resourcesAbsolutePath.containsKey(absolutePath);
        if (exists){
            return resourcesAbsolutePath.get(absolutePath);
        }
        return null;
    }

    @Override
    public Resource getResourceByMappedPath(String mappedPath) {
        boolean exists = resourcesMappedPath.containsKey(mappedPath);
        if (exists){
            return resourcesMappedPath.get(mappedPath);
        }
        return null;
    }

    @Override
    public boolean existResource(String absolutePath) {
        return resourcesAbsolutePath.containsKey(absolutePath);
    }

    @Override
    public void store(Resource resource, boolean clearDependingCombinedResources) {
        resourcesAbsolutePath.put(resource.getAbsolutePath(), resource);
        resourcesMappedPath.put(resource.getMappedPath(), resource);

        if (clearDependingCombinedResources) {
            clearDependingCombinedResources(resource);
        }
    }

    @Override
    public void clearCache() {
        this.resourcesAbsolutePath.removeAll();
        this.resourcesMappedPath.removeAll();
    }

    @Override
    public void removeAbsolutePath(String absolutePath) {
        resourcesAbsolutePath.remove(absolutePath);
    }

    @Override
    public void removeMappedPath(String mappedPath) {
        resourcesMappedPath.remove(mappedPath);
    }
}
