package ru.vsueducation.resolvers;

import com.google.gson.Gson;
import ru.vsueducation.utils.ResponseObject;

import java.util.*;

public class HungarianAlgorithm {

    public static double findLargest
    (double[][] array)
    {
        double largest = 0;
        for (int i=0; i<array.length; i++)
        {
            for (int j=0; j<array[i].length; j++)
            {
                if (array[i][j] > largest)
                {
                    largest = array[i][j];
                }
            }
        }

        return largest;
    }
    public static double[][] transpose
    (double[][] array)
    {
        double[][] transposedArray = new double[array[0].length][array.length];
        for (int i=0; i<transposedArray.length; i++)
        {
            for (int j=0; j<transposedArray[i].length; j++)
            {transposedArray[i][j] = array[j][i];}
        }
        return transposedArray;
    }
    public static double[][] copyOf
    (double[][] original)
    {
        double[][] copy = new double[original.length][original[0].length];
        for (int i=0; i<original.length; i++)
        {

            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }

        return copy;
    }





    //public static int[][] hgAlgorithm (double[][] array, String sumType)
    public static int[][] hgAlgorithm (double[][] array, String sumType)
    {
        double[][] cost = copyOf(array);

        if (sumType.equalsIgnoreCase("max"))
        {
            double maxWeight = findLargest(cost);
            for (int i=0; i<cost.length; i++)
            {
                for (int j=0; j<cost[i].length; j++)
                {
                    cost [i][j] = (maxWeight - cost [i][j]);
                }
            }
        }

        double maxCost = findLargest(cost);

        int[][] mask = new int[cost.length][cost[0].length];
        int[] rowCover = new int[cost.length];
        int[] colCover = new int[cost[0].length];
        int[] zero_RC = new int[2];
        int step = 1;
        boolean done = false;
        while (done == false)
        {
            switch (step)
            {
                case 1:
                    step = hg_step1(step, cost);
                    break;
                case 2:
                    step = hg_step2(step, cost, mask, rowCover, colCover);
                    break;
                case 3:
                    step = hg_step3(step, mask, colCover);
                    break;
                case 4:
                    step = hg_step4(step, cost, mask, rowCover, colCover, zero_RC);
                    break;
                case 5:
                    step = hg_step5(step, mask, rowCover, colCover, zero_RC);
                    break;
                case 6:
                    step = hg_step6(step, cost, rowCover, colCover, maxCost);
                    break;
                case 7:
                    done=true;
                    break;
            }
        }

        int[][] assignment = new int[array.length][2];
        for (int i=0; i<mask.length; i++)
        {
            for (int j=0; j<mask[i].length; j++)
            {
                if (mask[i][j] == 1)
                {
                    assignment[i][0] = i;
                    assignment[i][1] = j;
                }
            }
        }



		/*
		double sum = 0;
		for (int i=0; i<assignment.length; i++)
		{
			sum = sum + array[assignment[i][0]][assignment[i][1]];
		}
		return sum;
		*/



        return assignment;
        /*double sum = 0;
        for (int i=0; i<assignment.length; i++)
        {
            sum = sum + array[assignment[i][0]][assignment[i][1]];
        }
        return sum;*/
    }

    public static String getJsonResult(double[][] array, int[][] assignment) {
        List<ResponseObject<Integer>> list = new ArrayList<>();
        ResponseObject<Integer> responseObject = new ResponseObject<>();
        StringBuilder pathBuilder = new StringBuilder();
        int total = 0;
        pathBuilder.append("Путь: ");
        for (int i = 0; i < assignment.length; i++) {
            total += array[assignment[i][0]][assignment[i][1]];
            pathBuilder.append("(").append(assignment[i][0] + 1).append(";").append(assignment[i][1] + 1).append(")");
            if (i != assignment.length - 1) {
                pathBuilder.append(", ");
            }
        }
        responseObject.setText(pathBuilder.toString());
        list.add(responseObject);
        responseObject = new ResponseObject<>();
        responseObject.setText("Ответ: " + total);
        list.add(responseObject);
        final Gson gson = new Gson();
        return gson.toJson(list);

    }

