package com.github.ledom.runtime;

import com.github.ledom.utils.BeanUtils;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jiangjianbo on 2016/11/14.
 */
public class AbstractGeneratorTest {
    GeneratorCore generator = null;
    Class<? extends TestConfig> configClass = null;

    @Before
    public void setUp() throws Exception {
        generator = new GeneratorCore();

        generator.setConfigLoader(()-> BeanUtils.instantiate(configClass));
        generator.setDefaultLoaders(null);
    }

    @After
    public void tearDown() throws Exception {

    }

    static abstract class TestConfig extends Configuration {

        LoaderDefinition[] loaders = null;

        @Override
        public LoaderDefinition[] getLoaders() {
            ArrayList<LoaderDefinition> loaders = getLoaderList();
            return loaders.toArray(new LoaderDefinition[0]);
        }

        protected ArrayList<LoaderDefinition> getLoaderList() {
            ArrayList<LoaderDefinition> list = new ArrayList<>();
            list.add(
                new LoaderDefinition("var", com.github.ledom.loader.DefaultVarLoader.class, null)
            );
            if( loaders != null )
                Collections.addAll(list, loaders);

            return list;
        }

        VarDefinition[] vars = null;

        @Override
        public VarDefinition[] getVars() {
            ArrayList<VarDefinition> vars = getVarList();
            return vars.toArray(new VarDefinition[0]);
        }

        protected ArrayList<VarDefinition> getVarList() {
            ArrayList<VarDefinition> list = new ArrayList<>();

            if(vars != null )
                Collections.addAll(list, vars);
            return list;
        }

        FormatterDefinition[] formatters = null;

        @Override
        public FormatterDefinition[] getFormatters() {
            ArrayList<FormatterDefinition> loaders = getFormatterList();
            return loaders.toArray(new FormatterDefinition[0]);
        }

        protected ArrayList<FormatterDefinition> getFormatterList() {
            ArrayList<FormatterDefinition> list = new ArrayList<>();
            list.add(
                    new FormatterDefinition("text", com.github.ledom.formater.NullFormatter.class)
            );
            if( formatters != null )
                Collections.addAll(list, formatters);
            return list;
        }

        ProcessorDefinition[] processors = null;

        @Override
        public ProcessorDefinition[] getProcessors() {
            ArrayList<ProcessorDefinition> loaders = getProcessorList();
            return loaders.toArray(new ProcessorDefinition[0]);
        }

        protected ArrayList<ProcessorDefinition> getProcessorList() {
            ArrayList<ProcessorDefinition> list = new ArrayList<>();

            if( processors != null )
                Collections.addAll(list, processors);

            return list;
        }


        public abstract ArchitectureDefinition getArchitecture();
    }
}
