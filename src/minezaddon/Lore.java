package minezaddon;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Lore
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage(ChatColor.RED + "You must be a player to execute this Command!");
      return false;
    }

    Player player = (Player)sender;

    if ((player.hasPermission("lore.use")) || (player.hasPermission("lore.*")))
    {
      ItemStack is = player.getItemInHand();
      ItemMeta im = is.getItemMeta();

      if (is.getType().equals(Material.AIR))
      {
        player.sendMessage(ChatColor.RED + "Error: You don't have any item in your hand");
        return false;
      }

      int count = 0;

      count = args.length;

      if (args.length < 1)
      {
        commandSyntax(player);
      }
      else
      {
        List lore;
        if (im.getLore() == null)
        {
          lore = new ArrayList();
        }
        else
        {
          lore = im.getLore();
        }

        if (args[0].equalsIgnoreCase("set"))
        {
          if ((player.hasPermission("lore.set")) || (player.hasPermission("lore.*")))
          {
            if (count <= 1)
            {
              player.sendMessage(ChatColor.RED + "Error: Please add a line number");

              player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + "set" + 
                ChatColor.AQUA + " <" + ChatColor.GOLD + "Line" + ChatColor.AQUA + "> <" + 
                ChatColor.GOLD + "Lore" + ChatColor.AQUA + ">");
            }
            else if (count == 2)
            {
              player.sendMessage(ChatColor.RED + "Error: Please type the lore you want to add");

              player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + 
                "set" + ChatColor.AQUA + " <" + ChatColor.GOLD + "Line" + ChatColor.AQUA + 
                "> <" + ChatColor.GOLD + "Lore" + ChatColor.AQUA + ">");
            }
            else if (count == 3)
            {
              if (im.getLore() == null)
              {
                player.sendMessage(ChatColor.RED + "Error: Item does not have lore");
              }
              else
              {
                int line = Integer.parseInt(args[1]);

                if (line <= lore.size())
                {
                  lore.set(line - 1, 
                    ChatColor.translateAlternateColorCodes('&', parseLore(args[2])));
                  im.setLore(lore);
                  player.sendMessage(
                    ChatColor.LIGHT_PURPLE + "Sucessfully set lore at line #" + line);
                }
                else
                {
                  player.sendMessage(
                    ChatColor.RED + "Error: Line number doesn't exist in item lore");
                  player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + 
                    ChatColor.BOLD + "set" + ChatColor.AQUA + " <" + ChatColor.GOLD + 
                    "Line" + ChatColor.AQUA + "> <" + ChatColor.GOLD + "Lore" + 
                    ChatColor.AQUA + ">");
                  return false;
                }
              }
            }
            else
            {
              sender.sendMessage(ChatColor.RED + 
                "Error: Too many arguments. Maybe try using '_' instead of spaces in lore");

              return false;
            }

            player.getItemInHand().setItemMeta(im);
            return true;
          }

          player.sendMessage(
            ChatColor.RED + "You don't have permission \" lore.set \" to use this command");
          return false;
        }

        if (args[0].equalsIgnoreCase("add"))
        {
          if ((player.hasPermission("lore.add")) || (player.hasPermission("lore.*")))
          {
            if (count < 2)
            {
              player.sendMessage(ChatColor.RED + "Error: Please enter the Lore");
              player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + 
                "add" + ChatColor.AQUA + " <" + ChatColor.GOLD + "Lore" + ChatColor.AQUA + 
                ">");
            }
            else if (count == 2)
            {
              lore.add(ChatColor.translateAlternateColorCodes('&', parseLore(args[1])));
              im.setLore(lore);
              player.sendMessage(ChatColor.LIGHT_PURPLE + "Sucessfully added lore");
            }
            else if (count == 3)
            {
              int line = Integer.parseInt(args[1]);
              if (line > lore.size())
              {
                player.sendMessage(
                  ChatColor.RED + "Error: Line number doesn't exist in item lore");
                player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + 
                  ChatColor.BOLD + "add" + ChatColor.AQUA + " <" + ChatColor.GOLD + 
                  "Lore" + ChatColor.AQUA + ">");
                return false;
              }

              lore.add(line - 1, 
                ChatColor.translateAlternateColorCodes('&', parseLore(args[2])));
              im.setLore(lore);
              player.sendMessage(ChatColor.LIGHT_PURPLE + 
                "Sucessfully added lore after line #" + line);
            }
            else
            {
              sender.sendMessage(ChatColor.RED + 
                "Error: Too many arguments. Maybe try using _ instead of spaces");
              player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + 
                ChatColor.BOLD + "add" + ChatColor.AQUA + " <" + ChatColor.GOLD + 
                "Lore" + ChatColor.AQUA + ">");
              return false;
            }

            player.getItemInHand().setItemMeta(im);
            return true;
          }

          player.sendMessage(
            ChatColor.RED + "You don't have permission \" lore.add \" to use this command");
          return false;
        }

        if ((args[0].equalsIgnoreCase("remove")) || (args[0].equals("rem")))
        {
          if ((player.hasPermission("lore.remove")) || (player.hasPermission("lore.*")))
          {
            if (count == 1)
            {
              if (im.getLore() == null)
              {
                player.sendMessage(ChatColor.RED + "Error: Item has no lore");
                return false;
              }

              lore.remove(lore.size() - 1);
              im.setLore(lore);
              player.sendMessage(ChatColor.LIGHT_PURPLE + 
                "Sucessfully removed last line from item lore");
            }
            else if (count == 2)
            {
              int line = Integer.parseInt(args[1]);
              if (line > lore.size())
              {
                player.sendMessage(
                  ChatColor.RED + "Error: Line number doesn't exist in item lore");
                return false;
              }

              lore.remove(line - 1);
              im.setLore(lore);
              player.sendMessage(ChatColor.LIGHT_PURPLE + 
                "Sucessfully removed lore at line #" + line);
            }
            else
            {
              sender.sendMessage(ChatColor.RED + 
                "Error: Too many arguments. Maybe try using '_' instead of spaces in lore");
              return false;
            }

            player.getItemInHand().setItemMeta(im);
            return true;
          }

          player.sendMessage(ChatColor.RED + 
            "You don't have permission \" lore.remove \" to use this command");
          return false;
        }

        if (args[0].equalsIgnoreCase("clear"))
        {
          if ((player.hasPermission("lore.clear")) || (player.hasPermission("lore.*")))
          {
            if (count > 1)
            {
              player.sendMessage(ChatColor.RED + "Too Many Arguments");
              return false;
            }

            if (im.getLore() == null)
            {
              player.sendMessage(ChatColor.RED + "Error: Item has no lore");
              return false;
            }

            lore.clear();
            im.setLore(lore);
            player.getItemInHand().setItemMeta(im);
            player.sendMessage(
              ChatColor.LIGHT_PURPLE + "Sucessfully cleared item lore");
            return true;
          }

          player.sendMessage(ChatColor.RED + 
            "You don't have permission \" lore.clear \" to use this command");
          return false;
        }

        if (args[0].equalsIgnoreCase("name"))
        {
          if ((player.hasPermission("lore.name")) || (player.hasPermission("lore.*")))
          {
            if (count < 2)
            {
              player.sendMessage(ChatColor.RED + "Too few arguments.");
              player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + "name" + ChatColor.AQUA + 
                " <" + ChatColor.GOLD + "name" + ChatColor.AQUA + ">" + ChatColor.GREEN + " sets name of current item in hand");

              return false;
            }
            if (count > 2)
            {
              player.sendMessage(ChatColor.RED + "Too Many Arguments");
              player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + "name" + ChatColor.AQUA + 
                " <" + ChatColor.GOLD + "name" + ChatColor.AQUA + ">" + ChatColor.GREEN + " sets name of current item in hand");

              return false;
            }

            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', parseLore(args[1])));
            player.getItemInHand().setItemMeta(im);
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Sucessfully set item name");
            return true;
          }

          player.sendMessage(ChatColor.RED + 
            "You don't have permission \" lore.name \" to use this command");
          return false;
        }

        if ((args[0].equalsIgnoreCase("skull")) || (args[0].equalsIgnoreCase("head")))
        {
          if ((player.hasPermission("lore.head")) || (player.hasPermission("lore.*")))
          {
            if (count < 2)
            {
              player.sendMessage(ChatColor.RED + "Too few arguments.");

              return false;
            }
            if (count > 2)
            {
              player.sendMessage(ChatColor.RED + "Too Many Arguments");

              return false;
            }

            if (player.getItemInHand().getType().equals(Material.SKULL_ITEM))
            {
              SkullMeta sm = (SkullMeta)im;
              sm.setOwner(args[1]);

              player.getItemInHand().setItemMeta(sm);
              player.sendMessage(ChatColor.LIGHT_PURPLE + "Sucessfully changed skull owner");
              return true;
            }

            player.sendMessage(ChatColor.RED + "Error: You are not holding a skull in your hand");
            return false;
          }

          player.sendMessage(ChatColor.RED + 
            "You don't have permission \" lore.head \" to use this command");
          return false;
        }

        player.sendMessage(ChatColor.RED + "Error: no such sub command.");
        commandSyntax(player);
        return false;
      }

    }
    else
    {
      player.sendMessage(ChatColor.RED + "You don't have permission to use this command");
    }
    return false;
  }

  private void commandSyntax(Player player)
  {
    player.sendMessage(ChatColor.DARK_GRAY + "--------------/\\ " + ChatColor.DARK_PURPLE + ChatColor.BOLD + 
      "Insane Lore" + ChatColor.DARK_GRAY + " /\\--------------");
    player.sendMessage("Note:" + ChatColor.GRAY + ChatColor.BOLD + ChatColor.GRAY + ChatColor.ITALIC + 
      " Use '_' instead of spaces whilst writing the lore");

    player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + "add" + ChatColor.AQUA + 
      " <" + ChatColor.GOLD + "Lore" + ChatColor.AQUA + ">" + ChatColor.GREEN + " adds lore to item");

    player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + "name" + ChatColor.AQUA + 
      " <" + ChatColor.GOLD + "name" + ChatColor.AQUA + ">" + ChatColor.GREEN + " sets name of current item in hand");

    player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + "head" + ChatColor.AQUA + 
      " <" + ChatColor.GOLD + "playername" + ChatColor.AQUA + ">" + ChatColor.GREEN + " changes owner of the skull");

    player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + "set" + ChatColor.AQUA + 
      " <" + ChatColor.GOLD + "Line" + ChatColor.AQUA + "> <" + ChatColor.GOLD + "Lore" + ChatColor.AQUA + 
      ">" + ChatColor.GREEN + " sets specific line of lore");
    player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + "rem" + ChatColor.AQUA + 
      " <" + ChatColor.GOLD + "Line" + ChatColor.AQUA + "> <" + ChatColor.GOLD + "Lore" + ChatColor.AQUA + 
      ">" + ChatColor.GREEN + " removes specific/last line of lore");
    player.sendMessage(ChatColor.AQUA + "/Lore " + ChatColor.GOLD + ChatColor.BOLD + "clear" + ChatColor.GREEN + 
      " clears the lore of an item");
  }

  private String parseLore(String str)
  {
    str = str.replace('_', ' ');
    return str;
  }
}