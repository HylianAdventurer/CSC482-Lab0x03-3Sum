import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab0x03 {
    public static void main(String[] args) {
        if(Verification()) {

        }
    }

    public static List<Integer[]> BruteForce(List<Integer> list) {
        return null;
    }

    public static List<Integer[]> Faster(List<Integer> list) {
        return null;
    }

    public static List<Integer[]> Fastest(List<Integer> list) {
        return null;
    }

    public static boolean Verification() {
        List<Integer> testList = GenerateData(1000,-100,100);

        List<Integer[]> bruteOutput = BruteForce(testList), fasterOutput = Faster(testList), fastestOutput = Fastest(testList);

        assert bruteOutput != null;
        assert fasterOutput != null;
        assert fastestOutput != null;

        for(Integer[] set1 : bruteOutput) {
            boolean fasterMatch = false;
            for(Integer[] set2 : fasterOutput)
                if(Equals(set1,set2)) {
                    fasterMatch = true;
                    break;
                }
            if(!fasterMatch) {
                System.out.println("Faster Match did not contain set in Brute Force");
                return false;
            }
            boolean fastestMatch = false;
            for(Integer[] set2 : fastestOutput)
                if(Equals(set1,set2)) {
                    fastestMatch = true;
                    break;
                }
            if(!fastestMatch) {
                System.out.println("Fastest Match did not contain set in Brute Force");
                return false;
            }
        }

        for(Integer[] set1 : fasterOutput) {
            boolean fasterMatch = false;
            for(Integer[] set2 : bruteOutput)
                if(Equals(set1,set2)) {
                    fasterMatch = true;
                    break;
                }
            if(!fasterMatch) {
                System.out.println("Faster Match contained a set not in Brute Force");
                return false;
            }
        }

        for(Integer[] set1 : fastestOutput) {
            boolean fastestMatch = false;
            for(Integer[] set2 : bruteOutput)
                if(Equals(set1,set2)) {
                    fastestMatch = true;
                    break;
                }
            if(!fastestMatch) {
                System.out.println("Fastest Match contained a set not in Brute Force");
                return false;
            }
        }

        return true;
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
