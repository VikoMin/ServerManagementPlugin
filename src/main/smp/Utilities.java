package smp;

import arc.Core;

import java.util.*;

import static java.lang.Long.parseLong;
import static java.lang.Runtime.getRuntime;

public class Utilities {
    public static <T> T notNullElse(T value, T value2){
        return value != null ? value : value2;
    }
    public static boolean canParseLong(String s){
        return parseLong(s) != Long.MIN_VALUE;
    }

    public static void makeCoreSettingString(String settingInputText, String settingName){
        if (Objects.equals(Core.settings.getString(settingName), "none") || Core.settings.getString(settingName) == null) {
            Scanner inp = new Scanner(System.in);
            System.out.println(settingInputText);
            String settingValue = inp.next();
            Core.settings.put(settingName, settingValue);
        }
    }

    public static void makeCoreSettingLong(String settingInputText, String settingName){
        if (Objects.equals(Core.settings.getLong(settingName), 0L) || Core.settings.getLong(settingName) == null) {
            Scanner inp = new Scanner(System.in);
            System.out.println(settingInputText);
            Long settingValue = inp.nextLong();
            Core.settings.put(settingName, settingValue);
        }
    }

    public static String joinArrayString(String[] array, int startFrom){
        StringBuilder string = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(array));
        arrayList.remove(arrayList.size()-1);
        for (String str : arrayList){
            if (arrayList.indexOf(str) >= startFrom) {
                if (arrayList.indexOf(str) != arrayList.size()-1) {
                    string.append(str).append(" ");
                } else {
                    string.append(str);
                }
            }
        }
        return string.toString();
    }

    public static void exit(){
        Core.app.exit();
        getRuntime().exit(0);
    }

    public static <T, K> HashMap<T, K> createHashMap(T[] keys, K[] values){
        HashMap<T, K> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++){
            map.put(keys[i], values[i]);
        }
        return map;
    }

    public static <T, K> HashMap<T, K> createHashMap(T key, K value){
        HashMap<T, K> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
