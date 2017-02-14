package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dbrisingr on 06/02/2017.
 */
public class Main {


    public static void main(String[] args) {

        File currentDir = new File("."); // Read current file location
        File srcDir = null;
        File strategiesDir = null;

        try {
            srcDir = new File(currentDir.getCanonicalFile(), "src"); // Construct the target directory file with the right parent directory
            strategiesDir = new File(srcDir, "strategies");
            listDirectoryAndFiles(strategiesDir.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        }
//
//        ArrayList<String> strategyArrayList = new ArrayList<>();
//        strategyArrayList.add("ALWAYS_COOPERATE");
//        strategyArrayList.add("ALWAYS_DEFECT");
//        strategyArrayList.add("TIT_FOR_TAT");
////        strategyArrayList.add("RANDOM");
//
//        Tournament tournament = new Tournament(strategyArrayList, Tournament.TournamentMode.MODE_ORIGINAL);
//        tournament.executeMatches();
//        tournament.printTournamentScores(false, true, false);
////        tournament.getTournamentResult();
    }

    private static void listDirectoryAndFiles(Path path) throws IOException {
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(path);
        for (Path p : dirStream) {
            if(!p.toFile().isDirectory()){
                System.out.println(p.getFileName());
            }
//            if (p.toFile().isDirectory()) {
//                listDirectoryAndFiles(p);
//            }
        }
    }


}
