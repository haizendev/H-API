package fr.haizen.hapi.citizens;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.World;

public class EntityUtils {

    public static void registerCustomEntity(Class<? extends Entity> entityClass, String name, int id) {
        try {
            putInPrivateStaticMap(EntityTypes.class, "d", entityClass, name);
            putInPrivateStaticMap(EntityTypes.class, "f", entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static org.bukkit.entity.Entity spawnCustomEntity(Entity entity, Location loc) {
        World mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        mcWorld.addEntity(entity, SpawnReason.CUSTOM);
        return entity.getBukkitEntity();
    }

    public static void setDestination(org.bukkit.entity.Entity entity, Location location, float speed) {
        setDestination(entity, location.getX(), location.getY(), location.getZ(), speed);
    }

    public static void setDestination(org.bukkit.entity.Entity entity, double posX, double posY, double posZ, float speed) {
        Entity handle = getHandle(entity);
        if (handle == null) {
            return;
        }
        if ((handle instanceof EntityInsentient)) {
            ((EntityInsentient) handle).getControllerMove().a(posX, posY, posZ, speed);
        }
    }

    public static Entity getHandle(org.bukkit.entity.Entity entity) {
        if (!(entity instanceof CraftEntity)) {
            return null;
        }
        return (Entity) ((CraftEntity) entity).getHandle();
    }

    public static void putInPrivateStaticMap(Class clazz, String fieldName, Object key, Object value) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Map map = (Map) field.get(null);
        map.put(key, value);
        field.set(null, map);
    }
}
