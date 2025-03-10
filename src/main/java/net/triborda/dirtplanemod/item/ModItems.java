package net.triborda.dirtplanemod.item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.triborda.dirtplanemod.DirtplaneMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DirtplaneMod.MOD_ID);

    public static final RegistryObject<Item> PLANT_MATTER = ITEMS.register("plantmatter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIRT_PICK_AXE = ITEMS.register("dirtpickaxe",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIRT_CLUMP = ITEMS.register("dirtclump",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIRT_STICK = ITEMS.register("dirtstick",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
