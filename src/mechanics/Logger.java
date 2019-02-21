package mechanics;

import heroes.Hero;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Logger {

    static Map<String, Logger> pool;

    public static void log(Hero hero, int pad, String msg) {
        String tn = hero.getName();
        if (!pool.containsKey(tn))
            pool.put(tn, new Logger(tn));

        pool.get(tn)._log(pad, msg);
    }

    public static void logInline(Hero hero, String msg) {
        String tn = hero.getName();
        if (!pool.containsKey(tn))
            pool.put(tn, new Logger(tn));

        pool.get(tn)._logInline(msg);
    }

    static {
        pool = new HashMap<>();
    }

    private String filename;
    private FileWriter writer;
    private long startTime;
    private boolean running;

    private Logger(String threadname) {
        try {
            running = true;
            startTime = System.currentTimeMillis();
            filename = "logs/log_" + System.currentTimeMillis() + "_" + threadname + "_" + ".txt";
            writer = new FileWriter(filename);
            System.out.println("ready");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void _log(int pad, String data) {
        if(!running)
            return;
        try {
            writer.append("\n");
            writer.append(Long.toString(System.currentTimeMillis() - startTime)).append("\t");
            for (int i = 0; i < pad; ++i) {
                writer.append("\t");
            }
            writer.append(">").append(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void _logInline(String data) {
        if(!running)
            return;
        try {
            writer.append("----------------------------").append(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void finish() {
        try {
            for (Logger l : pool.values()){
                l.writer.close();
                l.running = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
