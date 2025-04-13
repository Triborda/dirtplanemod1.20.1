package net.triborda.dirtplanemod.menu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.triborda.dirtplanemod.DirtplaneMod;
import net.triborda.dirtplanemod.menu.custom.DirtWorkbenchMenu;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, DirtplaneMod.MOD_ID);

    public static final RegistryObject<MenuType<DirtWorkbenchMenu>> DIRT_WORKBENCH_MENU =
            MENUS.register("dirt_workbench_menu",
                    () -> IForgeMenuType.create((windowId, inv, data) -> {
                        var pos = data.readBlockPos();
                        var level = inv.player.level();
                        return new DirtWorkbenchMenu(windowId, inv, null, level, pos);
                    }));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
