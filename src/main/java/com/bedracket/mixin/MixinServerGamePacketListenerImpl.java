package com.bedracket.mixin;

import com.bedracket.configuration.BRConfig;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.Instant;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinServerGamePacketListenerImpl {

    @Inject(method = "updateChatOrder", at = @At("HEAD"), cancellable = true)
    private void ConfigOutOfOrderChat(Instant timestamp, CallbackInfoReturnable<Boolean> cir) {
        if (BRConfig.INSTANCE.disableOutOfOrderChat.getValue()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "handleEditBook", at = @At("HEAD"), cancellable = true)
    private void ConfigEnableBook(ServerboundEditBookPacket packet, CallbackInfo ci) {
        if (!BRConfig.INSTANCE.enableBooks.getValue()) {
            ci.cancel();
        }
    }
}
