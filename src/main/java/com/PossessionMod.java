package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.command.argument.EntityArgumentType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PossessionMod implements ModInitializer {

    // Храним связи: Контроллер (Наблюдатель) -> Жертва
    public static final Map<UUID, UUID> activePossessions = new HashMap<>();

    @Override
    public void onInitialize() {
        System.out.println("Possession Mod (Server-Side) loaded for 1.21.8!");

        // 1. Регистрация команды /possess
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("possess")
                .requires(source -> source.hasPermissionLevel(2)) // Только для админов/операторов
                .then(CommandManager.argument("target", EntityArgumentType.player())
                    .executes(context -> {
                        ServerPlayerEntity controller = context.getSource().getPlayer();
                        ServerPlayerEntity victim = EntityArgumentType.getPlayer(context, "target");

                        if (controller == null) return 0;

                        if (activePossessions.containsKey(controller.getUuid())) {
                            activePossessions.remove(controller.getUuid());
                            controller.sendMessage(Text.literal("Вы покинули тело игрока."), false);
                        } else {
                            activePossessions.put(controller.getUuid(), victim.getUuid());
                            controller.sendMessage(Text.literal("Вы захватили контроль над " + victim.getName().getString()), false);
                            
                            // Принудительно переводим контроллера в спектатор
                            controller.changeGameMode(net.minecraft.world.GameMode.SPECTATOR);
                            // Телепортируем спектатора прямо в жертву
                            controller.teleport(victim.getServerWorld(), victim.getX(), victim.getY(), victim.getZ(), victim.getYaw(), victim.getPitch());
                        }
                        return 1;
                    })
                )
            );
        });

        // 2. Логика синхронизации каждый такт (20 раз в секунду)
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (Map.Entry<UUID, UUID> entry : activePossessions.entrySet()) {
                ServerPlayerEntity controller = server.getPlayerManager().getPlayer(entry.getKey());
                ServerPlayerEntity victim = server.getPlayerManager().getPlayer(entry.getValue());

                // Если кто-то вышел с сервера — разрываем связь
                if (controller == null || victim == null) {
                    activePossessions.remove(entry.getKey());
                    continue;
                }

                // Синхронизация взгляда (Pitch и Yaw)
                victim.setYaw(controller.getYaw());
                victim.setPitch(controller.getPitch());
                victim.setHeadYaw(controller.getHeadYaw());

                // Принудительное перемещение жертвы к контроллеру
                // Мы берем X и Z от спектатора, но Y оставляем на земле, чтобы жертва не летала
                double targetX = controller.getX();
                double targetZ = controller.getZ();
                
                // Простая физика падения для жертвы (чтобы она не висела в воздухе)
                double targetY = controller.getY();
                if (!victim.isOnGround()) {
                    targetY = victim.getY() - 0.5; // Симуляция гравитации
                }

                // Телепортируем жертву (используем requestTeleport для мягкости на клиенте жертвы)
                victim.requestTeleport(targetX, targetY, targetZ);
                
                // Отправляем пакеты поворота головы клиентам вокруг
                victim.networkHandler.sendPacket(new net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket(victim, (byte) ((controller.getHeadYaw() * 256.0F) / 360.0F)));
            }
        });
    }
}
