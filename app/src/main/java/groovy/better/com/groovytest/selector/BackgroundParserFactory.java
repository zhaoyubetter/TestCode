package groovy.better.com.groovytest.selector;


import java.lang.reflect.Constructor;

import groovy.better.com.groovytest.selector.inflater.IBackgroundInflater;

/**
 * Created by hufeng7 on 2017/1/6
 */

public class BackgroundParserFactory {
    public static IBackgroundInflater getParser(String name) {
        try {
            Class<? extends IBackgroundInflater> clazz = (Class<? extends IBackgroundInflater>) Class.forName(name);
            Constructor<? extends IBackgroundInflater> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("%s not found", name));
        } catch (ClassCastException e) {
            throw new RuntimeException(String.format("%s must implement %s", name, IBackgroundInflater.class.getName()));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("%s does not have an empty constructor", name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
