package de.depascaldc.nukkitxprojects.commandsigns;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.plugin.PluginBase;

public class CommandSigns extends PluginBase implements Listener {
    public CommandSigns() {
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getLogger().info("Command Signs enabled...");
    }

    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void execute(PlayerInteractEvent e) {
        if (e != null) {
            Player p = e.getPlayer();
            Block b = e.getBlock();
            PlayerInteractEvent.Action action = e.getAction();
            if (b.getId() == 68 || b.getId() == 63) {
                if (!(p.getLevel().getBlockEntity(b.getLocation()) instanceof BlockEntitySign)) {
                    return;
                }

                BlockEntitySign bes = (BlockEntitySign)p.getLevel().getBlockEntity(b.getLocation());
                if (action == Action.RIGHT_CLICK_BLOCK) {
                    String[] signtext = bes.getText();
                    String line1 = "";
                    String line2 = "";
                    String line3 = "";
                    String line4 = "";
                    if (signtext.length > 1) {
                        line1 = signtext[0];
                        line2 = signtext[1];
                        line3 = signtext[2];
                        line4 = signtext[3];
                        if (line1.equals("[CMD]") && p.isOp()) {
                            bes.setText(new String[]{"§a§c§e§c§b§r[§a§c§e§c§bCommand§r]", "§a" + line2, "§a" + line3, "§a" + line4});
                        }

                        if (line1.equals("§a§c§e§c§b§r[§a§c§e§c§bCommand§r]")) {
                            if(!line3.isEmpty()) p.getServer().dispatchCommand(p, line3.replaceAll("§a", "").replace("@p", "\"" + p.getName() + "\""));
                            if(!line4.isEmpty()) p.getServer().dispatchCommand(p, line4.replaceAll("§a", "").replace("@p", "\"" + p.getName() + "\""));
                            e.setCancelled(true);
                        }
                    }
                }
            }

        }
    }

    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void onEvent(BlockBreakEvent e) {
        if (e != null) {
            Player p = e.getPlayer();
            Block b = e.getBlock();
            if (b.getId() == 68 || b.getId() == 63) {
                if (!(p.getLevel().getBlockEntity(b.getLocation()) instanceof BlockEntitySign)) {
                    return;
                }

                BlockEntitySign bes = (BlockEntitySign)p.getLevel().getBlockEntity(b.getLocation());
                String[] signtext = bes.getText();
                String line1 = "";
                if (signtext.length > 0) {
                    line1 = signtext[0];
                    if (line1.equals("§a§c§e§c§b§r[§a§c§e§c§bCommand§r]") && !p.isOp() || line1.equals("§a§c§e§c§b§r[§a§c§e§c§bCommand§r]") && p.isOp() && !p.isSneaking()) {
                        e.setCancelled(true);
                    }
                }
            }

        }
    }
}
