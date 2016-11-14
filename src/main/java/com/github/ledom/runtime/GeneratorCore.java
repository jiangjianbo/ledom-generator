package com.github.ledom.runtime;

import com.github.ledom.*;
import com.github.ledom.output.ConsoleOutputBuffer;
import com.github.ledom.utils.ScopedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jiangjianbo on 2016/11/11.
 */
public class GeneratorCore {
    private static ScopedMap<String, Loader> defaultLoaders = ScopedMap.create("default");
    private ConfigurationLoader configLoader;

    public void run() {
        checkValidation();

        // 装入配置
        Configuration cfg = configLoader.load();
        assert cfg != null;

        // 创建并初始化环境
        Environment env = cfg.createEnvironment();

        env.initLoaders(cfg, getDefaultLoaders());
        env.loadVars(cfg);
        env.loadFormatters(cfg);
        env.loadProcessors(cfg);

        Architecture arch = env.loadArchitecture(cfg);
        //env.loadEventHandlers(cfg, arch);

        // 创建 Stage 和名字的索引关系
        Stage[] stages = arch.getDefinedStages();
        final Map<String, Stage> stageNames = Stream.of(stages)
                .collect(Collectors.toMap(stage -> stage.getName(), stage -> stage));

        // 创建 Stage 和 Processor 的关联关系
        Map<Stage, List<Processor>> stageProcessors = Maps.newHashMap();
        env.getProcessors().forEach(proc -> {
            Stage stag = stageNames.get(proc.getStage());
            List<Processor> list = stageProcessors.get(stag);
            if (list == null) {
                list = Lists.newArrayList();
                stageProcessors.put(stag, list);
            }

            list.add(proc);
        });

        // 循环处理每一个 Stage，并调用 Processor 进行处理
        Stream.of(stages).forEach(stage -> {
            Environment stageScope = env.createChildScope();

            OutputBuffer output = stage.createDefaultOutput();
            stageScope.setDefaultOutput(output);

            try {
                stageProcessors.get(stage).forEach(proc -> {
                    // todo 这里需要每一个 process 用独立的 作用域吗？
                    proc.process(stageScope.createChildScope(), stage);
                });

                if( output != null )
                    output.flush();
            } finally {
                if( output != null )
                    output.close();
            }
        });
    }


    /**
     * 检查各种参数是否已经配置合理
     */
    private void checkValidation() {
        Validate.notNull(this.configLoader);

    }


    public ScopedMap<String, Loader> getDefaultLoaders() {
        return defaultLoaders;
    }

    public void setDefaultLoaders(ScopedMap<String, Loader> defaultLoaders) {
        this.defaultLoaders = defaultLoaders;
    }

    public ConfigurationLoader getConfigLoader() {
        return configLoader;
    }

    public void setConfigLoader(ConfigurationLoader configLoader) {
        this.configLoader = configLoader;
    }




}
