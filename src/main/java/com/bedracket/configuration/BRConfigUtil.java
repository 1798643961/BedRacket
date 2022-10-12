package com.bedracket.configuration;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

public class BRConfigUtil {
    public static File bedracketyml = new File("config/bedracket.yml");
    private static HashMap<String, String> argsConfig = new HashMap<>();

    public static String getString(File f, String key, String defaultReturn) {
        String _key = key.replace(":", "");
        if (argsConfig.containsKey(_key)) return argsConfig.get(_key);
        try {
            for (String line : Files.readAllLines(f.toPath()))
                if (line.contains(key + ":")) {
                    String[] spl = line.split(key + ":");
                    if (spl[1].startsWith(" ")) spl[1] = spl[1].substring(1);
                    return spl[1].replace("'", "").replace("\"", "");
                }
        } catch (Throwable ignored) {
        }
        return defaultReturn;
    }

    public static String sBedRacket(String key, String defaultReturn) {
        return getString(bedracketyml, key, defaultReturn);
    }

    public static Boolean BedRacket(String key, String defaultReturn) {
        return Boolean.parseBoolean(sBedRacket(key, defaultReturn));
    }
}
