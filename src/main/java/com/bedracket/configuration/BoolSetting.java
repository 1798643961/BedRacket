package com.bedracket.configuration;

import org.apache.commons.lang3.BooleanUtils;

public class BoolSetting extends Setting<Boolean> {
    private Boolean value;
    private ConfigBase config;

    public BoolSetting(ConfigBase config, String path, Boolean def) {
        super(path, def);
        this.value = def;
        this.config = config;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = BooleanUtils.toBooleanObject(value);
        this.value = this.value == null ? def : this.value;
        config.set(path, this.value);
    }
}
