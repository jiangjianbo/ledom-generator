package com.github.ledom.utils;

import com.google.common.collect.Maps;

import java.util.Map;


/**
 * Created by jiangjianbo on 2016/11/12.
 */
public class ScopedMap<K, V> {

    private Map<K,V> maps = Maps.newHashMap();

    private ScopedMap<K,V> parent;
    private String scopeName;

    public ScopedMap(String scopeName, ScopedMap<K, V> parent) {
        this.parent = parent;
        this.scopeName = scopeName;
    }

    public ScopedMap(String scopeName) {
        this.parent = null;
        this.scopeName = scopeName;
    }

    public boolean contains(K key){
        return maps.containsKey(key) ? true : parent == null? false : parent.contains(key);
    }

    public boolean containsLocal(K key){
        return maps.containsKey(key);
    }

    /**
     * gets the value corresponding to the key, at the innermost scope for which there is one;
     * if there is none, returns null
     * @param key
     * @return
     */
    public V get(K key){
        V value = maps.get(key);

        if( value == null && parent != null && !maps.containsKey(key))
            return parent.get(key);

        return value;
    }

    /**
     * puts the key/value pair in at the current scope; if the key is already in at the current nesting level,
     * the new value replaces the old one; neither the key nor the value may be null
     * @param key
     * @param value
     */
    public void put(K key, V value){
        if( maps.containsKey(key) )
            maps.put(key, value);
        else if( parent != null && parent.contains(key) )
            parent.put(key, value);
        else
            maps.put(key, value);
    }

    /**
     * gets the value corresponding to the key at the current scope (not any surrounding one);
     * if there is none, returns null
     * @param key
     * @return
     */
    public V getLocal(K key){
        return maps.get(key);
    }

    public void putLocal(K key, V value){
        maps.put(key, value);
    }

    /**
     * returns the current nesting level
     * @return
     */
    public String getScopeName(){
        return scopeName;
    }


    public static<K, V> ScopedMap<K, V> create(String name) {
        return new ScopedMap<>(name);
    }

    public static<K, V> ScopedMap<K, V> create(String name, ScopedMap<K, V> parent) {
        return new ScopedMap<>(name, parent);
    }
}
