package com.github.khy010802.elevator.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Elevator {

    public enum ElevatorState {
        OPENED, // 버튼 신호를 대기 중이다.
        CLOSED, // 버튼 신호를 받았고, 문이 닫혀 이동을 기다리는 상태다.
        MOVING, // 이동 중이다. 문은 닫혀있고, 목표를 향해 가는 중이다.
        DISABLED, // 점검 중인 상태. 디버그용.
        DESPAWNED; // 디스폰된 상태. build() 가 필요함.
    }

    // TODO : 버튼 객체


    private final Location center;               // 엘레베이터 중심 바닥 Location.
    private final List<Integer> floors;         // 층 수. 각 층 사이의 거리를 말하며, 0번 index가 4면 1층과 2층 사이의 거리는 4블럭
                                                        // 즉, 다시 말해 floors.size 는 곧 층의 개수-1을 의미함


    private ElevatorState now_state = ElevatorState.DESPAWNED;

    public Elevator(Location center){
        this.center = center;
        this.floors = new ArrayList<>();
    }

    // 정적 정보 Getter
    public Location getCenter(){ return this.center; }
    public List<Integer> getFloors() { return this.floors; }

    // 가변 정보 Getter
    public ElevatorState getNowState() { return this.now_state; }


    /**
     * 엘레베이터를 설정된 구역 내부의 블럭을 기반으로 빌드.
     * @param loc1 로케이션1
     * @param loc2 로케이션2
     */
    public void build(Location loc1, Location loc2){

        World world = loc1.getWorld();
        int min_x = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int min_y = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int min_z = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int max_x = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int max_y = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int max_z = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        Location minLoc = new Location(world, min_x, min_y, min_z);
        Location maxLoc = new Location(world, max_x, max_y, max_z);

        // 1층 빌드
        for(int dx=min_x;dx<=max_x;dx++){
            for(int dy=min_y;dy<=max_y;dy++){
                for(int dz=min_z;dz<=max_z;dz++){
                    Location loc = new Location(world, dx, dy, dz);
                    Block b = loc.getBlock();

                    Material mat = b.getType();
                    if(mat.isSolid()){
                        // TODO : 블럭 모델 구축
                    }
                }
            }
        }

        now_state = ElevatorState.OPENED;
    }


    /**
     * 블럭으로부터 Material을 구하기.
     */
    private Material getMaterialFromBlock(Block b, Material base){
        if(b.getType().name().contains("AIR"))
            return base;
        else
            return b.getType();
    }
}
