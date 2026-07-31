package Roma.menu.skillmenu;

import Roma.menu.stats.ModStats;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;

public class SkillMenu extends Screen {
    private static final ResourceLocation BACKGROUND_TEXTURE =
            new ResourceLocation("rma", "textures/gui/backround.png");
    private static final ResourceLocation ICON_TEXTURE =
            new ResourceLocation("rma", "textures/gui/icon.png");
    private static final ResourceLocation BAR_EMPTY_TEXTURE =
            new ResourceLocation("rma", "textures/gui/barempty.png");
    private static final ResourceLocation BAR_FILLED_TEXTURE =
            new ResourceLocation("rma", "textures/gui/bar.png");

    private final int imageWidth = 512;
    private final int imageHeight = 480;

    private final int iconSize = 32;
    private final int barWidth = 120;
    private final int barHeight = 16;

    private static final int MAX_LEVEL = 20;

    private int leftPos;
    private int topPos;

    public record SkillDefinition(String name, Stat<?> stat, IntUnaryOperator xpCurve) {}
    private final List<SkillDefinition> skillDefs = new ArrayList<>();

    public SkillMenu() {
        super(Component.literal("Skill Menu"));

        // Slot 0: Spellcasting | Base 100, +30% per level (Maxes at ~14.6k)
        if (ModStats.MAGIC_USED != null && ModStats.MAGIC_USED.isPresent()) {
            skillDefs.add(new SkillDefinition("Spellcasting", Stats.CUSTOM.get(ModStats.MAGIC_USED.get()),
                    x -> (int) (100 * Math.pow(1.30, x - 1))));
        }

        // Slot 1: Combat | Base 25, +37% per level (Maxes at ~9.8k)
        if (ModStats.CUSTOM_MOBS_KILLED != null && ModStats.CUSTOM_MOBS_KILLED.isPresent()) {
            skillDefs.add(new SkillDefinition("Combat", Stats.CUSTOM.get(ModStats.CUSTOM_MOBS_KILLED.get()),
                    x -> (int) (25 * Math.pow(1.37, x - 1))));
        }

        // Slot 2: Enchanting (Coming Soon)
        skillDefs.add(new SkillDefinition("Enchanting(Coming Soon)", Stats.CUSTOM.get(Stats.BELL_RING),
                x -> 100));

        // Slot 3: Mining | Base 50, +40% per level (Maxes at ~29.8k blocks)
        skillDefs.add(new SkillDefinition("Mining", Stats.CUSTOM.get(ModStats.XP_MINED.get()),
                x -> (int) (50 * Math.pow(1.40, x - 1))));

        // Slot 4: Coming Soon
        skillDefs.add(new SkillDefinition("Coming Soon", Stats.CUSTOM.get(Stats.BELL_RING),
                x -> 100));

        // Slot 5: Crafting | Base 30, +35.7% per level (Maxes perfectly at ~10,022)
        skillDefs.add(new SkillDefinition("Crafting", Stats.CUSTOM.get(ModStats.CUSTOM_ITEMS_CRAFTED.get()),
                x -> (int) (30 * Math.pow(1.357, x - 1))));

        // Slot 6: Fishing | Base 30, +35.7% per level (Maxes at ~10k)
        skillDefs.add(new SkillDefinition("Fishing", Stats.CUSTOM.get(Stats.FISH_CAUGHT),
                x -> (int) (30 * Math.pow(1.357, x - 1))));

        // Slot 7: Farming | Base 50, +32% per level (Maxes at ~9.7k)
        if (ModStats.CUSTOM_PLANTS_BROKEN != null && ModStats.CUSTOM_PLANTS_BROKEN.isPresent()) {
            skillDefs.add(new SkillDefinition("Farming", Stats.CUSTOM.get(ModStats.CUSTOM_PLANTS_BROKEN.get()),
                    x -> (int) (50 * Math.pow(1.32, x - 1))));
        }
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);

        guiGraphics.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        guiGraphics.drawCenteredString(this.font, this.title, this.leftPos + (this.imageWidth / 2), this.topPos + 15, 0xFFFFFF);

        renderSkillSlots(guiGraphics);

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void renderSkillSlots(GuiGraphics guiGraphics) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.getStats() == null) return;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int slotsToRender = Math.min(skillDefs.size(), 8);
        for (int i = 0; i < slotsToRender; i++) {
            SkillDefinition skill = skillDefs.get(i);
            int col = i % 2;
            int row = i / 2;

            int slotX = this.leftPos + 40 + (col * 234);
            int slotY = this.topPos + 40 + (row * 112);

            int rawStatValue = 0;
            if (skill.stat() != null) {
                rawStatValue = player.getStats().getValue(skill.stat());
            }

            // Calculate level with MAX_LEVEL cap of 20
            int level = 1;
            int remainingXp = rawStatValue;
            int xpForNextLevel = Math.max(1, skill.xpCurve().applyAsInt(level));

            while (remainingXp >= xpForNextLevel && level < MAX_LEVEL) {
                remainingXp -= xpForNextLevel;
                level++;
                xpForNextLevel = Math.max(1, skill.xpCurve().applyAsInt(level));
            }

            float progress;
            String levelText;
            if (level >= MAX_LEVEL) {
                progress = 1.0F; // Keep bar fully charged at Level 20
                levelText = "Lvl 20 (MAX)";
            } else {
                progress = (float) remainingXp / (float) xpForNextLevel;
                progress = Math.max(0.0F, Math.min(1.0F, progress));
                levelText = "Lvl " + level;
            }

            // A. Draw Icon Frame
            guiGraphics.blit(ICON_TEXTURE, slotX, slotY, 0, 0, this.iconSize, this.iconSize, this.iconSize, this.iconSize);

            // B. Draw Spell Name
            guiGraphics.drawString(this.font, skill.name(), slotX + 42, slotY, 0xDDDDDD, false);

            // C. Draw Empty Progress Bar
            int barX = slotX + 40;
            int barY = slotY + 16;
            guiGraphics.blit(BAR_EMPTY_TEXTURE, barX, barY, 0, 0, this.barWidth, this.barHeight, this.barWidth, this.barHeight);

            // D. Draw Filled Progress Bar
            int filledWidth = (int) (progress * (float) this.barWidth);
            if (filledWidth > 0) {
                guiGraphics.blit(BAR_FILLED_TEXTURE, barX, barY, 0, 0, filledWidth, this.barHeight, this.barWidth, this.barHeight);
            }

            // E. Draw Level Indicator
            guiGraphics.drawString(this.font, levelText, slotX + 128, slotY + 2, 0xFFAA00, false);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}