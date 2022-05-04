package com.yungnickyoung.minecraft.yungscavebiomes.block.property;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;

public enum IceSheetFaces implements StringRepresentable {
    NORTH("north", Direction.NORTH),
    SOUTH("south", Direction.SOUTH),
    EAST("east", Direction.EAST),
    WEST("west", Direction.WEST),
    UP("up", Direction.UP),
    DOWN("down", Direction.DOWN);
    private final String name;
    private final Direction[] directions;

    IceSheetFaces(String name, Direction... directions) {
        this.name = name;
        this.directions = directions;
    }

    public String toString() {
        return this.getSerializedName();
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public Direction[] getDirections() {
        return this.directions;
    }

    public boolean hasYAxis() {
        return Arrays.stream(this.directions).anyMatch(direction -> direction.getAxis() == Direction.Axis.Y);
    }
    
    public static IceSheetFaces fromDirection(Direction direction) {
        return switch (direction) {
            case DOWN -> DOWN;
            case UP -> UP;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        };
    }
}
