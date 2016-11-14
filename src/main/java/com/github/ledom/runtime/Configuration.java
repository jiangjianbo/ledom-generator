package com.github.ledom.runtime;

import com.github.ledom.Architecture;
import com.github.ledom.Formatter;
import com.github.ledom.Loader;
import com.github.ledom.Processor;

import java.util.Map;

/**
 * Created by jiangjianbo on 2016/11/11.
 */
public abstract class Configuration {
    public Environment createEnvironment() {
        return new Environment();
    }

    public abstract LoaderDefinition[] getLoaders();
    public abstract VarDefinition[] getVars();
    public abstract FormatterDefinition[] getFormatters();
    public abstract ProcessorDefinition[] getProcessors();

    public abstract ArchitectureDefinition getArchitecture();

    public static class LoaderDefinition {
        public final String name;
        public final Class<? extends Loader> loaderClass;
        /**
         * 关联的文件后缀，不带点号，例如： txt， sh， bat等
         */
        public final String[] extensions;
        public final Map<String, Object> properties;

        public LoaderDefinition(String name, Class<? extends Loader> loaderClass, String[] extensions) {
            this(name, loaderClass, extensions, null);
        }

        public LoaderDefinition(String name, Class<? extends Loader> loaderClass, String[] extensions, Map<String, Object> properties) {
            this.name = name;
            this.loaderClass = loaderClass;
            this.extensions = extensions;
            this.properties = properties;
        }
    }

    /**
     * 要支持从文件中读取，直接在配置文件中定义单变量。例如
     * <pre>
     *     &lt;var name="abc" value="cde" loader="var" /&gt;
     *     &lt;var loader="file" value="/home/abc/a.txt" /&gt;
     *     &lt;var file="/home/abc/a.txt" /&gt;
     * </pre>
     */
    public static  class VarDefinition {
        public final  String name;
        public final  String loader;
        public final  String file;
        public final String value;

        public VarDefinition(String loader, String name, String value, String file) {
            this.name = name;
            this.loader = loader;
            this.file = file;
            this.value = value;
        }
    }

    public static  class FormatterDefinition {
        public final  String name;
        public final  Class<? extends Formatter> formatterClass;
        public final Map<String, Object> properties;

        public FormatterDefinition(String name, Class<? extends Formatter> formatterClass) {
            this(name, formatterClass, null);
        }

        public FormatterDefinition(String name, Class<? extends Formatter> formatterClass, Map<String, Object> properties) {
            this.name = name;
            this.formatterClass = formatterClass;
            this.properties = properties;
        }
    }

    public static  class ProcessorDefinition {
        public final String name;
        public final String stage;
        public final Class<? extends Processor> processorClass;
        public final Map<String, Object> properties;

        public ProcessorDefinition(String name, String stage, Class<? extends Processor> processorClass) {
            this(name, stage, processorClass, null);
        }

        public ProcessorDefinition(String name, String stage, Class<? extends Processor> processorClass, Map<String, Object> properties) {
            this.name = name;
            this.stage = stage;
            this.processorClass = processorClass;
            this.properties = properties;
        }
    }

    public static class ArchitectureDefinition {
        public final String name;
        public final Class<? extends Architecture> architectureClass;
        public final EventHandlerDefinition[] handlers;
        public final Map<String, Object> properties;

        public ArchitectureDefinition(String name, Class<? extends Architecture> architectureClass) {
            this(name, architectureClass, null, null);
        }

        public ArchitectureDefinition(String name, Class<? extends Architecture> architectureClass, EventHandlerDefinition[] handlers) {
            this(name, architectureClass, handlers, null);
        }

        public ArchitectureDefinition(String name, Class<? extends Architecture> architectureClass, EventHandlerDefinition[] handlers,
                                      Map<String, Object> properties) {
            this.name = name;
            this.architectureClass = architectureClass;
            this.handlers = handlers;
            this.properties = properties;
        }
    }

    public static class EventHandlerDefinition {
        public final Class<? extends EventHandlerDefinition> eventHandlerClass;
        public final Map<String, Object> properties;

        public EventHandlerDefinition(Class<? extends EventHandlerDefinition> eventHandlerClass) {
            this(eventHandlerClass, null);
        }

        public EventHandlerDefinition(Class<? extends EventHandlerDefinition> eventHandlerClass,
                                      Map<String, Object> properties) {
            this.eventHandlerClass = eventHandlerClass;
            this.properties = properties;
        }
    }
}
