import java.util.Arrays;
import java.util.List;

/**
 This is a class that I personally made a while ago with multiple sorting Algorithms
 I used wikipedia and other sites as a reference for how the algorithms work but I copied no code
 **/

public class Sorting {
    public static <T extends Comparable<? super T>> void BubbleSort(T[] arr) {
        // Swaps adjacent values if they are out of order
        for(int i = 1; i < arr.length; i++)
            for(int j = 1; j < arr.length - i; j++)
                if(arr[j].compareTo(arr[j-1]) < 0)
                    Swap(arr,j,j-1);
    }

    public static <T extends Comparable<? super T>> void BubbleSort(List<T> list) {
        // Swaps adjacent values if they are out of order
        for(int i = 1; i < list.size(); i++)
            for(int j = 1; j < list.size() - i; j++)
                if(list.get(j).compareTo(list.get(j-1)) < 0)
                    Swap(list,j,j-1);
    }

    public static <T extends Comparable<? super T>> void InsertionSort(T[] arr) {
        // Moves through list and inserts next value into the already sorted portion of the array
        for(int i = 1; i < arr.length; i++)
            for(int j = i; j > 0 && arr[j-1].compareTo(arr[j]) > 0; j--)
                Swap(arr,j,j-1);
    }

    public static <T extends Comparable<? super T>> void InsertionSort(List<T> list) {
        // Moves through list and inserts next value into the already sorted portion of the list
        for(int i = 1; i < list.size(); i++)
            for(int j = i; j > 0 && list.get(j-1).compareTo(list.get(j)) > 0; j--)
                Swap(list,j,j-1);
    }

    public static <T extends Comparable<? super T>> boolean IsSorted(T[] arr) {
        // Cycles through array and makes sure that the previous value is smaller then the current one
        for(int i = 1; i < arr.length; i++)
            if(arr[i-1].compareTo(arr[i]) > 0)
                return false;
        return true;
    }

    public static <T extends Comparable<? super T>> boolean IsSorted(List<T> list) {
        // Cycles through list and makes sure that the previous value is smaller then the current one
        for(int i = 1; i < list.size(); i++)
            if(list.get(i-1).compareTo(list.get(i)) > 0)
                return false;
        return true;
    }

    public static <T extends Comparable<? super T>> void MergeSort(T[] arr) {
        MergeSort(arr,0,arr.length);
    }

    private static <T extends Comparable<? super T>> void MergeSort(T[] arr, int lo, int hi) {
        if(hi-lo > 1) {
            // Sort both sides
            MergeSort(arr, lo, (hi+lo)/2);
            MergeSort(arr, (hi+lo)/2, hi);

            int i = lo, j = (hi+lo)/2; // Represents the next value in each sorted half
            Object[] tarr = new Object[hi-lo]; // Temporary array to insert new values into

            for(int k = 0; k < tarr.length; k++)
                tarr[k] = i == (hi+lo)/2 ? // If first half is done then grab from second half
                        arr[j++] :
                        j == hi ? // if second half is done then grab from first half
                                arr[i++] :
                                arr[i].compareTo(arr[j]) < 0 ? // Figure out which is next and select it
                                        arr[i++] :
                                        arr[j++];

            // Moves values from temporary array back into original array
            for(int k = 0; k < tarr.length; k++) arr[lo+k] = (T) tarr[k];
        }
    }

    public static <T extends Comparable<? super T>> void MergeSort(List<T> list) {
        MergeSort(list,0,list.size());
    }

    private static <T extends Comparable<? super T>> void MergeSort(List<T> list, int lo, int hi) {
        if(hi-lo > 1) {
            // Sort both sides
            MergeSort(list, lo, (hi+lo)/2);
            MergeSort(list, (hi+lo)/2, hi);

            int i = lo, j = (hi+lo)/2; // Represents the next value in each sorted half
            Object[] tarr = new Object[hi-lo];  // Temporary array to insert new values into

            for(int k = 0; k < tarr.length; k++)
                tarr[k] = i == (hi+lo)/2 ? // If first half is done then grab from second half
                        list.get(j++) :
                        j == hi ? // if second half is done then grab from first half
                                list.get(i++) :
                                list.get(i).compareTo(list.get(j)) < 0 ? // Figure out which is next and select it
                                        list.get(i++) :
                                        list.get(j++);

            // Moves values from temporary array back into original array
            for(int k = 0; k < tarr.length; k++) list.set(lo+k,(T) tarr[k]);
        }
    }

    public static <T extends Comparable<? super T>> void QuickSort(T[] arr) {
        QuickSort(arr,0,arr.length-1);
    }

    private static <T extends Comparable<? super T>> void QuickSort(T[] arr, int lo, int hi) {
        // Standard Quicksort: find a partition and sort either side
        if(lo < hi) {
            int p = Partition(arr,lo,hi);
            QuickSort(arr,lo,p);
            QuickSort(arr,p+1,hi);
        }
    }

