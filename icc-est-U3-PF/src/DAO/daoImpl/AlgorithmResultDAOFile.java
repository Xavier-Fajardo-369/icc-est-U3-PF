package DAO.daoImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import DAO.AlgorithmResultDAO;
import Models.AlgorithmResult;

public class AlgorithmResultDAOFile implements AlgorithmResultDAO {
    private static final String FILE_NAME = "results.csv";

    public void saveResult(AlgorithmResult result) {
        List<AlgorithmResult> results = getAllResults();
        results.removeIf(r -> r.name.equals(result.name));
        results.add(result);
        writeAll(results);
    }

    public List<AlgorithmResult> getAllResults() {
        List<AlgorithmResult> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                list.add(new AlgorithmResult(data[0], Integer.parseInt(data[1]), Long.parseLong(data[2])));
            }
        } catch (IOException e) {}
        return list;
    }

    public void clearResults() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            pw.print("");
        } catch (IOException e) {}
    }

    private void writeAll(List<AlgorithmResult> results) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (AlgorithmResult r : results) {
                pw.println(r.name + "," + r.pathLength + "," + r.time);
            }
        } catch (IOException e) {}
    }
}


