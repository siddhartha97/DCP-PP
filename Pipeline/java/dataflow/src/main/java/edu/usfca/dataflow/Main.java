package edu.usfca.dataflow;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.*;
import java.util.Optional;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import edu.usfca.dataflow.utils.User;


public class Main {
  static public void write_toFile(String jsonString) throws IOException{
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/siddharthachowdhuri/Desktop/SEM-II/Data-Processing-Cloud/Personal-Project/DCP-PP/Output/output.json", true));
      bufferedWriter.write(jsonString);
      bufferedWriter.newLine();
      bufferedWriter.close();
  }
  static public void processJson(String filename) throws FileNotFoundException, IOException {
    Gson gson = new GsonBuilder().setLenient().create();
    JsonObject object = (JsonObject) new JsonParser().parse(new FileReader(filename));
    String user_id = "", user_name = "", user_screen_name = "", user_location = "", user_url = "";
    String user_description = "", created_at = "";
    String user_followers = "", user_following = "";
    String verified = ""; String full_text = "";
    String place_type = ""; String place_name = ""; String place_full_name = "";
    String country_code = ""; String country_name = "";

    if(object.has("retweeted_status")) {
       JsonObject re_status = object.get("retweeted_status").getAsJsonObject();
      if(re_status.has("user")) {
        JsonObject user_json = re_status.get("user").getAsJsonObject();
        if(user_json.has("id_str"))
          user_id = user_json.get("id_str").getAsString();
        if(user_json.has("name"))
          user_name = user_json.get("name").getAsString();
        if(user_json.has("screen_name"))
          user_screen_name = user_json.get("screen_name").getAsString();
        if(user_json.has("location"))
          user_location =  Optional.of(user_json.get("location")).get().toString();
        if(user_json.has("url"))
          user_url = Optional.of(user_json.get("url")).get().toString();
        if(user_json.has("description"))
          user_description = Optional.of(user_json.get("description")).get().toString();
        if(user_json.has("followers_count"))
          user_followers = Optional.of(user_json.get("followers_count")).get().toString();
        if(user_json.has("friends_count"))
          user_following = Optional.of(user_json.get("friends_count")).get().toString();
        if(user_json.has("created_at"))
          created_at = Optional.of(user_json.get("created_at")).get().toString();
        if(user_json.has("verified"))
          verified = Optional.of(user_json.get("verified")).get().toString();

      }
      if(re_status.has("extended_tweet")) {
        JsonObject extended_tweet = re_status.get("extended_tweet").getAsJsonObject();
        if(extended_tweet.has("full_text"))
          full_text = Optional.of(extended_tweet.get("full_text")).get().getAsString();
      }

      if(re_status.has("place")) {
        if(!re_status.get("place").isJsonNull()) {
          JsonObject places_obj = re_status.get("place").getAsJsonObject();
          if (places_obj.has("place_type"))
            place_type = Optional.of(places_obj.get("place_type")).get().toString();
          if (places_obj.has("name"))
            place_name = Optional.of(places_obj.get("name")).get().toString();
          if (places_obj.has("full_name"))
            place_full_name = Optional.of(places_obj.get("full_name")).get().toString();
          if (places_obj.has("country_code"))
            country_code = Optional.of(places_obj.get("country_code")).get().toString();
          if (places_obj.has("country"))
            country_name = Optional.of(places_obj.get("country")).get().toString();
        }
      }
      User user = new User(user_id, user_name, user_screen_name,
              user_location, user_url, user_description, user_followers,
              user_following, created_at, verified, full_text, place_type, place_name, place_full_name, country_code, country_name);
      String jsonString = gson.toJson(user);
      write_toFile(jsonString);
    }
    else {
      System.out.println("Nope");
    }
  }
  public static void parse(String directory) {
    Path path = Paths.get(directory);
    try (DirectoryStream<Path> fileList = Files.newDirectoryStream(path)) {
      int ct = 0;
      for(Path file: fileList) {
        if(!Files.isDirectory(file)) {
          String curr_file = file.toString();
          if (curr_file.contains("tweet")) {
            System.out.format("Current file: %s\n", curr_file);
            processJson(curr_file);
          }
        }
        else
          parse(file.toString());
      }
    } catch (IOException error) {
      System.out.println(path);
      System.out.println("No File :" + error);
    }
  }
  public static void main(String[] args)  {
    String directory = "/Users/siddharthachowdhuri/Desktop/SEM-II/Data-Processing-Cloud/Personal-Project-Local/All-Files/";
     try {
       parse(directory);
     } catch (Exception e) {
       System.out.println(e.getMessage());
     }
  }
}
