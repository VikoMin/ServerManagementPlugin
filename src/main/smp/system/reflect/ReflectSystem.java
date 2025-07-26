package smp.system.reflect;

import arc.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class ReflectSystem {

    public static List<Class<?>> exportClasses(String pckg) throws IOException {
        String dir = pckg.replace(".", "/");
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(ReflectSystem.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        Stream<String> stream = jarFile.stream()
                .filter(jarEntry -> jarEntry.getName().endsWith(".class") && jarEntry.getName().startsWith(dir) && !jarEntry.getName().contains("$"))
                .map(jarEntry -> jarEntry.getName().replace("/", ".")
                        .replaceAll("\\.class$", ""));

        for (Object className : stream.toArray()){
            try {
                Class<?> cls = Class.forName((String) className);
                classes.add(cls);
            } catch (ClassNotFoundException e) {
                Log.err("Class is not found");
            }
        }
        return classes;
    }

    public static <T> Object getFieldParam(String name, T object){
        try {
            Field field = object.getClass().getField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Log.err("Couldn't get field!");
        }
        return null;
    }
}
