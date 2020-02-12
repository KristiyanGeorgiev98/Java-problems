package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.util.HashMap;

public class LinguisticSignature {
    private HashMap<FeatureType, Double> features;

    public LinguisticSignature(HashMap<FeatureType, Double> features) {
        //this.features=new HashMap<>();
        this.features = features;

    }

    public HashMap<FeatureType, Double> getFeatures() {
        return features;

    }


}
