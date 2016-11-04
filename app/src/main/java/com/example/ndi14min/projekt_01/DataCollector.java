package com.example.ndi14min.projekt_01;

import android.util.Log;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataCollector {


	private List<VertexPosition> vertexPos = new ArrayList<>();
	private Double test = -1.0;
	ImportFilter filter;
	Double[][] DEMdata;
	String t ="";
	private Double cellSize;

	VertexPosition[][] vertPosAr;

	int count = 0;

	private int rows = -1, cols = -2;

	public DataCollector(InputStream in){

		filter = new ImportFilter(in);


		try {
			cols = filter.getFileInfo("ncols").intValue();
			rows = filter.getFileInfo("nrows").intValue();
			cellSize = filter.getFileInfo("cellsize");
			DEMdata = filter.getData(rows, cols);
			vertPosAr = new VertexPosition[rows][cols];
			Log.i("hejhej", "kanske fel" + cols +"   "+ rows);
			vertPos();


			// DEMdata = new Double[rows][cols];
		}
		catch (Exception e){
			t = " Exception filter: " + filter.getExc() +" exc DC: "  + e.toString();
			Log.e("hejhej","fel", e);
		}

		//System.out.println(rows);



/*
		for (int j = 0; j < 212; j++) {

			for (int i = 0; i < 316; i++) {
				//System.out.print(DEMdata[j][i] + " ");
				count++;
			}

		}*/

		//System.out.println(count);
		//System.out.println("First element " + DEMdata[0][0]);

		/*
		 * for (int i = 0; i < DEMdata.length; i++) {
		 * System.out.println(DEMdata[i][0]); }
		 * 
		 */

	}


	public void vertPos() {


		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				vertPosAr[i][j]= new VertexPosition(j*cellSize + (0.5 * cellSize), ((rows-i)*cellSize + (0.5 * cellSize)), (DEMdata[i][j]), i, j);




			}
		}

	}

	public VertexPosition[][] getVertPosRows(int row){

		VertexPosition[][] vertPos = new VertexPosition[2][cols];

		for(int i = 0; i < 2; i++){
			for (int j = 0; j<cols; j++){

				Log.i("vertPos", ""+j);
				vertPos[i][j] = new VertexPosition(j*cellSize + (0.5 * cellSize), ((row-i)*cellSize + (0.5 * cellSize)), (DEMdata[i][j]), i, j);

			}
		}


		return vertPos;
	}
	public VertexPosition[][] getVertPos(){

		return vertPosAr;
	}


	public Double get(){
		return test;
	}

	public Double[][] getData(){



		return DEMdata;
	}

	public int getRows(){
		// rows = filter.getFileInfo("nrows");
		return rows;
	}
	public int getCols(){
		//  cols = filter.getFileInfo("ncols");
		return cols;
	}
	public Double getCellSize(){
		return cellSize;
	}
	public String getT(){
		return t + " " +filter.getExc();
	}



}
