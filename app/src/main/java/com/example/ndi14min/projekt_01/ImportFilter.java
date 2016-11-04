package com.example.ndi14min.projekt_01;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class ImportFilter {

    ImportFilter filt;
    // Scanner s;
    private Double dataInfo;
    private InputStream in;
    int rows, cols;
    String exc;
    BufferedReader br;

    public ImportFilter(InputStream in) {


        this.in = in;
        br = new BufferedReader(new InputStreamReader(in));
        //this.rows = getFileInfo("nrows");
        //this.cols = getFileInfo("ncols");

    }

    /**
     * Returnerar värdet på det som frågas efter. T ex antal rader eller
     * columner.
     *
     * @param type
     * @return
     */
    public Double getFileInfo(String type){

        dataInfo = -1.0;
        String line;
        String[] parts;



        //Scanner s = new Scanner(ins);




        try {
            br.mark(1000);
            while (br.ready()) {
                line = br.readLine();
                if (line.contains(type)) {
                    parts = line.split(type);

                    dataInfo = Double.parseDouble(parts[1].trim());

                    br.reset();
                    return dataInfo;
                }

            }
        }
        catch (Exception e){

            exc = e.toString();
            return -3.0;
        }


        /*}while (s.hasNextLine()) {
            String line = s.nextLine();
            if (line.contains(type)) {
                String[] parts = line.split(type);

                dataInfo = Integer.parseInt(parts[1].trim());

                s.close();
                return dataInfo;
            }
        }
        s.close();
*/

        return dataInfo;
    }

    public Double[][] getData(int rows, int cols) {

        int rowCount = 0;


        //System.out.println(rows + "  " + cols);

        Double[][] data = new Double[rows][cols];

      Scanner s = new Scanner(in);

        try {
            while (br.ready()) {

                String line = br.readLine();
                // System.out.println(line);
                if (line.contains("   ")) {
                    String[] tempRow = line.split("\\s+");
                    String[] row = new String[tempRow.length - 1];

                    for (int i = 0; i < row.length; i++) {

                        row[i] = tempRow[i + 1];
                    }

                    for (int i = 0; i < row.length; i++) {
                        data[rowCount][i] = Double.parseDouble(row[i].trim());


                    }
                    rowCount++;
                }

            }
        }
        catch(Exception e){

        }
        /*while (s.hasNextLine()) {

            String line = s.nextLine();
            // System.out.println(line);
            if (line.contains("   ")) {
                String[] tempRow = line.split("\\s+");
                String[] row = new String[tempRow.length - 1];

                for (int i = 0; i < row.length; i++) {

                    row[i] = tempRow[i + 1];
                }

                for (int i = 0; i < row.length; i++) {
                    data[rowCount][i] = Double.parseDouble(row[i].trim());


                }
                rowCount++;
            }

        }*/

        return data;
    }

    public Double get(){
        return 2.0;
    }

    public String getExc(){

        return exc;
    }


}
