package main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
                    e3.printStackTrace();
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