    private static <T extends Comparable<? super T>> int Partition(T[] arr, int lo, int hi) {
        // Finds the best pivot between the first, middle and last values in the list
        if(arr[lo].compareTo(arr[(hi+lo)/2]) > 0) Swap(arr,lo,(hi+lo)/2);
        if(arr[(hi+lo)/2].compareTo(arr[hi]) > 0) Swap(arr,(hi+lo)/2,hi);
        if(arr[lo].compareTo(arr[(hi+lo)/2]) > 0) Swap(arr,lo,(hi+lo)/2);
        T pivot = arr[(hi+lo)/2];

        // Hoare partition scheme as described at en.wikipedia.org/wiki/Quicksort
        int i = lo-1, j = hi+1;
        while(true) {
            // Finds two values on the wrong side of the pivot and switches them
            while(arr[++i].compareTo(pivot) < 0);
            while(arr[--j].compareTo(pivot) > 0);
            if(i>=j) return j;
            Swap(arr,i,j);
        }
    }

    public static <T extends Comparable<? super T>> void QuickSort(List<T> list) {
        QuickSort(list,0,list.size()-1);
    }

    private static <T extends Comparable<? super T>> void QuickSort(List<T> list, int lo, int hi) {
        // Standard Quicksort: find a partition and sort either side
        if(lo < hi) {
            int p = Partition(list,lo,hi);
            QuickSort(list,lo,p);
            QuickSort(list,p+1,hi);
        }
    }

    private static <T extends Comparable<? super T>> int Partition(List<T> list, int lo, int hi) {
        // Finds the best pivot between the first, middle and last values in the list
        if(list.get(lo).compareTo(list.get((hi+lo)/2)) > 0) Swap(list,lo,(hi+lo)/2);
        if(list.get((hi+lo)/2).compareTo(list.get(hi)) > 0) Swap(list,(hi+lo)/2,hi);
        if(list.get(lo).compareTo(list.get((hi+lo)/2)) > 0) Swap(list,lo,(hi+lo)/2);
        T pivot = list.get((hi+lo)/2);

        // Hoare partition scheme as described at en.wikipedia.org/wiki/Quicksort
        int i = lo-1, j = hi+1;
        while(true) {
            // Finds two values on the wrong side of the pivot and switches them
            while(list.get(++i).compareTo(pivot) < 0);
            while(list.get(--j).compareTo(pivot) > 0);
            if(i>=j) return j;
            Swap(list,i,j);
        }
    }

    public static void RadixSort(String[] arr) {
        RadixSort(arr, 1);
    }

    public static void RadixSort(String[] arr, int d) {
        String[] arr2 = new String[arr.length];
        Integer[] count = new Integer[(int)Math.pow(2,8*d)];
        int place = arr[0].length()-1; // Assumes that all strings in the list are the same size

        // Sorting algorithm will continue until the array is sorted.
        while(!IsSorted(arr)) {
            // Resets counting 'buckets'
            Arrays.fill(count, 0);

            // Counts up all of the different 'digits' in the array
            for (String str : arr) {
                int value = 0;
                for(int i = d-1; i >= 0; i--) {
                    value *= 256;
                    value += str.charAt(place-i);
                }

                count[value]++;
            }
            for(int i = 1; i < count.length; i++) count[i] += count[i-1];

            // Rewrites the array according to the buckets
            for (int i = arr.length-1; i >= 0; i--) {
                int value = 0;
                for(int j = d-1; j >= 0; j--) {
                    value *= 256;
                    value += arr[i].charAt(place-j);
                }
                arr2[--count[value]] = arr[i];
            }

            // Resets and prepares for next iteration.
            System.arraycopy(arr2, 0, arr, 0, arr.length);
            place -= d;
        }
    }

    public static <T extends Comparable<? super T>> void SelectionSort(T[] arr) {
        // Cycles through the whole array finding the next smallest value
        for(int i = 1; i < arr.length-2; i++) {
            // Finds next value in list and then puts it in the correct position
            int select = i-1;
            for(int j = i; j < arr.length-1; j++)
                if(arr[select].compareTo(arr[j]) > 0) select = j;
            Swap(arr,i-1,select);
        }
    }

    public static <T extends Comparable<? super T>> void SelectionSort(List<T> list) {
        // Cycles through the whole list finding the next smallest value
        for(int i = 1; i < list.size()-2; i++) {
            // Finds next value in list and then puts it in the correct position
            int select = i-1;
            for(int j = i; j < list.size()-1; j++)
                if(list.get(select).compareTo(list.get(j)) > 0) select = j;
            Swap(list,i-1,select);
        }
    }

    // Swaps values in array
    private static <T extends Comparable<? super T>> void Swap(T[] arr, int i, int j) {
        T t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    // Swaps values in list
    private static <T extends Comparable<? super T>> void Swap(List<T> list, int i, int j) {
        T t = list.get(i);
        list.set(i,list.get(j));
        list.set(j,t);
    }
}
