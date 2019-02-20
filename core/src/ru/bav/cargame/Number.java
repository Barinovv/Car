package ru.bav.cargame;

import com.badlogic.gdx.math.Rectangle;

public class Number extends Rectangle{

    public Number(int coordinate, int y, int width, int height, com.badlogic.gdx.math.Rectangle numbrSpawn){

        this.x = coordinate;
        this.y = y;
        this.width = width;
        this.height = height;

        numbrSpawn.x = coordinate;
        numbrSpawn.y = 1000;
        numbrSpawn.width = 64;
        numbrSpawn.height = 64;

    }

}
