package com.github.ledom.runtime;

import com.github.ledom.Architecture;
import com.github.ledom.EventHandler;
import com.github.ledom.Processor;
import com.github.ledom.Stage;
import com.github.ledom.stage.DefaultStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by jiangjianbo on 2016/11/13.
 */
public class GeneratorHelloWorldTest extends AbstractGeneratorTest {

    @Before
    public void setUp() throws Exception {
        this.configClass = TestHelloConfig.class;
        super.setUp();
    }

    @Test
    public void run() throws Exception {
        generator.run();
    }

    static public class TestHelloConfig extends TestConfig {

        public TestHelloConfig(){
            vars = new VarDefinition[]{
                    new VarDefinition("var", "hello", "Hello!World!", null)
            };

            processors = new ProcessorDefinition[]{
                    new ProcessorDefinition("raw", "one", GeneratorHelloWorldTest.TestHelloProcessor.class)
            };
        }

        @Override
        public ArchitectureDefinition getArchitecture() {
            return new ArchitectureDefinition("test", GeneratorHelloWorldTest.TestHelloArchitecture.class);
        }

    }

    public static class TestHelloProcessor extends Processor {
        @Override
        public void process(Environment env, Stage stage) {
            env.getDefaultOutput().println((String)env.getVar("hello"));
        }
    }

    public static class TestHelloArchitecture implements Architecture {
        @Override
        public Stage[] getDefinedStages() {
            return new Stage[]{
                new DefaultStage("one")
            };
        }

        @Override
        public void addEventListener(EventHandler handler) {

        }
    }
}