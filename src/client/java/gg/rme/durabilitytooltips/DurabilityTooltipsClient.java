package gg.rme.durabilitytooltips;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gg.rme.durabilitytooltips.config.ModConfig;

import java.util.List;

public class DurabilityTooltipsClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("RME's Durability Tooltips");

    @Override
    public void onInitializeClient() {

        ModConfig.loadConfig();

        ModConfig config = ModConfig.getInstance();

        ItemTooltipCallback.EVENT.register((ItemStack stack, Item.TooltipContext context, TooltipType type, List<Text> lines) -> {
            if (!type.isAdvanced() && config.isTooltipEnabled()) {
                if (stack.getMaxDamage() == 0
                        || (!config.isTooltipEnabledWhenEmpty() && stack.getMaxDamage() - stack.getDamage() <= 0)
                        || (!config.isTooltipEnabledWhenFull() && !stack.isDamaged())) {
                    return;
                }

                lines.add(Text.empty());
                lines.add(TooltipHandler.getTooltip(stack));
            }
        });

        LOGGER.info("[RME's Durability Tooltips] Mod loaded!");
    }
}
