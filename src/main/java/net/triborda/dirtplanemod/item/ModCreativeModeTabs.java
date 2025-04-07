package net.triborda.dirtplanemod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.triborda.dirtplanemod.DirtplaneMod;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DirtplaneMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DIRT_PLANE = CREATIVE_MODE_TABS.register("dirt_plane_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PLANT_MATTER.get()))
                    .title(Component.translatable("creativetab.dirt_plane_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        for(Item item : ModItems.ITEMS.getEntries().stream()
                                .map(RegistryObject::get) // Extract the actual Item from RegistryObject<Item>
                                .sorted(Comparator.comparing(Item::toString)) // Replace with your preferred sorting logic
                                .toList()){
                            pOutput.accept((item));
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
