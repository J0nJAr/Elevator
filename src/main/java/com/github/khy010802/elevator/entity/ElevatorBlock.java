package com.github.khy010802.elevator.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Shulker;

/**
 * 엘레베이터 블럭 하나하나를 만들어둔 클래스.
 * ShulkerBox, FallingBlock, Location을 저장해야하므로 만듦.
 */
public class ElevatorBlock {

    public ElevatorBlock(Location loc, Material mat){
        this.loc = loc;

        spawn(mat);
    }

    private final Location loc;
    private Material mat;

    private Shulker shulker;
    private FallingBlock fb;

    private boolean isSpawned = false;


    public Location getLocation() { return this.loc; }
    public boolean isSpawned() { return this.isSpawned; }


    public void setType(Material mat){
        // TODO : 셜커박스는 지우지 말고, FallingBlock 만 지우게끔 변경
        if(isSpawned)
            despawn();

        spawn(mat);
    }


    public void spawn(Material mat){
        World world = loc.getWorld();

        shulker = (Shulker) world.spawnEntity(loc, EntityType.SHULKER);
        shulker.setInvisible(true);
        shulker.setInvulnerable(true);
        shulker.setAI(false);
        shulker.setGravity(false);

        fb = (FallingBlock) world.spawnFallingBlock(loc, mat, (byte) 0);
        fb.setHurtEntities(false);
        fb.setDropItem(false);
        fb.setGravity(false);

        this.mat = mat;
        this.isSpawned = true;
    }

    public void despawn(){
        shulker.remove();
        fb.remove();

        shulker = null;
        fb = null;
        this.isSpawned = false;
    }

}
