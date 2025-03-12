package net.triborda.dirtplanemod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.triborda.dirtplanemod.DirtplaneMod;

import java.awt.*;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DirtplaneMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DIRT_PLANE = CREATIVE_MODE_TABS.register("dirt_plane_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PLANT_MATTER.get()))
                    .title(Component.translatable("creativetab.dirt_plane_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.DIRT_CLUMP.get());
                        pOutput.accept(ModItems.DIRT_PICK_AXE.get());
                        pOutput.accept(ModItems.DIRT_SHOVEL.get());
                        pOutput.accept(ModItems.DIRT_STICK.get());
                        pOutput.accept(ModItems.PLANT_MATTER.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
