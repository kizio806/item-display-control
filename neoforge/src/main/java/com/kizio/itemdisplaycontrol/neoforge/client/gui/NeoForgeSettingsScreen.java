package com.kizio.itemdisplaycontrol.neoforge.client.gui;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.EnumMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class NeoForgeSettingsScreen extends Screen {

    private static final int FULL_BUTTON_WIDTH = 308;
    private static final int COLUMN_BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 18;
    private static final int TEXT_FIELD_HEIGHT = 20;
    private static final int ROW_SPACING = 20;
    private static final int COLUMN_GAP = 8;

    private final Screen parent;
    private final Map<ProtectionTarget, Button> targetButtons = new EnumMap<>(ProtectionTarget.class);

    private Button toggleButton;
    private Button whitelistButton;
    private Button blacklistButton;
    private EditBox whitelistField;
    private EditBox blacklistField;
    private boolean updatingTextFields;

    public NeoForgeSettingsScreen(Screen parent) {
        super(Component.translatable(TranslationKeys.SCREEN_SETTINGS_TITLE));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int fullLeft = this.width / 2 - (FULL_BUTTON_WIDTH / 2);
        int top = this.height / 6 + 28;

        toggleButton = this.addRenderableWidget(Button.builder(Component.empty(), button -> {
            ItemDisplayControl.toggleEnabled();
            refreshButtonLabels();
        }).bounds(fullLeft, top, FULL_BUTTON_WIDTH, BUTTON_HEIGHT).build());

        int index = 0;
        for (ProtectionTarget target : ProtectionTarget.orderedValues()) {
            final ProtectionTarget currentTarget = target;
            int row = index / 2;
            int column = index % 2;
            int x = fullLeft + (column * (COLUMN_BUTTON_WIDTH + COLUMN_GAP));
            int y = top + ROW_SPACING + (row * ROW_SPACING);

            Button button = this.addRenderableWidget(Button.builder(Component.empty(), clickable -> {
                ItemDisplayControl.toggleProtection(currentTarget);
                refreshButtonLabels();
            }).bounds(x, y, COLUMN_BUTTON_WIDTH, BUTTON_HEIGHT).build());
            targetButtons.put(currentTarget, button);
            index++;
        }

        int rows = (ProtectionTarget.orderedValues().size() + 1) / 2;
        int rulesTop = top + ROW_SPACING + (rows * ROW_SPACING) + 8;

        whitelistButton = this.addRenderableWidget(Button.builder(Component.empty(), button -> {
            ItemDisplayControl.toggleWhitelistEnabled();
            refreshButtonLabels();
        }).bounds(fullLeft, rulesTop, FULL_BUTTON_WIDTH, BUTTON_HEIGHT).build());

        whitelistField = this.addRenderableWidget(new EditBox(
                this.font,
                fullLeft,
                rulesTop + ROW_SPACING,
                FULL_BUTTON_WIDTH,
                TEXT_FIELD_HEIGHT,
                Component.translatable(TranslationKeys.GUI_WHITELIST_ITEMS)
        ));
        whitelistField.setMaxLength(1024);
        whitelistField.setHint(Component.literal("minecraft:diamond, minecraft:emerald"));
        setWhitelistFieldText(ItemDisplayControl.getWhitelistItemsText());
        whitelistField.setResponder(this::onWhitelistTextChanged);

        blacklistButton = this.addRenderableWidget(Button.builder(Component.empty(), button -> {
            ItemDisplayControl.toggleBlacklistEnabled();
            refreshButtonLabels();
        }).bounds(fullLeft, rulesTop + (ROW_SPACING * 2) + 4, FULL_BUTTON_WIDTH, BUTTON_HEIGHT).build());

        blacklistField = this.addRenderableWidget(new EditBox(
                this.font,
                fullLeft,
                rulesTop + (ROW_SPACING * 3) + 4,
                FULL_BUTTON_WIDTH,
                TEXT_FIELD_HEIGHT,
                Component.translatable(TranslationKeys.GUI_BLACKLIST_ITEMS)
        ));
        blacklistField.setMaxLength(1024);
        blacklistField.setHint(Component.literal("minecraft:tnt, minecraft:lava_bucket"));
        setBlacklistFieldText(ItemDisplayControl.getBlacklistItemsText());
        blacklistField.setResponder(this::onBlacklistTextChanged);

        this.addRenderableWidget(Button.builder(
                Component.translatable(TranslationKeys.GUI_OPTION_DONE),
                button -> onClose()
        ).bounds(fullLeft, rulesTop + (ROW_SPACING * 4) + 10, FULL_BUTTON_WIDTH, BUTTON_HEIGHT).build());

        refreshButtonLabels();
    }

    @Override
    public void extractRenderState(net.minecraft.client.gui.GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(extractor, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;
        extractor.centeredText(this.font, this.title, centerX, 20, 0xFFFFFF);
        extractor.centeredText(this.font, Component.translatable(TranslationKeys.SCREEN_SETTINGS_SUBTITLE), centerX, 34, 0xA0A0A0);
        extractor.text(this.font, Component.translatable(TranslationKeys.GUI_WHITELIST_ITEMS), this.width / 2 - (FULL_BUTTON_WIDTH / 2), whitelistField.getY() - 12, 0xFFFFFF);
        extractor.text(this.font, Component.translatable(TranslationKeys.GUI_BLACKLIST_ITEMS), this.width / 2 - (FULL_BUTTON_WIDTH / 2), blacklistField.getY() - 12, 0xFFFFFF);
        extractor.centeredText(this.font, Component.translatable(TranslationKeys.GUI_DESC_PRIMARY), centerX, this.height - 48, 0xA0A0A0);
        extractor.centeredText(this.font, Component.translatable(TranslationKeys.GUI_DESC_RULES), centerX, this.height - 36, 0xA0A0A0);
        extractor.centeredText(this.font, Component.translatable(TranslationKeys.GUI_DESC_SECONDARY), centerX, this.height - 24, 0xA0A0A0);
    }

    @Override
    public void onClose() {
        ItemDisplayControl.flushPendingConfiguration();
        if (this.minecraft != null) {
            this.minecraft.setScreenAndShow(parent);
        }
    }

    private void refreshButtonLabels() {
        toggleButton.setMessage(Component.translatable(TranslationKeys.GUI_ENABLED, booleanComponent(ItemDisplayControl.isEnabled())));
        whitelistButton.setMessage(Component.translatable(TranslationKeys.GUI_WHITELIST_TOGGLE, booleanComponent(ItemDisplayControl.isWhitelistEnabled())));
        blacklistButton.setMessage(Component.translatable(TranslationKeys.GUI_BLACKLIST_TOGGLE, booleanComponent(ItemDisplayControl.isBlacklistEnabled())));
        if (whitelistField != null && !whitelistField.isFocused()) {
            String whitelistText = ItemDisplayControl.getWhitelistItemsText();
            if (!whitelistText.equals(whitelistField.getValue())) {
                setWhitelistFieldText(whitelistText);
            }
        }
        if (blacklistField != null && !blacklistField.isFocused()) {
            String blacklistText = ItemDisplayControl.getBlacklistItemsText();
            if (!blacklistText.equals(blacklistField.getValue())) {
                setBlacklistFieldText(blacklistText);
            }
        }
        for (ProtectionTarget target : ProtectionTarget.orderedValues()) {
            Button button = targetButtons.get(target);
            if (button != null) {
                button.setMessage(targetButtonText(target));
            }
        }
    }

    private Component targetButtonText(ProtectionTarget target) {
        return Component.translatable(target.translationKey())
                .append(Component.literal(": "))
                .append(booleanComponent(ItemDisplayControl.isProtectionEnabled(target)));
    }

    private MutableComponent booleanComponent(boolean enabled) {
        return Component.translatable(enabled
                ? TranslationKeys.MESSAGE_ENABLED
                : TranslationKeys.MESSAGE_DISABLED)
                .withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED);
    }

    private void onWhitelistTextChanged(String value) {
        if (!updatingTextFields) {
            ItemDisplayControl.setWhitelistItemsFromText(value);
        }
    }

    private void onBlacklistTextChanged(String value) {
        if (!updatingTextFields) {
            ItemDisplayControl.setBlacklistItemsFromText(value);
        }
    }

    private void setWhitelistFieldText(String value) {
        updatingTextFields = true;
        try {
            whitelistField.setValue(value);
        } finally {
            updatingTextFields = false;
        }
    }

    private void setBlacklistFieldText(String value) {
        updatingTextFields = true;
        try {
            blacklistField.setValue(value);
        } finally {
            updatingTextFields = false;
        }
    }
}