    public static int hg_step1(int step, double[][] cost)
    {




        double minval;

        for (int i=0; i<cost.length; i++)
        {
            minval=cost[i][0];
            for (int j=0; j<cost[i].length; j++)
            {
                if (minval>cost[i][j])
                {
                    minval=cost[i][j];
                }
            }
            for (int j=0; j<cost[i].length; j++)
            {
                cost[i][j]=cost[i][j]-minval;
            }
        }

        step=2;
        return step;
    }
    public static int hg_step2(int step, double[][] cost, int[][] mask, int[]rowCover, int[] colCover)
    {



        for (int i=0; i<cost.length; i++)
        {
            for (int j=0; j<cost[i].length; j++)
            {
                if ((cost[i][j]==0) && (colCover[j]==0) && (rowCover[i]==0))
                {
                    mask[i][j]=1;
                    colCover[j]=1;
                    rowCover[i]=1;
                }
            }
        }

        clearCovers(rowCover, colCover);

        step=3;
        return step;
    }
    public static int hg_step3(int step, int[][] mask, int[] colCover)
    {



        for (int i=0; i<mask.length; i++)
        {
            for (int j=0; j<mask[i].length; j++)
            {
                if (mask[i][j] == 1)
                {
                    colCover[j]=1;
                }
            }
        }

        int count=0;
        for (int j=0; j<colCover.length; j++)
        {
            count=count+colCover[j];
        }

        if (count>=mask.length)
        {
            step=7;
        }
        else
        {
            step=4;
        }

        return step;
    }
    public static int hg_step4(int step, double[][] cost, int[][] mask, int[] rowCover, int[] colCover, int[] zero_RC)
    {





        int[] row_col = new int[2];
        boolean done = false;
        while (done == false)
        {
            row_col = findUncoveredZero(row_col, cost, rowCover, colCover);
            if (row_col[0] == -1)
            {
                done = true;
                step = 6;
            }
            else
            {
                mask[row_col[0]][row_col[1]] = 2;

                boolean starInRow = false;
                for (int j=0; j<mask[row_col[0]].length; j++)
                {
                    if (mask[row_col[0]][j]==1)
                    {
                        starInRow = true;
                        row_col[1] = j;
                    }
                }

                if (starInRow==true)
                {
                    rowCover[row_col[0]] = 1;
                    colCover[row_col[1]] = 0;
                }
                else
                {
                    zero_RC[0] = row_col[0];
                    zero_RC[1] = row_col[1];
                    done = true;
                    step = 5;
                }
            }
        }

        return step;
    }
    public static int[] findUncoveredZero
    (int[] row_col, double[][] cost, int[] rowCover, int[] colCover)
    {
        row_col[0] = -1;
        row_col[1] = 0;

        int i = 0; boolean done = false;
        while (done == false)
        {
            int j = 0;
            while (j < cost[i].length)
            {
                if (cost[i][j]==0 && rowCover[i]==0 && colCover[j]==0)
                {
                    row_col[0] = i;
                    row_col[1] = j;
                    done = true;
                }
                j = j+1;
            }
            i=i+1;
            if (i >= cost.length)
            {
                done = true;
            }
        }

        return row_col;
    }
    public static int hg_step5(int step, int[][] mask, int[] rowCover, int[] colCover, int[] zero_RC)
    {

        int count = 0;
        int[][] path = new int[(mask[0].length*mask.length)][2];
        path[count][0] = zero_RC[0];
        path[count][1] = zero_RC[1];

        boolean done = false;
        while (done == false)
        {
            int r = findStarInCol(mask, path[count][1]);
            if (r>=0)
            {
                count = count+1;
                path[count][0] = r;
                path[count][1] = path[count-1][1];
            }
            else
            {
                done = true;
            }

            if (done == false)
            {
                int c = findPrimeInRow(mask, path[count][0]);
                count = count+1;
                path[count][0] = path [count-1][0];
                path[count][1] = c;
            }
        }

        convertPath(mask, path, count);
        clearCovers(rowCover, colCover);
        erasePrimes(mask);

        step = 3;
        return step;

    }
    public static int findStarInCol
    (int[][] mask, int col)
    {
        int r=-1;
        for (int i=0; i<mask.length; i++)
        {
            if (mask[i][col]==1)
            {
                r = i;
            }
        }

        return r;
    }
    public static int findPrimeInRow
    (int[][] mask, int row)
    {
        int c = -1;
        for (int j=0; j<mask[row].length; j++)
        {
            if (mask[row][j]==2)
            {
                c = j;
            }
        }

        return c;
    }
    public static void convertPath
    (int[][] mask, int[][] path, int count)
    {
        for (int i=0; i<=count; i++)
        {
            if (mask[(path[i][0])][(path[i][1])]==1)
            {
                mask[(path[i][0])][(path[i][1])] = 0;
            }
            else
            {
                mask[(path[i][0])][(path[i][1])] = 1;
            }
        }
    }
    public static void erasePrimes
    (int[][] mask)
    {
        for (int i=0; i<mask.length; i++)
        {
            for (int j=0; j<mask[i].length; j++)
            {
                if (mask[i][j]==2)
                {
                    mask[i][j] = 0;
                }
            }
        }
    }
    public static void clearCovers
    (int[] rowCover, int[] colCover)
    {
        for (int i=0; i<rowCover.length; i++)
        {
            rowCover[i] = 0;
        }
        for (int j=0; j<colCover.length; j++)
        {
            colCover[j] = 0;
        }
    }
    public static int hg_step6(int step, double[][] cost, int[] rowCover, int[] colCover, double maxCost)
    {

        double minval = findSmallest(cost, rowCover, colCover, maxCost);

        for (int i=0; i<rowCover.length; i++)
        {
            for (int j=0; j<colCover.length; j++)
            {
                if (rowCover[i]==1)
                {
                    cost[i][j] = cost[i][j] + minval;
                }
                if (colCover[j]==0)
                {
                    cost[i][j] = cost[i][j] - minval;
                }
            }
        }

        step = 4;
        return step;
    }
    public static double findSmallest
    (double[][] cost, int[] rowCover, int[] colCover, double maxCost)
    {
        double minval = maxCost;
        for (int i=0; i<cost.length; i++)
        {
            for (int j=0; j<cost[i].length; j++)
            {
                if (rowCover[i]==0 && colCover[j]==0 && (minval > cost[i][j]))
                {
                    minval = cost[i][j];
                }
            }
        }

        return minval;
    }

}
