package net.hole.boi.mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

import com.mojang.brigadier.arguments.StringArgumentType;

import java.util.*;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
public class HoleBoiMod implements ModInitializer{
    /**
     * Determines whether the mod will scroll
     * away from the held tool when at 1 durability
     */
    public Boolean SCROLLAWAY = false;
    /**
     * Determines whether to send a message & sound when
     * tool is at less than 10% durability
     */
    public Boolean DURANOISE = true;
    /**
     * The Prefix to be prepended to all command feedback
     */
    public final String MODPREFIX = "§3[HB]§r ";
    
    /**
     * Contains list of holebois
     */
    //public List<String> holebois = Arrays.asList(new String[]{"deceiverW","Kroojel","eeveevy","JustPrez","bk____","Galiano"});
    

    /**
     * Fixes Strings resulting from the method AbstractClientPlayerEntity.getName().toString()
     * @param String "literal{name}"
     * @return Fixed String
     * @see net.minecraft.client.network.AbstractClientPlayerEntity#getName()
     */
    public String fixLiteralString(String brokeString) {
        String y = brokeString.substring(8, brokeString.length()-1);
        return y;

    }


    
    @Override
    public void onInitialize() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
        {
            if (DURANOISE) {
                int maxDura = player.getMainHandStack().getMaxDamage();
                int currentDura = player.getMainHandStack().getDamage();
                int dura = maxDura-currentDura;
                String item = player.getMainHandStack().toString();
                Boolean isEmptyHand = item.equals("1 air") || item.equals("0 air");
                //player.sendMessage(Text.literal(item));
                //player.sendMessage(Text.literal(String.valueOf(currentDura) + "/" + String.valueOf(maxDura)));
                if (dura == 1 && !isEmptyHand ) {
                    player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 0.7F, 1.0F);
                    player.sendMessage(Text.literal(MODPREFIX + "§l§cAutomatically swapped away from tool with 1 durability"));
                    player.getInventory().scrollInHotbar(0.5);
                }
                else if (dura < maxDura*0.1 && !isEmptyHand) {
                    player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.5F, 1.0F);
                    player.sendMessage(Text.literal(MODPREFIX + "§cThe tool you are using has low durability!!"));
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
                    context.getSource().getPlayer().sendMessage(Text.literal(MODPREFIX + "Low Durability scrolling is now §c§l" + state));
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

                    context.getSource().getPlayer().sendMessage(Text.literal(MODPREFIX + "Low Durability scrolling is §c§l" + state));
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
                    context.getSource().getPlayer().sendMessage(Text.literal(MODPREFIX + "Durability Message is now §c§l" + state));
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

                    context.getSource().getPlayer().sendMessage(Text.literal(MODPREFIX + "Durability Message is §c§l" + state));
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

                        player.sendMessage(Text.literal(MODPREFIX + mumbos));
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
            
            .then(literal("msg")
                .then(argument("message", StringArgumentType.string())
                    .executes(context -> {
                        /*
                         * GOAL: Send a message to every online holeboi 
                         * 
                         * TOOLS:
                         *  - Can send commands with `player.sendCommand(<string>)`
                         *  - Can get all online players with `world.getPlayers()`
                         * 
                         * PROCESS:
                         *  - Have Some list of Whitelisted users
                         *  - Loop through list of players 
                         *      - If player name matches an item in the list add it to new list
                         *  - Loop through list of online players 
                         *      - Send message to each one
                         * 
                         */


                        ClientPlayerEntity player = context.getSource().getPlayer();
                        List<String> holebois = Arrays.asList(new String[]{"deceiverW","Kroojel","eeveevy","JustPrez","bk____","Galiano","Player476"});
                        
                        String commandUser = fixLiteralString(player.getName().toString());
                        ClientWorld world = context.getSource().getWorld();
                        List<String> OnlineHB = new ArrayList<>();
                        String message = StringArgumentType.getString(context, "message");
                        
                        
                        List<AbstractClientPlayerEntity> playerlist = world.getPlayers();
                        for (AbstractClientPlayerEntity x : playerlist) {
                            String playerName = fixLiteralString(x.getName().toString());
                            for (String y : holebois) {
                                if (playerName.equals(y)  && !playerName.equals(commandUser) ) {
                                    OnlineHB.add(y);
                                }
                            }
                        }
                        if (OnlineHB.size() == 0) {
                            player.sendMessage(Text.literal(MODPREFIX + "§cThere are no other members of HB online!"));
                        }
                        else {
                            for (String x : OnlineHB) {
                                player.sendCommand("msg " + x + " " + message);
                            } 
                        }
                        return 1;
                    })
                )
            )
        ));
    }


}

