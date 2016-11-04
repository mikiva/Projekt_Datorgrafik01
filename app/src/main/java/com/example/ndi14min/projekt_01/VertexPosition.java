package com.example.ndi14min.projekt_01;

/**
 * Created by ndi14min on 2016-11-03.
 */
public class VertexPosition {

    private Double xPos;
    private Double yPos;
    private Double height;
    private int rowPos;
    private int colPos;


    public VertexPosition(Double x, Double y, Double h, int r, int c){

        this.xPos = x;
        this.yPos = y;
        this.height = h;
        this.rowPos = r;
        this.colPos = c;
    }


    public float getxPos(){
        return xPos.floatValue();
    }
    public float getyPos(){
        return yPos.floatValue();
    }
    public float getHeight(){
        return height.floatValue();
    }
    public float getRowPos(){
        return rowPos;
    }
    public float getColPos(){
        return colPos;
    }


}
