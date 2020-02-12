package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.*;

import static bg.sofia.uni.fmi.mjt.authorship.detection.FeatureType.*;
import static java.lang.Math.abs;

public class AuthorshipDetectorImpl implements AuthorshipDetector {

    private static final int MAX_SIZE = 11;
    private static final double FIRST_WEIGHT = 11;
    private static final double SECOND_WEIGHT = 33;
    private static final double THIRD_WEIGHT = 50;
    private static final double FORTH_WEIGHT = 0.4;
    private static final double FIFTH_WEIGHT = 4;
    private static final double WORDS_PER_LINE = 6;

    public static String cleanUp(String word) {
        return word.toLowerCase()
                .replaceAll("^[!.,:;\\-?<>#\\*\'\"\\[\\(\\]\\)\\n\\t\\\\]+|[!.,:;\\-?<>#\\" +
                        "*\'\"\\[\\(\\]\\)\\n\\t\\\\]+$", "");
    }

    double countAllWords(String inputText) {
        int i;
        double blankSpaces = 0;
        for (i = 0; i < inputText.length(); ++i) {
            if (inputText.charAt(i) == ' ' || inputText.charAt(i) == '?' || inputText.charAt(i) == '!'
                    || inputText.charAt(i) == '.') {
                blankSpaces = blankSpaces + 1;
            }
        }
        return (blankSpaces + 1);
    }

    double countUniqueWords(String inputText) {
        Set<String> uniqueWords = new HashSet<>();
        String[] tokens;
        tokens = inputText.split("[\\s+.!?;:,\"]");
        int i;
        for (i = 0; i < tokens.length; ++i) {
            uniqueWords.add(tokens[i]);
        }
        return (double) uniqueWords.size();
    }

    double countPhrases(String inputText) {
        String[] tokens;
        tokens = inputText.split("[/;:,]");
        return (double) tokens.length;
    }

    double countOnlyOnceMetWords(String inputText) {
        Map<String, Integer> wordsMap = new HashMap<>();
        String[] tokens;
        int counter;
        double numberOfUniqueWords = 0;
        tokens = inputText.split("[\\s+.!?;:,\"]");

        int i;
        for (i = 0; i < tokens.length; ++i) {
            if (wordsMap.containsKey(tokens[i])) {
                counter = wordsMap.get(tokens[i]);
                wordsMap.put(tokens[i], counter + 1);
            } else {
                wordsMap.put(tokens[i], 1);
            }
        }
        for (int value : wordsMap.values()) {
            if (value == 1) {
                ++numberOfUniqueWords;
            }
        }
        return numberOfUniqueWords;
    }

    double countSentences(String inputText) {
        String[] tokens;
        tokens = inputText.split("[!.?]+");
        return (double) tokens.length;
    }

