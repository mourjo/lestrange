import java.io.*;

public class SystemInfo {
   public static String info() {
    String result = "";
    /* Total number of processors or cores available to the JVM */
    result += "Available processors (cores): " + 
        Runtime.getRuntime().availableProcessors() + "\n\n";

    /* Total amount of free memory available to the JVM */
    result += "Free memory (bytes): " + 
        Runtime.getRuntime().freeMemory()  + "\n\n";

    /* This will return Long.MAX_VALUE if there is no preset limit */
    long maxMemory = Runtime.getRuntime().maxMemory();
    /* Maximum amount of memory the JVM will attempt to use */
    result += "Maximum memory (bytes): " + 
        (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory) + "\n\n";

    /* Total memory currently available to the JVM */
    result += "Total memory available to JVM (bytes): " + 
        Runtime.getRuntime().totalMemory() + "\n\n";

    /* Get a list of all filesystem roots on this system */
    File[] roots = File.listRoots();

    /* For each filesystem root, print some info */
    for (File root : roots) {
      result += "File system root: " + root.getAbsolutePath() + "\n\n";
      result += "Total space (bytes): " + root.getTotalSpace() + "\n\n";
      result += "Free space (bytes): " + root.getFreeSpace() + "\n\n";
      result += "Usable space (bytes): " + root.getUsableSpace() + "\n\n";
    }

    result += "Thread=" + Thread.currentThread().getName();
    return result;
  }

}
