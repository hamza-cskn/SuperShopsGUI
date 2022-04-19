package mc.obliviate.supershops.utils;

import mc.obliviate.supershops.utils.message.MessageUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }


    public ItemBuilder setDamage(final short dmg) {
        item.setDurability(dmg);
        return this;
    }

    public ItemBuilder setDamage(final int dmg) {
        setDamage((short) dmg);
        return this;
    }


    public ItemBuilder setName(final String name) {
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.parseColor(name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(final List<String> lore) {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(MessageUtils.parseColor(lore));
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(final String... lore) {
        return setLore(new ArrayList<>(Arrays.asList(lore)));
    }

    public ItemBuilder appendLore(final List<String> appendLore) {
        List<String> lore = item.getItemMeta().getLore();
        if (lore != null) lore.addAll(appendLore);
        else lore = appendLore;
        return setLore(lore);
    }

    public ItemBuilder enchant(final Enchantment ench) {
        return enchant(ench, ench.getStartLevel());
    }

    public ItemBuilder enchant(final Map<Enchantment, Integer> enchantments) {
        for (Map.Entry<Enchantment, Integer> enchant : enchantments.entrySet()) {
            enchant(enchant.getKey(), enchant.getValue());
        }
        return this;
    }

    public ItemBuilder enchant(final Enchantment ench, final int value) {
        if (item.getType().equals(Material.ENCHANTED_BOOK)) {
            final EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();

            if (meta == null) return this;

            meta.addStoredEnchant(ench, value, true);

            item.setItemMeta(meta);
        } else {
            item.addUnsafeEnchantment(ench, value);
        }
        return this;
    }

    public ItemBuilder appendLore(final String... appendLore) {
        return appendLore(new ArrayList<>(Arrays.asList(appendLore)));
    }

    public ItemBuilder setAmount(final int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder hideFlags(final ItemFlag itemFlag) {
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(itemFlag);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hideFlags() {
        hideFlags(ItemFlag.HIDE_ATTRIBUTES);
        return this;
    }


    public ItemStack build() {
        return item;
    }

}

