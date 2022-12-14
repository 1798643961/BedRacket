package com.bedracket.configuration;

public class IntSetting extends Setting<Integer> {
    private Integer value;
    private ConfigBase config;

    public IntSetting(ConfigBase config, String path, Integer def) {
        super(path, def);
        this.value = def;
        this.config = config;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = org.apache.commons.lang3.math.NumberUtils.toInt(value, def);
        config.set(path, this.value);
    }
}

