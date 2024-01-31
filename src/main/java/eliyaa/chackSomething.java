package eliyaa;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class chackSomething {

    public static void main(String[] args) {
        double time =runtenMethodMultipleTimes(100000);
        System.out.println("time of func:"+time);

    }
    public static int[] sort(int[] arr) 
    {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }
public static double runtenMethodMultipleTimes(int numIterations) 
{
        long endTime;

        int[] randomArray = new int[numIterations];
 
            for (int j = 0; j < numIterations; j++) 
                {
                    randomArray[j] = numIterations - j;
                }
                long startTime = System.nanoTime();
                    sort(randomArray); // The method you want to check
                endTime = System.nanoTime();
        double time = (endTime - startTime) / 1e9; // Calculating time in seconds
        return time;
    
}
}


