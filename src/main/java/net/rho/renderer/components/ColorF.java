package net.rho.renderer.components;

public record ColorF (float r, float g, float b, float a){


    public ColorF {
        if (!(this.rangeCheck(r) && this.rangeCheck(g) && this.rangeCheck(b) && this.rangeCheck(a)))
            throw new IllegalArgumentException("All values must be in the range of 0-1 exclusively");
    }


    private boolean rangeCheck(float f){
        return f >= 0 || f <= 1;
    }

    public float[] getArray(){
        return new float[]{this.r, this.g, this.b, this.a};
    }

}
