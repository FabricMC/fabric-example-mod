package net.hole.boi.mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

import net.minecraft.client.network.ClientPlayerEntity;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
public class HoleBoiMod implements ModInitializer{
    public Boolean SCROLLAWAY = false;
    public Boolean DURANOISE = true;

    
    @Override
    public void onInitialize() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
        {
            if (DURANOISE) {
                int maxDura = player.getMainHandStack().getMaxDamage();
                int currentDura = player.getMainHandStack().getDamage();
                int dura = maxDura-currentDura;
                String item = player.getMainHandStack().toString();
                Boolean isEmptyHand = item.equals("1 air");
                //player.sendMessage(Text.literal(item));
                //player.sendMessage(Text.literal(String.valueOf(currentDura) + "/" + String.valueOf(maxDura)));
                if (dura == 1 & !isEmptyHand ) {
                    player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 0.5F, 1.0F);
                    player.sendMessage(Text.literal("§3[HB]§r §l§cAutomatically swapped away from tool with 1 durability"));
                    player.getInventory().scrollInHotbar(0.5);
                }
                else if (dura < maxDura*0.1 && !isEmptyHand) {
                    player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.5F, 1.0F);
                    player.sendMessage(Text.literal("§3[HB]§r §cThe tool you are using has low durability!!"));
                    if (SCROLLAWAY) {
                        player.getInventory().scrollInHotbar(0.5);
                    }
                }

            }

            return ActionResult.PASS;
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("hb")
            .then(literal("help")
                .executes(context -> {
                    String helpmsg;
                    helpmsg = "§b========= §3§lHole Boi Mod§r§b =========§r";
                    helpmsg += "\n§7§l/hb togglenoise §r§7-> toggles the noise & message when low durability";
                    helpmsg += "\n§7§l/hb noisestate §r§7-> returns whether the durability noise/text is on";
                    helpmsg += "\n§7§l/hb togglescroll §r§7-> toggles if you scroll off your held tool if low durability";
                    helpmsg += "\n§7§l/hb scrollstate §r§7-> returns whether low durability scrolling is on";
                    helpmsg += "§b\n================================§r";

                    context.getSource().getPlayer().sendMessage(Text.literal(helpmsg));
                    return 1;
                }))
            .then(literal("togglescroll")
                .executes(context -> {
                    String state = "";
                    if (SCROLLAWAY) {
                        state = "off";
                        SCROLLAWAY = false;
                    }
                    else{
                        state = "on";
                        SCROLLAWAY = true;
                    }
                    context.getSource().getPlayer().sendMessage(Text.literal("§3[HB]§r Low Durability scrolling is now §c§l" + state));
                    return 1;
                }))
            .then(literal("scrollstate")
                .executes(context -> {
                    String state = "";
                    if (SCROLLAWAY) {
                        state = "on";
                    }
                    else{
                        state = "off";
                    }

                    context.getSource().getPlayer().sendMessage(Text.literal("§3[HB]§r Low Durability scrolling is §c§l" + state));
                    return 1;
                }))
            .then(literal("togglenoise")
                .executes(context -> {
                    String state = "";
                    if (DURANOISE) {
                        state = "off";
                        DURANOISE = false;
                    }
                    else{
                        state = "on";
                        DURANOISE = true;
                    }
                    context.getSource().getPlayer().sendMessage(Text.literal("§3[HB]§r Durability Message is now §c§l" + state));
                    return 1;
                }))
            .then(literal("noisestate")
                .executes(context -> {
                    String state = "";
                    if (DURANOISE) {
                        state = "on";
                    }
                    else{
                        state = "off";
                    }

                    context.getSource().getPlayer().sendMessage(Text.literal("§3[HB]§r Durability Message is §c§l" + state));
                    return 1;
                }))
            .then(literal("mumbos")
                .then(literal("text")
                    .executes(context -> {
                        String mumbos = "";
                        ClientPlayerEntity player = context.getSource().getPlayer();
                        for (int i = 0; i<10; i++) {
                            if (Math.random() > 0.5) {
                                mumbos += "mumbo";
                            }
                            else {
                                mumbos += "jumbo";
                            }
                            if (Math.random() > 0.5 ) {
                                mumbos += "s";
                            }
                            mumbos += " ";
                        }

                        player.sendMessage(Text.literal("§3[HB]§r " + mumbos));
                        return 1;

                    }))
                .then(literal("chat")
                    .executes(context -> {
                        String mumbos = "";
                        ClientPlayerEntity player = context.getSource().getPlayer();
                        for (int i = 0; i<10; i++) {
                            if (Math.random() > 0.5) {
                                mumbos += "mumbo";
                            }
                            else {
                                mumbos += "jumbo";
                            }
                            if (Math.random() > 0.5 ) {
                                mumbos += "s";
                            }
                            mumbos += " ";
                        }

                        player.sendChatMessage(mumbos, null);
                        return 1;

                    }))
            )
            /* .then(literal("thres")
                .then(argument("threshold", integer(0))
                    .executes(context -> {
                        Integer x = getInteger(context, "threshold");
                        //context.getSource().getPlayer().sendMessage(Text.literal(String.valueOf(x)));
                        ClientPlayerEntity player = context.getSource().getPlayer();
                        player.dropSelectedItem(true);
                        return 1;
                    })
                )
            ) */
        ));
    }


}

