package com.github.ledom.runtime;

import com.github.ledom.*;
import com.github.ledom.utils.BeanUtils;
import com.github.ledom.utils.ConfigurationErrorException;
import com.github.ledom.utils.PathUtils;
import com.github.ledom.utils.ScopedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by jiangjianbo on 2016/11/11.
 */
public class Environment {
    private final Environment parent;
    final ScopedMap<String, Object> vars;
    private ScopedMap<String, Formatter> formatters = ScopedMap.create("global");

    private List<Processor> processors = Lists.newArrayList();

    private ScopedMap<String, Loader> loaders = null;
    private Map<Class<? extends Loader>, String> loaderClassIndex = Maps.newHashMap();
    private Map<String, String> loaderExtIndex = Maps.newHashMap();
    private OutputBuffer defaultOutput = null;

    public Environment() {
        this.parent = null;
        vars = ScopedMap.create("global");
    }

    public Environment(Environment parent) {
        this.parent = parent;
        vars = ScopedMap.create("", parent.vars);
    }

    /**
     * 装入 Loader 定义，建立 别名映射，类名到别名映射， 文件类型到别名映射。
     * @param cfg
     * @param defaultLoaders
     */
    public void initLoaders(Configuration cfg, ScopedMap<String, Loader> defaultLoaders) {
        loaders = new ScopedMap("global", defaultLoaders);

        Stream.of(cfg.getLoaders()).forEach(pair -> {
            Class<? extends Loader> clazz = pair.loaderClass;

            if( loaders.contains(pair.name) )
                throw new ConfigurationErrorException("duplicated loader name " + pair.name);
            else
                loaders.putLocal(pair.name, BeanUtils.instantiate(clazz));

            if( loaderClassIndex.containsKey(clazz) )
                throw new ConfigurationErrorException("loader " + clazz + " already used by alias " + loaderClassIndex.get(clazz));
            else
                loaderClassIndex.put(clazz, pair.name);

            // 扩展名注册会被后续覆盖掉
            if( pair.extensions != null && pair.extensions.length > 0) {
                for (String ext : pair.extensions) {
                    // TODO: 这里添加日志警告
                    loaderExtIndex.put(ext, pair.name);
                }
            }
        });
    }

    /**
     * 从文件中装入变量
     * @param cfg
     */
    public void loadVars(Configuration cfg) {
        Stream.of(cfg.getVars()).forEach(info->{
            // 先当简写，直接获取loader
            Loader loader = getLoader(info);
            if( loader == null )
                throw new ConfigurationErrorException("loader not found: " + info.loader + " and " + info.file);

            loader.loadVars(vars, info.name, info.value, info.file);
        });
    }

    /**
     * 获取 Loader， 首先把 loader 当作别名，然后当作 formatterClass，如果没有loader，则将文件后缀查找 loader
     * @param info
     * @return
     */
    private Loader getLoader(Configuration.VarDefinition info) {
        // 首先检查 loader，是否是别名或者class，如果没有，则检查文件后缀

        Loader loader = null;
        if( info.loader != null ) {
            loader = loaders.get(info.loader);
            if (loader == null) {
                // 转化为 class ，尝试获取
                Class<Loader> loaderClass = BeanUtils.toClass(info.loader);
                if (loaderClass == null)
                    throw new ConfigurationErrorException(
                            "loader class of var " + info.name + " could not instantiate: " + info.loader);

                String loaderName = loaderClassIndex.get(loaderClass);
                if (loaderName != null)
                    loader = loaders.get(loaderName);
                else {
                    loader = BeanUtils.instantiate(loaderClass);
                    String key = RandomStringUtils.randomAlphanumeric(10);
                    loaders.put(key, loader);
                    loaderClassIndex.put(loaderClass, key);
                }
            }
        }else if( info.file != null ) {
            String ext = PathUtils.extractExt(info.file);
            if( loaderExtIndex.containsKey(ext) ){
                loader = loaders.get(loaderExtIndex.get(ext));
            }else
                throw new ConfigurationErrorException("unknown file ext " + ext );
        }

        return loader;
    }

    /**
     * 装入格式化器
     * @param cfg
     */
    public void loadFormatters(Configuration cfg) {
        Stream.of(cfg.getFormatters()).forEach(info -> {
            formatters.putLocal(info.name, BeanUtils.instantiate(info.formatterClass));
        });
    }

    /**
     * 装入所有的处理器
     * @param cfg
     */
    public void loadProcessors(Configuration cfg) {
        Stream.of(cfg.getProcessors()).forEach(info -> {
            Processor proc = BeanUtils.instantiate(info.processorClass);
            proc.setName(info.name);
            proc.setStage(info.stage);

            processors.add(proc);
        });
    }

    /**
     * 装入架构定义
     * @param cfg
     * @return
     */
    public Architecture loadArchitecture(Configuration cfg) {
        Configuration.ArchitectureDefinition archDef = cfg.getArchitecture();

        Architecture arch = BeanUtils.instantiate(archDef.architectureClass);

        if( archDef.handlers != null )
            for(Configuration.EventHandlerDefinition handler : archDef.handlers)
                arch.addEventListener((EventHandler)BeanUtils.instantiate(handler.eventHandlerClass));

        return arch;
    }

    /**
     * 装入所有的架构事件监听器
     * @param cfg
     * @param arch
     */
    public void loadEventHandlers(Configuration cfg, Architecture arch) {

    }

    public List<Processor> getProcessors() {
        return processors;
    }

    public Environment createChildScope() {
        return new Environment(this);
    }

    public OutputBuffer createBuffer(Stage stage) {
        return null;
    }


    public<T> T getVar(String var) {
        return (T)this.vars.get(var);
    }

    public<T> T getVar(String var, T defVal) {
        T value = (T)this.vars.get(var);

        return value == null? defVal : value;
    }

    public OutputBuffer getDefaultOutput() {
        return defaultOutput == null && parent != null? parent.getDefaultOutput(): defaultOutput;
    }

    public void setDefaultOutput(OutputBuffer defaultOutput) {
        this.defaultOutput = defaultOutput;
    }
}
