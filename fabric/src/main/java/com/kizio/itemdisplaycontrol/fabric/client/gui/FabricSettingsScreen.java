package com.kizio.itemdisplaycontrol.fabric.client.gui;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.EnumMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public final class FabricSettingsScreen extends Screen {

    private static final int FULL_BUTTON_WIDTH = 308;
    private static final int COLUMN_BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 18;
    private static final int TEXT_FIELD_HEIGHT = 20;
    private static final int ROW_SPACING = 20;
    private static final int COLUMN_GAP = 8;

    private final Screen parent;
    private final Map<ProtectionTarget, ButtonWidget> targetButtons = new EnumMap<>(ProtectionTarget.class);

    private ButtonWidget toggleButton;
    private ButtonWidget whitelistButton;
    private ButtonWidget blacklistButton;
    private TextFieldWidget whitelistField;
    private TextFieldWidget blacklistField;
    private boolean updatingTextFields;

    public FabricSettingsScreen(Screen parent) {
        super(Text.translatable(TranslationKeys.SCREEN_SETTINGS_TITLE));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int fullLeft = this.width / 2 - (FULL_BUTTON_WIDTH / 2);
        int top = this.height / 6 + 28;

        toggleButton = this.addDrawableChild(ButtonWidget.builder(Text.empty(), button -> {
            ItemDisplayControl.toggleEnabled();
            refreshButtonLabels();
        }).dimensions(fullLeft, top, FULL_BUTTON_WIDTH, BUTTON_HEIGHT).build());

        int index = 0;
        for (ProtectionTarget target : ProtectionTarget.orderedValues()) {
            final ProtectionTarget currentTarget = target;
            int row = index / 2;
            int column = index % 2;
            int x = fullLeft + (column * (COLUMN_BUTTON_WIDTH + COLUMN_GAP));
            int y = top + ROW_SPACING + (row * ROW_SPACING);

            ButtonWidget button = this.addDrawableChild(ButtonWidget.builder(Text.empty(), clickable -> {
                ItemDisplayControl.toggleProtection(currentTarget);
                refreshButtonLabels();
            }).dimensions(x, y, COLUMN_BUTTON_WIDTH, BUTTON_HEIGHT).build());
            targetButtons.put(currentTarget, button);
            index++;
        }

        int rows = (ProtectionTarget.orderedValues().size() + 1) / 2;
        int rulesTop = top + ROW_SPACING + (rows * ROW_SPACING) + 8;

        whitelistButton = this.addDrawableChild(ButtonWidget.builder(Text.empty(), button -> {
            ItemDisplayControl.toggleWhitelistEnabled();
            refreshButtonLabels();
        }).dimensions(fullLeft, rulesTop, FULL_BUTTON_WIDTH, BUTTON_HEIGHT).build());

        whitelistField = this.addDrawableChild(new TextFieldWidget(
                this.textRenderer,
                fullLeft,
                rulesTop + ROW_SPACING,
                FULL_BUTTON_WIDTH,
                TEXT_FIELD_HEIGHT,
                Text.translatable(TranslationKeys.GUI_WHITELIST_ITEMS)
        ));
        whitelistField.setMaxLength(1024);
        whitelistField.setSuggestion("minecraft:diamond, minecraft:emerald");
        setWhitelistFieldText(ItemDisplayControl.getWhitelistItemsText());
        whitelistField.setChangedListener(this::onWhitelistTextChanged);

        blacklistButton = this.addDrawableChild(ButtonWidget.builder(Text.empty(), button -> {
            ItemDisplayControl.toggleBlacklistEnabled();
            refreshButtonLabels();
        }).dimensions(fullLeft, rulesTop + (ROW_SPACING * 2) + 4, FULL_BUTTON_WIDTH, BUTTON_HEIGHT).build());

        blacklistField = this.addDrawableChild(new TextFieldWidget(
                this.textRenderer,
                fullLeft,
                rulesTop + (ROW_SPACING * 3) + 4,
                FULL_BUTTON_WIDTH,
                TEXT_FIELD_HEIGHT,
                Text.translatable(TranslationKeys.GUI_BLACKLIST_ITEMS)
        ));
        blacklistField.setMaxLength(1024);
        blacklistField.setSuggestion("minecraft:tnt, minecraft:lava_bucket");
        setBlacklistFieldText(ItemDisplayControl.getBlacklistItemsText());
        blacklistField.setChangedListener(this::onBlacklistTextChanged);

        this.addDrawableChild(ButtonWidget.builder(
                Text.translatable(TranslationKeys.GUI_OPTION_DONE),
                button -> close()
        ).dimensions(fullLeft, rulesTop + (ROW_SPACING * 4) + 10, FULL_BUTTON_WIDTH, BUTTON_HEIGHT).build());

        refreshButtonLabels();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderInGameBackground(context);
        super.render(context, mouseX, mouseY, delta);

        int centerX = this.width / 2;
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, centerX, 20, 0xFFFFFF);
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.translatable(TranslationKeys.SCREEN_SETTINGS_SUBTITLE),
                centerX,
                34,
                0xA0A0A0
        );
        context.drawTextWithShadow(
                this.textRenderer,
                Text.translatable(TranslationKeys.GUI_WHITELIST_ITEMS),
                this.width / 2 - (FULL_BUTTON_WIDTH / 2),
                whitelistField.getY() - 12,
                0xFFFFFF
        );
        context.drawTextWithShadow(
                this.textRenderer,
                Text.translatable(TranslationKeys.GUI_BLACKLIST_ITEMS),
                this.width / 2 - (FULL_BUTTON_WIDTH / 2),
                blacklistField.getY() - 12,
                0xFFFFFF
        );
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.translatable(TranslationKeys.GUI_DESC_PRIMARY),
                centerX,
                this.height - 48,
                0xA0A0A0
        );
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.translatable(TranslationKeys.GUI_DESC_RULES),
                centerX,
                this.height - 36,
                0xA0A0A0
        );
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.translatable(TranslationKeys.GUI_DESC_SECONDARY),
                centerX,
                this.height - 24,
                0xA0A0A0
        );
    }

    @Override
    public void close() {
        ItemDisplayControl.flushPendingConfiguration();
        if (this.client != null) {
            this.client.setScreen(parent);
        }
    }

    private void refreshButtonLabels() {
        toggleButton.setMessage(Text.translatable(TranslationKeys.GUI_ENABLED, booleanText(ItemDisplayControl.isEnabled())));
        whitelistButton.setMessage(Text.translatable(TranslationKeys.GUI_WHITELIST_TOGGLE, booleanText(ItemDisplayControl.isWhitelistEnabled())));
        blacklistButton.setMessage(Text.translatable(TranslationKeys.GUI_BLACKLIST_TOGGLE, booleanText(ItemDisplayControl.isBlacklistEnabled())));
        if (whitelistField != null && !whitelistField.isFocused()) {
            String whitelistText = ItemDisplayControl.getWhitelistItemsText();
            if (!whitelistText.equals(whitelistField.getText())) {
                setWhitelistFieldText(whitelistText);
            }
        }
        if (blacklistField != null && !blacklistField.isFocused()) {
            String blacklistText = ItemDisplayControl.getBlacklistItemsText();
            if (!blacklistText.equals(blacklistField.getText())) {
                setBlacklistFieldText(blacklistText);
            }
        }
        for (ProtectionTarget target : ProtectionTarget.orderedValues()) {
            ButtonWidget button = targetButtons.get(target);
            if (button != null) {
                button.setMessage(targetButtonText(target));
            }
        }
    }

    private Text targetButtonText(ProtectionTarget target) {
        return Text.translatable(target.translationKey())
                .append(Text.literal(": "))
                .append(booleanText(ItemDisplayControl.isProtectionEnabled(target)));
    }

    private Text booleanText(boolean enabled) {
        return Text.translatable(enabled
                ? TranslationKeys.MESSAGE_ENABLED
                : TranslationKeys.MESSAGE_DISABLED)
                .formatted(enabled ? Formatting.GREEN : Formatting.RED);
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
            whitelistField.setText(value);
        } finally {
            updatingTextFields = false;
        }
    }

    private void setBlacklistFieldText(String value) {
        updatingTextFields = true;
        try {
            blacklistField.setText(value);
        } finally {
            updatingTextFields = false;
        }
    }
}
