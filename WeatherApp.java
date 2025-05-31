import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {

    // Replace with your API key from https://openweathermap.org/api
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        String city = "New York"; // You can make this dynamic (e.g., using Scanner)
        String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject json = new JSONObject(response.toString());

                String cityName = json.getString("name");
                double temp = json.getJSONObject("main").getDouble("temp");
                int humidity = json.getJSONObject("main").getInt("humidity");
                String weather = json.getJSONArray("weather")
                                     .getJSONObject(0)
                                     .getString("description");

                System.out.println("=== Weather Information ===");
                System.out.println("City: " + cityName);
                System.out.println("Temperature: " + temp + "°C");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Condition: " + weather);

            } else {
                System.out.println("Error: HTTP response code " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}