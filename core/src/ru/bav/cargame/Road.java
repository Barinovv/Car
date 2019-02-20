package ru.bav.cargame;

import com.badlogic.gdx.math.Rectangle;

    class Road extends Rectangle {


        Road(int x, int y, int width, int height, Rectangle rasingspaw) {

            this.height = height;
            this.width = width;
            this.x = x;
            this.y = y;

            rasingspaw.height = height;
            rasingspaw.width = width;
            rasingspaw.x = x;
            rasingspaw.y = y;

        }
    }


