package main;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Arrays;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class StrategyManager {

    private History parameters;
    private String strategyName;

    public StrategyManager(History parameters) {

        this.strategyName = parameters.getSelfName();
        this.parameters = parameters;

    }

    public String readStrategyClass() {
        String toReturn = null;

        Class<?> classStrategy = null;
        Object objectStrategy;

        if (strategyName.contains("original")) {
            strategyName = strategyName.substring(0, strategyName.indexOf(" (original)"));
        } else if (strategyName.contains(" (built-in)")) {
            strategyName = strategyName.substring(0, strategyName.indexOf(" (built-in)"));
        }
        try {
            classStrategy = Class.forName("strategies.original." + strategyName);
        } catch (ClassNotFoundException e1) {
            try {
                classStrategy = Class.forName("strategies.custom." + strategyName);
            } catch (ClassNotFoundException e2) {
                try {
                    classStrategy = Class.forName("strategies.built_in." + strategyName);
                } catch (ClassNotFoundException e3) {
                    try {
                        File sourceFile = new File("src/strategies/custom/" + strategyName + ".java");
                        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
                        StandardJavaFileManager fileManager = compiler.getStandardFileManager(
                                null, null, null);

                        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays
                                .asList(new File("out/production/ThePrisonersDilemmaProject/")));
                        compiler.getTask(null, fileManager, null, null, null,
                                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile)))
                                .call();
                        fileManager.close();

                        classStrategy = Class.forName("strategies.custom." + strategyName);
                    } catch (ClassNotFoundException e4) {
                        e4.printStackTrace();
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            }
        }


        try {
            Method method = classStrategy.getDeclaredMethod("calculate", History.class);
            objectStrategy = classStrategy.newInstance();
            Object s = method.invoke(objectStrategy, parameters);
            toReturn = (String) s;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
