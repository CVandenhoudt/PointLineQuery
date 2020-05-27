/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.other;

/**
 *
 * @author vandenboer
 */
public class Table {
    
    private char rowDatatype;
    private char columnDatatype;
    private double[] type1Data;
    private double[] type2Data;
    private double[][] tableData;

    public Table(int columns, int rows, char rowDatatype, char columnDatatype) {
        this.rowDatatype = rowDatatype;
        this.columnDatatype = columnDatatype;
        this.type1Data = new double[rows];
        this.type2Data = new double[columns];
        this.tableData = new double[columns][rows];
    }
    
   public void setDataAt(int row, int column, double data) {
       this.tableData[column][row] = data;
   }

   public void setRowValue(int row, double value) {
       this.type1Data[row] = value;
   }
   
   public void setColumnValue(int column, double value) {
       this.type2Data[column] = value;
   }
   
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("       ").append('|');
        for (int i = 0; i < this.type2Data.length; i++) {
            result.append(String.format("%6.2f", this.type2Data[i])).append(this.columnDatatype).append('|');
        }
        
        result.append("\n");
        for (int ii = 0; ii < this.type2Data.length; ii++) {
            result.append("----------");
        }
        
        for (int i = 0; i < this.type1Data.length; i++) {
            result.append("\n");
            result.append(String.format("%6.2f", this.type1Data[i])).append(this.rowDatatype).append('|');
            for (int ii = 0; ii < this.type2Data.length; ii++) {
                result.append(String.format("%7.2f", this.tableData[ii][i])).append('|');
            }
            result.append("\n");
            for (int ii = 0; ii < this.type2Data.length; ii++) {
                result.append("----------");
            }
        }
        
        return result.toString();
    }
   
}
