package com.logo.demo.inteceptor.cache;

/**
 * 文件缓存抽象类
 */
public abstract class FileCacheLoader {

    /**
     * 自定义构建缓存对象value
     * @param fileName 文件名
     * @return
     * @throws Exception
     */
    public abstract Object createForCache(String fileName) throws Exception;

    /**
     * 自定义构建缓存key
     * @param fileName 文件名
     * @return
     */
    public abstract String buildCacheKey(String fileName);
}
