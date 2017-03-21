package main;

import main.Variables;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by dbrisingr on 28/02/2017.
 */
public class CommonFunctions {

    public static LinkedHashSet<String> loadResultsList() {

        LinkedHashSet<String> results = new LinkedHashSet<>();

        File folder = new File("src/main/results_data");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String temp = listOfFiles[i].getName();
                temp = temp.substring(temp.indexOf('[') + 1, temp.indexOf(']'));
                results.add(temp);
            }
        }

        return results;
    }

    public static ArrayList<Object> loadResult(String name) {

        ArrayList<Object> arrayList = new ArrayList<>();
        ArrayList<String> resultNames = new ArrayList<>();
        String pathName = "src/main/results_data/";
        File folder = new File(pathName);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String temp = listOfFiles[i].getName();
                temp = temp.substring(temp.indexOf('[') + 1, temp.indexOf(']'));
                if (temp.equals(name)) {
                    resultNames.add(pathName + listOfFiles[i].getName());
                }
            }
        }

        for (String s : resultNames) {
            try {
                FileInputStream fin = new FileInputStream(s);
                ObjectInputStream in = new ObjectInputStream(fin);
                arrayList.add(in.readObject());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static ArrayList<String> getStrategies(boolean all, boolean custom) {
        File currentDir = new File("."); // Read current file location
        File srcDir;
        File strategiesDir;
        ArrayList<String> strategies = new ArrayList<>();
        try {
            srcDir = new File(currentDir.getCanonicalFile(), "src"); // Construct the target directory file with the right parent directory
            strategiesDir = new File(srcDir, "strategies");

            String toAdd;
            DirectoryStream<Path> dirStream = Files.newDirectoryStream(strategiesDir.toPath());
            for (Path p : dirStream) {
                if (p.toFile().isDirectory() && p.toFile().getName().equals("original") && (all || !custom)) {
                    for (Path path : Files.newDirectoryStream(p)) {
                        toAdd = path.getFileName().toString();
                        toAdd = toAdd.substring(0, toAdd.indexOf(".java"));
                        toAdd += " (original)";
                        strategies.add(toAdd);
                    }
                } else if (p.toFile().isDirectory() && p.toFile().getName().equals("custom") && (custom || all)) {
                    for (Path path : Files.newDirectoryStream(p)) {
                        toAdd = path.getFileName().toString();
                        toAdd = toAdd.substring(0, toAdd.indexOf(".java"));
                        strategies.add(toAdd);
                    }
                } else if (p.toFile().isDirectory() && p.toFile().getName().equals("built_in") && (all || !custom)) {
                    for (Path path : Files.newDirectoryStream(p)) {
                        toAdd = path.getFileName().toString();
                        toAdd = toAdd.substring(0, toAdd.indexOf(".java"));
                        toAdd += " (built-in)";
                        strategies.add(toAdd);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strategies;
    }

    public static String randomChoice(float p) {
        if (p == 0) {
            return Variables.DEFECT;
        }
        if (p == 1) {
            return Variables.COOPERATE;
        }
        if (new Random().nextInt(100) < p) {
            return Variables.COOPERATE;
        } else {
            return Variables.DEFECT;
        }
    }
}
