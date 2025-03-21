package net.triborda.dirtplanemod.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RemoveItemModifier extends LootModifier {
    public static final Supplier<Codec<RemoveItemModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec()
            .fieldOf("item").forGetter(m -> m.item)).apply(inst, RemoveItemModifier::new)));
    private final Item item;

    public RemoveItemModifier(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for(LootItemCondition condition : this.conditions) {
            if(!condition.test(context)) {
                return generatedLoot;
            }
        }
        // Create a new list to store the modified loot (without the item to remove)
        ObjectArrayList<ItemStack> newLoot = new ObjectArrayList<>();

        for (ItemStack loot : generatedLoot) {
            // If the loot item is not the one to remove, add it to the new loot list
            if (!loot.is(this.item)) {
                newLoot.add(loot);
            }
        }

        return newLoot; // Return the new loot list without the removed item
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}