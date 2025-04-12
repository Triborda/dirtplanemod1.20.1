package net.triborda.dirtplanemod.item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.triborda.dirtplanemod.DirtplaneMod;
import net.triborda.dirtplanemod.item.custom.DirtScraperItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DirtplaneMod.MOD_ID);

    public static final RegistryObject<Item> PLANT_MATTER = ITEMS.register("plant_matter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIRT_CLUMP = ITEMS.register("dirt_clump",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIRT_STICK = ITEMS.register("dirt_stick",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> DIRT_PICK_AXE = ITEMS.register("dirt_pickaxe",
            () -> new PickaxeItem(ModToolTiers.DIRT, 1, 0, new Item.Properties()));
    public static final RegistryObject<Item> DIRT_SHOVEL = ITEMS.register("dirt_shovel",
            () -> new ShovelItem(ModToolTiers.DIRT, 1, 0, new Item.Properties()));
    public static final RegistryObject<Item> DIRT_SCRAPER = ITEMS.register("dirt_scraper",
            () -> new DirtScraperItem(new Item.Properties().durability(5)));

    public static final RegistryObject<Item> DIRT_SAPLING = ITEMS.register("dirt_sapling",
            () -> new Item(new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
