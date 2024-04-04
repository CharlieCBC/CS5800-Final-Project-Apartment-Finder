import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApartmentSearcher {
  private List<ApartmentCluster> clusters;

  public ApartmentSearcher() {
    clusters = createClusters();
  }

  private List<ApartmentCluster> createClusters() {
    List<ApartmentCluster> clusters = new ArrayList<>();

    int[] budgetRanges = {500, 1000, 1500, 2000, 2500, 3500};
    int[] distanceRanges = {15, 30, 45, 60};

    for (int i = 0; i < budgetRanges.length - 1; i++) {
      for (int j = 0; j < distanceRanges.length; j++) {
        int priceMin = budgetRanges[i];
        int priceMax = budgetRanges[i + 1];
        int distanceMax = distanceRanges[j];
        clusters.add(new ApartmentCluster(priceMin, priceMax, distanceMax));
      }
    }

    return clusters;
  }

  public void addApartments(String jsonData) {
    Gson gson = new Gson();
    Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
    List<Map<String, Object>> apartmentsData = gson.fromJson(jsonData, listType);

    for (Map<String, Object> apartmentData : apartmentsData) {
      String mlsId = (String) apartmentData.get("mls_id");
      String propertyUrl = (String) apartmentData.get("property_url");
      int listPrice = ((Double) apartmentData.get("list_price")).intValue();
      int distance = ((Double) apartmentData.get("distance")).intValue();
      boolean hasGym = (boolean) apartmentData.getOrDefault("gym", false); // Use getOrDefault to handle missing values
      boolean isPetFriendly = (boolean) apartmentData.getOrDefault("pets_allowed", false);
      double garage = (double) apartmentData.getOrDefault("parking_garage", 0);

      Apartment apartment = new Apartment(mlsId, propertyUrl, listPrice, distance, hasGym, isPetFriendly, garage);

      for (ApartmentCluster cluster : clusters) {
        if (apartment.getPrice() >= cluster.getPriceMin()
                && apartment.getPrice() <= cluster.getPriceMax()
                && apartment.getDistance() <= cluster.getDistanceMax()) {
          cluster.addApartment(apartment);
          break;
        }
      }
    }
    System.out.println(apartmentsData);
  }


  public List<Apartment> searchApartments(int minBudget, int maxBudget, int maxDistance) {
    List<Apartment> results = new ArrayList<>();
    for (ApartmentCluster cluster : clusters) {
      if (cluster.getPriceMax() >= minBudget && cluster.getPriceMin() <= maxBudget
              && cluster.getDistanceMax() <= maxDistance) {
        results.addAll(cluster.getApartments());
      }
    }
    return results;
  }

  public List<Apartment> filterApartments(List<Apartment> results, boolean requiresGym, boolean requiresPetFriendly) {
    List<Apartment> filteredList = new ArrayList<>();
    for (Apartment apt : results) {
      if ((!requiresGym || apt.isHasGym()) && (!requiresPetFriendly || apt.isPetFriendly())) {
        filteredList.add(apt);
      }
    }
    return filteredList;
  }

/*
public void updateApartmentsWithAttributes(String attributesJson) {
Gson gson = new Gson();
Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
List<Map<String, Object>> attributeList = gson.fromJson(attributesJson, listType);

Map<Integer, Map<String, Object>> attributesMap = new HashMap<>();
for (Map<String, Object> attributes : attributeList) {
  Integer id = ((Double) attributes.get("mls_id")).intValue();
  attributesMap.put(id, attributes);
}

for (ApartmentCluster cluster : clusters) {
  for (Apartment apartment : cluster.getApartments()) {
    Map<String, Object> additionalAttrs = attributesMap.get(apartment.getId());
    if (additionalAttrs != null) {
      Boolean hasGym = (Boolean) additionalAttrs.get("gym");
      Boolean isPetFriendly = (Boolean) additionalAttrs.get("petFriendly");

      if (hasGym != null) {
        apartment.setHasGym(hasGym);
      }
      if (isPetFriendly != null) {
        apartment.setPetFriendly(isPetFriendly);
      }
    }
    }
  }
}
*/
}
