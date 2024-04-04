import java.nio.file.Paths;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.List;

public class Main {
  public static void main(String[] args) {

    String filePath = Paths.get("database.json").toString();
    String apartmentsJson = readJsonFile(filePath);

    ApartmentSearcher searcher = new ApartmentSearcher();
    searcher.addApartments(apartmentsJson);
    /*searcher.updateApartmentsWithAttributes(attributesJson);  String attributesJson = readJsonFile("milton.json");*/

    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter minimum budget: ");
    int minBudget = scanner.nextInt();
    System.out.print("Enter maximum budget: ");
    int maxBudget = scanner.nextInt();
    System.out.print("Enter maximum distance (in minutes): ");
    int maxDistance = scanner.nextInt();



    List<Apartment> results = searcher.searchApartments(minBudget, maxBudget, maxDistance);
    displayApartments(results);

/*
    System.out.println("Do you want to apply additional filters? (yes/no)");
    String response = scanner.next();
    if (response.equalsIgnoreCase("yes")) {
      System.out.println("Is a gym required? (true/false)");
      Boolean requiresGym = scanner.nextBoolean();
      System.out.println("Must be pet friendly? (true/false)");
      Boolean requiresPetFriendly = scanner.nextBoolean();


      List<Apartment> finalResults = searcher.filterApartments(results, requiresGym, requiresPetFriendly);
      displayApartments(finalResults);
    }

 */

    System.out.println("Do you want to apply additional filters? (yes/no)");
    String response = scanner.next();
    if (response.equalsIgnoreCase("yes")) {
      System.out.println("Is a gym required? (true/false)");
      boolean requiresGym = scanner.nextBoolean();
      System.out.println("Must be pet friendly? (true/false)");
      boolean requiresPetFriendly = scanner.nextBoolean();

      List<Apartment> finalResults = searcher.filterApartments(results, requiresGym, requiresPetFriendly);
      displayApartments(finalResults);
    }
  }


  private static String readJsonFile(String filename) {
    StringBuilder sb = new StringBuilder();
    try {
      FileReader fileReader = new FileReader(filename);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        sb.append(line);
      }
      bufferedReader.close();
      fileReader.close();
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
    return sb.toString();
  }


  private static void displayApartments(List<Apartment> apartments) {
    if (apartments.isEmpty()) {
      System.out.println("No apartments found.");
      System.exit(0);
      return;
    }
    System.out.println("Apartment Results:");
    for (Apartment apartment : apartments) {
      System.out.println("ID: " + apartment.getId());
      System.out.println("Property_url: " + apartment.getName());
      System.out.println("Price: " + apartment.getPrice());
      System.out.println("Distance: " + apartment.getDistance() + " minutes");
      System.out.println("Gym: " + apartment.isHasGym());
      System.out.println("Pet Friendly: " + apartment.isPetFriendly());
      System.out.println("Garage: " + apartment.getGarage());
      System.out.println("--------------------");
    }
  }

}
