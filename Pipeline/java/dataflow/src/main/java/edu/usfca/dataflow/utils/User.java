package edu.usfca.dataflow.utils;

import java.math.BigInteger;

public class User {
    String user_id = "";
    String user_name = "";
    String user_screen_name = "";
    String user_location = "";
    String user_url = "";
    String user_description = "";
    String  user_followers = "";
    String user_following = "";
    String created_at = "";
    String verified = "";
    String full_text = "";
    String place_type = "";
    String place_name = "";
    String place_full_name = "";
    String country_code = "";
    String country_name = "";

    public User(String user_id, String user_name, String user_screen_name, String user_location, String user_url, String user_description, String user_followers, String user_following, String created_at, String verified, String full_text, String place_type, String place_name, String place_full_name, String country_code, String country_name) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_screen_name = user_screen_name;
        this.user_location = user_location;
        this.user_url = user_url;
        this.user_description = user_description;
        this.user_followers = user_followers;
        this.user_following = user_following;
        this.created_at = created_at;
        this.verified = verified;
        this.full_text = full_text;
        this.place_type = place_type;
        this.place_name = place_name;
        this.place_full_name = place_full_name;
        this.country_code = country_code;
        this.country_name = country_name;
    }
}
