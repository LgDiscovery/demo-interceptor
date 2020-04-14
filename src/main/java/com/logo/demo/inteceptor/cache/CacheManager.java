package com.logo.demo.inteceptor.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存管理器，单例模式
 */
public class CacheManager {

    private static volatile CacheManager instance = null;

    //缓存数据
    private Map<String,Object> cacheData;

    private final static Object lock = new Object();

    //私有
    private CacheManager(){
        this.cacheData = new ConcurrentHashMap<>();
    }

    //获取单例
    public CacheManager getInstance(){
        if(instance == null){
            synchronized (lock){//初始化并发争用锁的情况很少出现，所以不必锁方法
                if(instance == null){
                    instance = new CacheManager();
                }
            }
        }
        return instance;
    }

    /**
     * 文件缓存
     * @param fileNameKey 文件名
     * @param loaderCalsss FileCacheLoader 子类继承该抽象类 重写该方法 自定义 key value
     * @return
     * @throws Exception
     */
    public synchronized Object getData(String fileNameKey,Class loaderCalsss) throws Exception{
        String cacheKey = fileNameKey;
        FileCacheLoader loader = null;
        if(loaderCalsss != null){
            loader = (FileCacheLoader) loaderCalsss.newInstance();
            cacheKey = loader.buildCacheKey(fileNameKey);
        }

        if(cacheData.containsKey(cacheKey)){
            return cacheData.get(cacheKey); //取数据
        }else{
            if(loader==null){
                return null;
            }else{
                Object cache = loader.createForCache(fileNameKey);
                if(cache != null){
                    cacheData.put(cacheKey,cache); //放数据
                }
                return cache;
            }
        }
    }
}
