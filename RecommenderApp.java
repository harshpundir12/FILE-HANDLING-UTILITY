import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommenderApp {
    public static void main(String[] args) {
        try {
            // Load data from CSV
            File dataFile = new File("data/ratings.csv");
            DataModel model = new FileDataModel(dataFile);

            // Compute user similarity
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Define nearest N neighbors
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build the recommender
            GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend items for user 3
            int userId = 3;
            List<RecommendedItem> recommendations = recommender.recommend(userId, 2);

            System.out.println("Recommendations for user " + userId + ":");
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Item: " + recommendation.getItemID() + " | Estimated Preference: " + recommendation.getValue());
            }

        } catch (IOException | TasteException e) {
            e.printStackTrace();
        }
    }
}