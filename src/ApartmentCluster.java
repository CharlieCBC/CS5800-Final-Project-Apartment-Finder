import java.util.ArrayList;
import java.util.List;

public class ApartmentCluster {
  private int priceMin;
  private int priceMax;
  private int distanceMax;
  private List<Apartment> apartments;

  public ApartmentCluster(int priceMin, int priceMax, int distanceMax) {
    this.priceMin = priceMin;
    this.priceMax = priceMax;
    this.distanceMax = distanceMax;
    this.apartments = new ArrayList<>();
  }

  public void addApartment(Apartment apartment) {
    apartments.add(apartment);
  }

  public List<Apartment> getApartments() {
    return apartments;
  }


  public int getPriceMin() {
    return priceMin;
  }

  public int getPriceMax() {
    return priceMax;
  }

  public int getDistanceMax() {
    return distanceMax;
  }
}
