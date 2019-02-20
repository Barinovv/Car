package ru.bav.cargame;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;

public class Automobile extends Rectangle{

        Texture sheet;

    public Automobile(int x, int y, int width, int height, String texturePath ){

            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;

            sheet = new Texture(texturePath);

        }
}
