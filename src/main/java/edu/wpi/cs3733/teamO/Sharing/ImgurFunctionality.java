package edu.wpi.cs3733.teamO.Sharing;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.*;
import java.util.LinkedList;

public class ImgurFunctionality {

  /**
   * Creates an album on Imgur using bwappteamo@gmail.com credientals
   *
   * @return LinkedList<String> that contains album ID [1], album deleteHash [2]
   * @throws UnirestException
   */
  public static LinkedList<String> createImgurAlbum() throws UnirestException {

    LinkedList<String> result = new LinkedList<>();

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response =
        Unirest.post("https://api.imgur.com/3/album")
            .header("Authorization", "Bearer 87600038c4c60be5eff9d42dd09d5010f8cadde5")
            // if function suddenly doesn't work, get new access token value from Postman
            .field("title", "My Map Images")
            .asString();

    String res = response.getBody();

    // getting album ID to use it in the link
    String startId = res.substring(res.indexOf("id") + 5);
    String albumId = startId.substring(0, startId.indexOf("\""));
    result.add(albumId);

    // getting deleteHash for adding images to this album
    String start = res.substring(res.indexOf("deletehash") + 13);
    String deleteHash = start.substring(0, start.indexOf("\""));
    result.add(deleteHash);

    return result;
  }

  /**
   * Uploads a single image from file directory to Imgur
   *
   * @param imageName (ex. somethingName.png)
   * @return String link of where image is located on Imgur
   * @throws UnirestException
   */
  public static String uploadImage(String imageName) throws UnirestException {
    String home = System.getProperty("user.home");
    String file = home + "/Downloads/" + imageName;

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response =
        Unirest.post("https://api.imgur.com/3/upload")
            .header("Authorization", "Client-ID 546c25a59c58ad7")
            // NOTE: this takes in a client ID token and not an access token
            // also can be generated via Postman
            .field("image", new File(file))
            .asString();

    String b = response.getBody();

    String a =
        b.substring(
            b.indexOf("https")); // If it gives you out of bounds exception your imgur API is broken
    return a.substring(0, a.indexOf("png") + 3);
  }

  /**
   * Uploads an image to an already existing Imgur Album To be used for QR Code Functionality as all
   * images will be stored at one link
   *
   * @param imageName (ex. somethingName.png)
   * @param albumDeleteHash (String at index 2 of returned LL from createImgurAlbum() function)
   * @throws UnirestException
   */
  public static void uploadToImgurAlbum(String imageName, String albumDeleteHash)
      throws UnirestException {
    String home = System.getProperty("user.home");
    String file = home + "/Downloads/" + imageName;

    Unirest.setTimeouts(0, 0);
    HttpResponse<String> response =
        Unirest.post("https://api.imgur.com/3/upload")
            .header("Authorization", "Client-ID 546c25a59c58ad7")
            // NOTE: this takes in a client ID token and not an access token
            // also can be generated via Postman
            .field("image", new File(file))
            .field("album", albumDeleteHash)
            .asString();
  }
}
