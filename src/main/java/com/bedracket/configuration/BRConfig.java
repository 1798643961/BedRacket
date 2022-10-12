package com.bedracket.configuration;

import com.bedracket.BedRacket;
import com.mohistmc.yaml.file.YamlConfiguration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class BRConfig extends ConfigBase {

    public static BRConfig INSTANCE;
    private final String HEADER = "This is the main configuration file for BedRacket.\n";
    public final BoolSetting enableBooks = new BoolSetting(this, "enable-books", true);
    public final BoolSetting disableOutOfOrderChat = new BoolSetting(this, "misc.disable-out-of-order-chat", false);
    public final BoolSetting disableMethodProfiler = new BoolSetting(this, "misc.disable-method-profiler", true);

    public BRConfig() {
        super("bedracket.yml");
        init();
        INSTANCE = this;
    }

    public static void setValue(String key, String value) {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(BRConfigUtil.bedracketyml);
        yml.set(key, value);
        try {
            yml.save(BRConfigUtil.bedracketyml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setValue(String key, boolean value) {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(BRConfigUtil.bedracketyml);
        yml.set(key, value);
        try {
            yml.save(BRConfigUtil.bedracketyml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getStringList0(String path, List<String> defValue) {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(BRConfigUtil.bedracketyml);
        yml.addDefault(path, defValue);
        return yml.getStringList(path);
    }

    public static boolean getBoolean0(String path, boolean defValue) {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(BRConfigUtil.bedracketyml);
        return yml.getBoolean(path, defValue);
    }

    public void init() {
        for (Field f : this.getClass().getFields()) {
            if (Modifier.isFinal(f.getModifiers()) && Modifier.isPublic(f.getModifiers()) && !Modifier.isStatic(f.getModifiers())) {
                try {
                    Setting setting = (Setting) f.get(this);
                    if (setting == null) {
                        continue;
                    }
                    settings.put(setting.path, setting);
                } catch (ClassCastException ignored) {

                } catch (Throwable t) {
                    System.out.println("[BedRacket] Failed to initialize a BRConfig setting.");
                    t.printStackTrace();
                }
            }
        }
        load();
    }

    @Override
    public void load() {
        try {
            config = YamlConfiguration.loadConfiguration(configFile);
            StringBuilder header = new StringBuilder(HEADER + "\n");
            for (Setting toggle : settings.values()) {
                config.addDefault(toggle.path, toggle.def);
                settings.get(toggle.path).setValue(config.getString(toggle.path));
            }

            version = getInt("config-version", 1);
            set("config-version", 1);
            config.options().header(header.toString());
            config.options().copyDefaults(true);
            this.save();
        } catch (Exception ex) {
            BedRacket.LOGGER.info("Could not load " + this.configFile);
            ex.printStackTrace();
        }
    }
}
