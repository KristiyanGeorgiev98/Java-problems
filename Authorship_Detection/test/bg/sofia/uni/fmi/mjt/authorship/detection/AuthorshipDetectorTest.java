package bg.sofia.uni.fmi.mjt.authorship.detection;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import static bg.sofia.uni.fmi.mjt.authorship.detection.FeatureType.*;
import static junit.framework.TestCase.assertEquals;

public class AuthorshipDetectorTest {
    private static final double NUMBER_OF_COUNT_SENTENCES = 3.0;
    private static final double NUMBER_OF_COUNT_PHRASES = 3.0;
    private static final double NUMBER_OF_COUNT_ALL_WORDS = 14.0;
    private static final double NUMBER_OF_COUNT_ONLY_ONCE_MET_WORDS = 9.0;
    private static final double NUMBER_OF_COUNT_UNIQUE_WORDS = 12.0;

    private static final double NUMBER_OF_AVERAGE_WORD_LENGTH = 2.7857142857142856;
    private static final double NUMBER_OF_TYPE_TOKEN_RATIO = (double) 12 / 14;
    private static final double NUMBER_OF_HAPAX_LEGOMENA_RATIO = (double) 9 / 14;
    private static final double NUMBER_OF_AVERAGE_SENTENCE_LENGTH = (double) 14 / 3;
    private static final double NUMBER_OF_AVERAGE_SENTENCE_COMPLEXITY = 1.0;

    private static final double NUMBER_OF_CALCULATE_SIMILARITY = 0.0;


    public String openTestFileInString() {
        String initialString = "If i was a dragon.Go;do come back;yes you. i was you.";
        return initialString;
    }

    @Test
    public void testCountSentences() {
        String inputString = openTestFileInString();
        AuthorshipDetectorImpl testClass = new AuthorshipDetectorImpl();
        assertEquals(testClass.countSentences(inputString), NUMBER_OF_COUNT_SENTENCES);
    }

    @Test
    public void testCountPhrases() {
        String inputString = openTestFileInString();
        AuthorshipDetectorImpl testClass = new AuthorshipDetectorImpl();
        assertEquals(testClass.countPhrases(inputString), NUMBER_OF_COUNT_PHRASES);
    }

    @Test
    public void testCountAllWords() {
        String inputString = openTestFileInString();
        AuthorshipDetectorImpl testClass = new AuthorshipDetectorImpl();
        assertEquals(testClass.countAllWords(inputString), NUMBER_OF_COUNT_ALL_WORDS);
    }

    @Test
    public void testCountOnlyOnceMetWords() {
        String inputString = openTestFileInString();
        AuthorshipDetectorImpl testClass = new AuthorshipDetectorImpl();
        assertEquals(testClass.countOnlyOnceMetWords(inputString)
                , NUMBER_OF_COUNT_ONLY_ONCE_MET_WORDS);
    }

    @Test
    public void testCountUniqueWords() {
        String inputString = openTestFileInString();
        AuthorshipDetectorImpl testClass = new AuthorshipDetectorImpl();
        assertEquals(testClass.countUniqueWords(inputString),
                NUMBER_OF_COUNT_UNIQUE_WORDS);
    }

    @Test
    public void testCalculateSignature() {
        String initialString = "If i was a dragon.Go;do come back;yes you. i was you.";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        AuthorshipDetectorImpl testClass = new AuthorshipDetectorImpl();
        HashMap<FeatureType, Double> testFeaturesMap = new HashMap<>();
        testFeaturesMap.put(AVERAGE_WORD_LENGTH, NUMBER_OF_AVERAGE_WORD_LENGTH);
        testFeaturesMap.put(TYPE_TOKEN_RATIO, NUMBER_OF_TYPE_TOKEN_RATIO);
        testFeaturesMap.put(HAPAX_LEGOMENA_RATIO, NUMBER_OF_HAPAX_LEGOMENA_RATIO);
        testFeaturesMap.put(AVERAGE_SENTENCE_LENGTH, NUMBER_OF_AVERAGE_SENTENCE_LENGTH);
        testFeaturesMap.put(AVERAGE_SENTENCE_COMPLEXITY, NUMBER_OF_AVERAGE_SENTENCE_COMPLEXITY);
        LinguisticSignature testSignature = new LinguisticSignature(testFeaturesMap);
        assertEquals(testClass.calculateSignature(targetStream).getFeatures(), testSignature.getFeatures());
    }

    @Test
    public void testCalculateSimilarity() {
        HashMap<FeatureType, Double> firstTestFeaturesMap = new HashMap<>();
        firstTestFeaturesMap.put(AVERAGE_WORD_LENGTH, NUMBER_OF_AVERAGE_WORD_LENGTH);
        firstTestFeaturesMap.put(TYPE_TOKEN_RATIO, NUMBER_OF_TYPE_TOKEN_RATIO);
        firstTestFeaturesMap.put(HAPAX_LEGOMENA_RATIO, NUMBER_OF_HAPAX_LEGOMENA_RATIO);
        firstTestFeaturesMap.put(AVERAGE_SENTENCE_LENGTH, NUMBER_OF_AVERAGE_SENTENCE_LENGTH);
        firstTestFeaturesMap.put(AVERAGE_SENTENCE_COMPLEXITY, NUMBER_OF_AVERAGE_SENTENCE_COMPLEXITY);
        LinguisticSignature firstTestSignature = new LinguisticSignature(firstTestFeaturesMap);

        HashMap<FeatureType, Double> secondTestFeaturesMap = new HashMap<>();
        secondTestFeaturesMap.put(AVERAGE_WORD_LENGTH, NUMBER_OF_AVERAGE_WORD_LENGTH);
        secondTestFeaturesMap.put(TYPE_TOKEN_RATIO, NUMBER_OF_TYPE_TOKEN_RATIO);
        secondTestFeaturesMap.put(HAPAX_LEGOMENA_RATIO, NUMBER_OF_HAPAX_LEGOMENA_RATIO);
        secondTestFeaturesMap.put(AVERAGE_SENTENCE_LENGTH, NUMBER_OF_AVERAGE_SENTENCE_LENGTH);
        secondTestFeaturesMap.put(AVERAGE_SENTENCE_COMPLEXITY, NUMBER_OF_AVERAGE_SENTENCE_COMPLEXITY);
        LinguisticSignature secondTestSignature = new LinguisticSignature(secondTestFeaturesMap);

        AuthorshipDetectorImpl testClass = new AuthorshipDetectorImpl();
        assertEquals(testClass.calculateSimilarity(firstTestSignature, secondTestSignature),
                NUMBER_OF_CALCULATE_SIMILARITY);
    }

    @Test
    public void testFindAuthor() {
        String initialString = "If i was a dragon.Go;do come back;yes you. i was you.";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        AuthorshipDetectorImpl testClass = new AuthorshipDetectorImpl();
        assertEquals(testClass.findAuthor(targetStream), "Fyodor Dostoevsky");

    }
}
