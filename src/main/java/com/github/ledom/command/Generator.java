package com.github.ledom.command;

import com.github.ledom.Architecture;
import com.github.ledom.runtime.GeneratorCore;

/**
 * Hello world!
 *
 */
public class Generator
{
    public static void main( String[] args )
    {
        System.out.println( "LEDOM Generator" );

        GeneratorCore engine = new GeneratorCore();
        engine.run();
    }
}
