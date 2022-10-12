package com.bedracket;

import com.bedracket.configuration.BRConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BedRacket implements ModInitializer {

    public static final String MODID = "bedracket";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        new BRConfig();
    }
}
