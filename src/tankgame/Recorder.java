package tankgame;

import java.io.*;

/**
 * @author Zhang Yu
 * @version 1.0
 */
public class Recorder {
    private static int count = 0;
    private static String filePath = "src\\recorder.txt";

    public static void addCount(){
        count++;
    }
    public static int getCount() {
        return count;
    }

    public static void saveRecord() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(count + "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readRecord() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            count = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
