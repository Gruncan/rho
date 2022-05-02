package testgame;

public class Collision {


    public static boolean collisionDetection(Rec rect1, Rec rect2) {
        // no collision
        return rect1.x < rect2.x + rect2.w &&
                rect1.x + rect1.w > rect2.x &&
                rect1.y < rect2.y + rect2.h &&
                rect1.h + rect1.y > rect2.y;
    }


    public static class Rec {

        private final int x;
        private final int y;
        private final int w;
        private final int h;


        public Rec(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }


}
