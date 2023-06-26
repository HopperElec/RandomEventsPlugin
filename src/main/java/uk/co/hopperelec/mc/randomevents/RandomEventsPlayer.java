package uk.co.hopperelec.mc.randomevents;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class RandomEventsPlayer implements InventoryHolder {
    @NotNull private final Player spigotPlayer;
    @Nullable private PotionEffectType lastPotionEffect;
    @NotNull private static final Set<RandomEventsPlayer> allPlayers = new HashSet<>();

    private RandomEventsPlayer(@NotNull Player spigotPlayer) {
        this.spigotPlayer = spigotPlayer;
    }

    public static @NotNull RandomEventsPlayer getRandomEventsPlayer(@NotNull Player spigotPlayer) {
        for (RandomEventsPlayer randomEventsPlayer : allPlayers) {
            if (randomEventsPlayer.spigotPlayer == spigotPlayer) {
                return randomEventsPlayer;
            }
        }
        final RandomEventsPlayer randomEventsPlayer = new RandomEventsPlayer(spigotPlayer);
        allPlayers.add(randomEventsPlayer);
        return randomEventsPlayer;
    }

    public void clearPotionEffect() {
        if (lastPotionEffect != null) {
            spigotPlayer.removePotionEffect(lastPotionEffect);
            lastPotionEffect = null;
        }
    }

    public void setPotionEffect(@NotNull PotionEffect potionEffect) {
        clearPotionEffect();
        lastPotionEffect = potionEffect.getType();
        spigotPlayer.addPotionEffect(potionEffect);
    }

    public void sendMessage(@NotNull String message) {
        spigotPlayer.sendMessage(message);
    }

    public void setBlock(@NotNull Material type) {
        getLocation().getBlock().setType(type);
    }

    public void giveItem(@NotNull ItemStack itemStack) {
        getInventory().addItem(itemStack);
    }

    public void spawnEntity(@NotNull EntityType entityType) {
        getWorld().spawnEntity(getLocation(), entityType);
    }

    public void teleport(@NotNull Location location) {
        spigotPlayer.teleport(location);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return spigotPlayer.getInventory();
    }

    public @NotNull Location getLocation() {
        return spigotPlayer.getLocation();
    }

    public @NotNull World getWorld() {
        return spigotPlayer.getWorld();
    }
}