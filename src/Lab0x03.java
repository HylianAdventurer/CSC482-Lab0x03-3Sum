import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lab0x03 {

    final static long MAX_TIME = 1000000000;

    public static void main(String[] args) {
        long time;
        long[] lastTimes = new long[] {0,0,0};
        boolean running = true;

        System.out.println("N\tBrute Time\tDR\tEDR\tFaster Time\tDR\tEDR\tFastest Time\tDR\tEDR");
        for(int n = 4; running; n *= 2) {
            System.out.print(n + "\t");
            List<Integer> list = GenerateData(n,-n,n);
            running = false;

            if(lastTimes[0] < MAX_TIME) {
                time = getCpuTime();
                BruteForce(list);
                time = getCpuTime() - time;
                System.out.print(time + "\t" + (lastTimes[0] == 0 ? "NA" : (double) time / lastTimes[0]) + "\t" + Math.pow(n, 3) / Math.pow(n / 2, 3) + "\t");
                lastTimes[0] = time;
                running = true;
            } else System.out.print("NA\tNA\tNA\t");

            if(lastTimes[1] < MAX_TIME) {
                time = getCpuTime();
                Faster(list);
                time = getCpuTime() - time;
                System.out.print(time + "\t" + (lastTimes[1] == 0 ? "NA" : (double) time / lastTimes[1]) + "\t" + Math.pow(n, 2) * Math.log(n) / Math.pow(n / 2, 2) / Math.log(n / 2) + "\t");
                lastTimes[1] = time;
                running = true;
            } else System.out.print("NA\tNA\tNA\t");

            if(lastTimes[2] < MAX_TIME) {
                time = getCpuTime();
                Fastest(list);
                time = getCpuTime() - time;
                System.out.println(time + "\t" + (lastTimes[2] == 0 ? "NA" : (double) time / lastTimes[2]) + "\t" + Math.pow(n, 2) / Math.pow(n / 2, 2));
                lastTimes[2] = time;
                running = true;
            } else System.out.println("NA\tNA\tNA\t");
        }
        System.out.println("Testing Done!");

    }

    public static List<Integer[]> BruteForce(List<Integer> list) {
        Sorting.QuickSort(list);
        for(int i = 1; i < list.size(); i++)
            if(list.get(i-1).equals(list.get(i)))
                list.remove(i--);

        List<Integer[]> result = new ArrayList<>();
        Integer[] set = new Integer[3];

        for(int i = 0; i < list.size(); i++) {
            set[0] = list.remove(i);

            for(int j = i; j < list.size(); j++) {
                set[1] = list.remove(j);

                for(int k = j; k < list.size(); k++) {
                    set[2] = list.remove(k);

                    int sum = 0;
                    for(int x : set) sum += x;
                    if(sum == 0)
                        result.add(new Integer[] {set[0],set[1],set[2]});

                    list.add(k,set[2]);
                }

                list.add(j,set[1]);
            }

            list.add(i,set[0]);
        }

        return result;
    }

    public static List<Integer[]> Faster(List<Integer> list) {
        Sorting.QuickSort(list);
        for(int i = 1; i < list.size(); i++)
            if(list.get(i-1).equals(list.get(i)))
                list.remove(i--);

        List<Integer[]> result = new ArrayList<>();
        Integer[] set = new Integer[3];

        for(int i = 0; i < list.size(); i++) {
            set[0] = list.remove(i);

            for(int j = i; j < list.size(); j++) {
                set[1] = list.remove(j);

                int low = j, high = list.size();
                while(low < high) {
                    if(-list.get((low+high)/2) == set[0] + set[1]) {
                        result.add(new Integer[] {set[0],set[1],list.get((low+high)/2)});
                        break;
                    } else if(-list.get((low+high)/2) > set[0] + set[1])
                        high = (low+high)/2;
                    else low = (low+high)/2+1;
                }

                list.add(j,set[1]);
            }

            list.add(i,set[0]);
        }

        return result;
    }

    public static List<Integer[]> Fastest(List<Integer> list) {
        Sorting.QuickSort(list);
        for(int i = 1; i < list.size(); i++)
            if(list.get(i-1).equals(list.get(i)))
                list.remove(i--);

        List<Integer[]> result = new ArrayList<>();
        Integer[] set = new Integer[3];

        for(int i = 0; i < list.size(); i++) {
            set[0] = list.get(i);
            int start = i+1, end = list.size()-1;
            while(start < end) {
                set[1] = list.get(start);
                set[2] = list.get(end);
                if (set[0] + set[1] + set[2] == 0) {
                    result.add(new Integer[] {set[0],set[1],set[2]});
                    start++;
                    end--;
                } else if(set[0] + set[1] + set[2] > 0) end--;
                else start++;
            }
        }

        return result;
    }

    public static List<Integer> GenerateData(int N, int min, int max) {
        Random rng = new Random();
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < N; i++) list.add(rng.nextInt(max-min)+min);
        return list;
    }

    public static boolean Equals(Integer[] set1, Integer[] set2) {
        for(Integer x1 : set1) {
            boolean match = false;
            for(Integer x2 : set2)
                if (x1 == x2) {
                    match = true;
                    break;
                }
            if(!match) return false;
        }
        for(Integer x2 : set2) {
            boolean match = false;
            for(Integer x1 : set1)
                if(x1 == x2) {
                    match = true;
                    break;
                }
            if(!match) return false;
        }
        return true;
    }

    /** Get CPU time in nanoseconds since the program(thread) started. */
    /** from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime **/
    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }
}
