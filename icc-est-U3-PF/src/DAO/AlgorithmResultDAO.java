package DAO;

import java.util.List;

import Models.AlgorithmResult;

public interface AlgorithmResultDAO {
    void saveResult(AlgorithmResult result);
    List<AlgorithmResult> getAllResults();
    void clearResults();
}

