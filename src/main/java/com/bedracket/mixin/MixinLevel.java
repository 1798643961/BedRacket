package com.bedracket.mixin;

import com.bedracket.configuration.BRConfig;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class MixinLevel {

    @Inject(method = "getProfiler", at = @At("HEAD"), cancellable = true)
    private void configMethodProfiler(CallbackInfoReturnable<ProfilerFiller> cir) {
        if (BRConfig.INSTANCE.disableMethodProfiler.getValue()) {
            cir.setReturnValue(net.minecraft.util.profiling.InactiveProfiler.INSTANCE);
        }
    }
}
