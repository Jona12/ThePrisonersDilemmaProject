package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by dbrisingr on 06/03/2017.
 */
public class TournamentMode {

    private static TournamentMode tournamentMode = new TournamentMode();

    private TournamentMode() {}

    public static TournamentMode getInstance() {
        return tournamentMode;
    }

    public final static String MODE_ORIGINAL = "ORIGINAL";
    public final static String MODE_NO_RANDOM = "NO RANDOM";
    public final static String MODE_NO_TWIN = "NO TWIN";
    public final static String MODE_NO_RANDOM_NO_TWIN = "NO RANDOM NO TWIN";
    public final static String MODE_ORIGINAL_WITH_REPEAT = "ORIGINAL MODE, WITH 1 REPEAT";
    public final static String MODE_CUSTOM = "CUSTOM MODE";

    private static HashMap<String, HashMap<Object, Object>> modesHashMap;

    private static String[] originalStrategies = {
            "TIT_FOR_TAT (original)", "DAVIS (original)", "DOWNING_REVISED (original)", "FELD (original)",
            "GROFMAN (original)", "JOSS (original)", "NYDEGGER (original)", "SHUBIK (original)",
            "TULLOCK (original)", "UNKNOWN (original)"
    };

    private static final String[] originalModesArray = {
            MODE_ORIGINAL, MODE_NO_RANDOM, MODE_NO_TWIN, MODE_NO_RANDOM_NO_TWIN, MODE_ORIGINAL_WITH_REPEAT, MODE_CUSTOM
    };

    public HashMap<Object, Object> getVariables(String mode) {
        return modesHashMap.get(mode);
    }

    public static HashMap<String, HashMap<Object, Object>> getModesHashMap() {

        modesHashMap = new HashMap<>();
        for (int i = 0; i < originalModesArray.length - 1; i++) {
            modesHashMap.put(originalModesArray[i], prepareTempHashMap(originalModesArray[i]));
        }

        HashMap<Object, Object> tempHashMap = loadCustomModeData();
        modesHashMap.put(MODE_CUSTOM, tempHashMap);

        return modesHashMap;
    }

    public static String[] getOriginalModes() {
        return originalModesArray;
    }

    public static String[] getOriginalStrategies() {
        return originalStrategies;
    }

    private static HashMap<Object, Object> prepareTempHashMap(String mode) {
        HashMap<Object, Object> tempHashMap = new HashMap<>();

        int numberOfRounds = 200;
        int numberOfRepeats = 0;
        boolean twin = true;
        boolean random = true;

        int[] scoreMatrix = new int[4];
        scoreMatrix[0] = 5;
        scoreMatrix[1] = 0;
        scoreMatrix[2] = 3;
        scoreMatrix[3] = 1;

        switch (mode) {
            case MODE_ORIGINAL:
                break;
            case MODE_NO_RANDOM:
                random = false;
                break;
            case MODE_NO_TWIN:
                twin = false;
                break;
            case MODE_NO_RANDOM_NO_TWIN:
                random = false;
                twin = false;
                break;
            case MODE_ORIGINAL_WITH_REPEAT:
                numberOfRepeats = 1;
        }

        tempHashMap.put(Variables.ROUNDS, numberOfRounds);
        tempHashMap.put(Variables.SCORE_MATRIX, scoreMatrix);
        tempHashMap.put(Variables.REPEAT, numberOfRepeats);
        tempHashMap.put(Variables.TWIN, twin);
        tempHashMap.put(Variables.RANDOM, random);

        return tempHashMap;
    }

    public static Properties storeCustomModeData(HashMap<Object, Object> toPut) {

        HashMap<Object, Object> temp;
        if (toPut != null) {
            temp = toPut;
        } else {
            int[] scoreMatrix = new int[4];
            scoreMatrix[0] = 5;
            scoreMatrix[1] = 0;
            scoreMatrix[2] = 3;
            scoreMatrix[3] = 1;

            String roundsValue = String.valueOf(20);
            String scoreMatrixValue = Arrays.toString(scoreMatrix);
            String repeatValue = String.valueOf(1);
            String twinValue = String.valueOf(true);
            String randomValue = String.valueOf(true);

            temp = new HashMap<>();

            temp.put(Variables.ROUNDS, roundsValue);
            temp.put(Variables.SCORE_MATRIX, scoreMatrixValue);
            temp.put(Variables.REPEAT, repeatValue);
            temp.put(Variables.TWIN, twinValue);
            temp.put(Variables.RANDOM, randomValue);
        }

        Properties properties = new Properties();
        properties.putAll(temp);

        try {
            properties.store(new FileOutputStream("src/main/mode_data/data.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static HashMap<Object, Object> loadCustomModeData() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/mode_data/data.properties"));
        } catch (IOException e) {
            properties = storeCustomModeData(null);
//                e.printStackTrace();
        }

        String arr = properties.getProperty(Variables.SCORE_MATRIX);
        String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
        int[] results = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            results[i] = Integer.parseInt(items[i]);
        }

        int roundsValue = Integer.valueOf(properties.getProperty(Variables.ROUNDS));
        int[] scoreMatrixValue = results;
        int repeatValue = Integer.valueOf(properties.getProperty(Variables.REPEAT));
        boolean twinValue = Boolean.valueOf(properties.getProperty(Variables.TWIN));
        boolean randomValue = Boolean.valueOf(properties.getProperty(Variables.RANDOM));

        properties.put(Variables.ROUNDS, roundsValue);
        properties.put(Variables.SCORE_MATRIX, scoreMatrixValue);
        properties.put(Variables.REPEAT, repeatValue);
        properties.put(Variables.TWIN, twinValue);
        properties.put(Variables.RANDOM, randomValue);

        HashMap<Object, Object> tempHashMap = new HashMap<>(properties);


        return tempHashMap;
    }
}
