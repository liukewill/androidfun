package com.example.offer;

/**
 * Created by kenan on 18/3/29.
 */

public class Code3 {
    public static void main(String [] args){
// 测试用的例子
        int A[][] = { { 1, 2, 8, 9 }, { 2, 4, 9, 12 }, { 4, 7, 10, 13 },
                { 6, 8, 11, 15 } };
        System.out.println(findNumber(A, 0));
    }

    public static int findNumber(int [] [] arrays,int number){
        int times=0;
        int rows=arrays.length;
        int colums=arrays[0].length;

        int row=0;
        int colum=colums-1;

//        while(row<rows && colum>=0){
//            int cur=arrays[row][colum];
//            times++;
//            if(cur==number){
//                return times;
//            }else if(cur>number){
//                colum--;
//            }else{
//                row++;
//            }
//        }
        for(int i=0;i<rows;i++){
            for(int j=0;j<colums;j++){
                System.out.print(arrays[i][j]+",");
                if(j==colums-1){
                    System.out.println();
                }
            }
        }
        return times;
    }
}
