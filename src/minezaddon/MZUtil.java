/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minezaddon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Administrator
 */
class MZUtil {
    	public static boolean isSoulbound(ItemStack is) {
		if(is == null) {
			return false;
		}
		ItemMeta im = is.getItemMeta();
		if(im == null) {
			return false;
		}

		List<String> lore = im.getLore()==null?new ArrayList<String>():im.getLore();
		for(String s : lore) {
			if(s.contains("Soulbound")) {
				return true;
			}
		}
		return false;
	}
}
