package minezaddon;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin
  implements Listener
{
    public void onEnable(){
        regCommands();
        getLogger().info("插件加载成功");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }
  public void regCommands()
  {
    getCommand("lore").setExecutor(new Lore());
    getCommand("il").setExecutor(new Lore());
  }
        @EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		if(MZUtil.isSoulbound(e.getItemDrop().getItemStack())) {
			e.getItemDrop().remove();
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.IRONGOLEM_DEATH, 1, 2f);
		}
	}
	@EventHandler
	public void onItemDropNotPlayer(ItemSpawnEvent e) {
		if(MZUtil.isSoulbound(e.getEntity().getItemStack())) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onInventoryEvent(InventoryClickEvent e) {
		if(e.getCurrentItem() == null) {
			return;
		}
		if(MZUtil.isSoulbound(e.getCursor()) || MZUtil.isSoulbound(e.getCurrentItem())) {
			if(e.getInventory().getType() != InventoryType.CRAFTING) {
				if(e.getSlot() == e.getRawSlot()) {
					e.setCancelled(true);
				}
				if(e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
					e.setCancelled(true);
				}

			}
			if((e.getSlotType() != InventoryType.SlotType.CONTAINER && e.getSlotType() != InventoryType.SlotType.QUICKBAR)
					) {
				e.setCancelled(true);
			}
		}
	}
                }