    String convertInputStreamToString(InputStream stream) {
        String inputText = new String();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream))) {
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                inputText = inputText + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return inputText;
    }

    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException();
        }
        double totalCharacters;
        double numberOfAllWords;
        double numberOfUniqueWords;
        double numberOfOnlyOnceMetWords;
        double numberOfSentences;
        double numberOfPhrases;
        HashMap<FeatureType, Double> features = new HashMap<>();
        String inputText = convertInputStreamToString(mysteryText);
        if (inputText == null) {
            return null;
        }

        numberOfSentences = countSentences(inputText);
        numberOfPhrases = countPhrases(inputText);
        cleanUp(inputText);

        numberOfAllWords = countAllWords(inputText);
        totalCharacters = inputText.length() - numberOfAllWords;
        features.put(AVERAGE_WORD_LENGTH, totalCharacters / numberOfAllWords);
        numberOfUniqueWords = countUniqueWords(inputText);
        features.put(TYPE_TOKEN_RATIO, numberOfUniqueWords / numberOfAllWords);
        numberOfOnlyOnceMetWords = countOnlyOnceMetWords(inputText);
        features.put(HAPAX_LEGOMENA_RATIO, numberOfOnlyOnceMetWords / numberOfAllWords);
        features.put(AVERAGE_SENTENCE_LENGTH, numberOfAllWords / numberOfSentences);
        features.put(AVERAGE_SENTENCE_COMPLEXITY, numberOfPhrases / numberOfSentences);
        LinguisticSignature currentSignature = new LinguisticSignature(features);
        return currentSignature;
    }


    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature,
                                      LinguisticSignature secondSignature) {
        double coincidenceCoefficient = 0;
        double[] weights = {FIRST_WEIGHT, SECOND_WEIGHT, THIRD_WEIGHT, FORTH_WEIGHT, FIFTH_WEIGHT};

        HashMap<FeatureType, Double> firstSignatureMap = firstSignature.getFeatures();
        HashMap<FeatureType, Double> secondSignatureMap = secondSignature.getFeatures();

        coincidenceCoefficient += abs((firstSignatureMap.get(AVERAGE_WORD_LENGTH)
                - secondSignatureMap.get(AVERAGE_WORD_LENGTH))) * weights[0];
        coincidenceCoefficient += abs((firstSignatureMap.get(TYPE_TOKEN_RATIO)
                - secondSignatureMap.get(TYPE_TOKEN_RATIO))) * weights[1];
        coincidenceCoefficient += abs((firstSignatureMap.get(HAPAX_LEGOMENA_RATIO)
                - secondSignatureMap.get(HAPAX_LEGOMENA_RATIO))) * weights[2];
        coincidenceCoefficient += abs((firstSignatureMap.get(AVERAGE_SENTENCE_LENGTH)
                - secondSignatureMap.get(AVERAGE_SENTENCE_LENGTH))) * weights[3];
        coincidenceCoefficient += abs((firstSignatureMap.get(AVERAGE_SENTENCE_COMPLEXITY)
                - secondSignatureMap.get(AVERAGE_SENTENCE_COMPLEXITY))) * weights[4];

        return coincidenceCoefficient;
    }

    int findMostSimilar(Double[] coincidenceCoefficient) {
        int i;
        if (coincidenceCoefficient == null) {
            return -1;
        }
        double mostSimilar = coincidenceCoefficient[0];
        int mostSimilarIndex = 0;
        for (i = 0; i < coincidenceCoefficient.length; ++i) {
            if (mostSimilar > coincidenceCoefficient[i]) {
                mostSimilar = coincidenceCoefficient[i];
                mostSimilarIndex = i;
            }
        }
        return mostSimilarIndex;

    }

    String convertInputFileStreamToString(String fileName) {
        String line;
        String inputText = new String();
        try (InputStream input = new FileInputStream(fileName);
             BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(input))) {
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                inputText = inputText + line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return inputText;
    }

    @Override
    public String findAuthor(InputStream mysteryText) {
        int i;
        int featureCounter = 1;
        int authorsCounter = 0;
        int mostSimilarIndex;
        HashMap<FeatureType, Double> featuresMap = new HashMap<>();
        LinguisticSignature unknownSignature;
        String inputText;
        FeatureType[] types = FeatureType.values();
        inputText = convertInputFileStreamToString("./resources/signatures/knownSignatures.txt");
        if (inputText == null) {
            return null;
        }
        String[] featuresFromFile = inputText.split("[\\n,]");
        String[] authorNames = new String[MAX_SIZE];
        Double[] coincidenceCoefficient = new Double[MAX_SIZE];
        unknownSignature = calculateSignature(mysteryText);
        for (i = 0; i < featuresFromFile.length; ++i) {
            if (i % WORDS_PER_LINE == 0) {
                authorNames[authorsCounter] = featuresFromFile[i];
                featureCounter = 0;
                if (i != 0) {
                    LinguisticSignature currentAuthorSignature = new LinguisticSignature(featuresMap);
                    coincidenceCoefficient[authorsCounter] =
                            calculateSimilarity(unknownSignature, currentAuthorSignature);
                    featuresMap.clear();
                    ++authorsCounter;
                }
            } else {
                double value = Double.parseDouble(featuresFromFile[i]);
                featuresMap.put(types[featureCounter], value);
                ++featureCounter;

            }
        }
        mostSimilarIndex = findMostSimilar(coincidenceCoefficient);
        return authorNames[mostSimilarIndex];
    }
}
