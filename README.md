## Motivation

Billions of users use the social network, which means billions of data is available on the internet. Twitter being one of the social network giants is one of the famous platforms for people to rant, provide insights on unnecessary topics, bash each other, talk about politics, and many more. Primary motivation for choosing the twitter dataset is to analyse the dynamics and structure of the modern society. Almost every active twitter user’s thinking about a certain topic can be altered via fake news. My analysis of the dataset is to find patterns within countries.

### About the data
* The tweets were extracted via [Twitter API](https://developer.twitter.com/en/docs) and [Tweepy](https://www.tweepy.org/)
* Data Format : JSONL

### Obtaining the data
* Wrote a Python script to parse tweets
* Around 30k-100k tweets were extracted per day

### Code Snippet: [link](https://github.com/siddhartha97/Twitter-Data-Analysis/blob/master/Scripts/parse_tweet.py)

```
import​ time
import​ twitter_credential
from​ tweepy ​import​ Stream
from​ tweepy ​import​ OAuthHandler
from​ tweepy.streaming ​import​ StreamListener

class​ ​StdOutListener​(StreamListener)​:
  global​ ct
  ct = ​0
  def​ ​on_data​(self, data)​: 
    ts = time.time()
    global​ ct
    ct=ct + ​1
    print(​"Fetching Tweet #:"​, ct)
    with​ open(​"Files/"​ + ​"tweet_"​ + str(ts) + ​".json"​, "​ w"​) ​as​ tf:
                      tf.write(data)
    return​ ​True
  def​ ​on_error​(self, status)​:
    print(status)
```
### Preprocessing
* Used GSON Parser to parse the Json object and extract important key-values such as *(user_name: “Something”, user_screen_name : “loremipsum”, user_location:
“loremipsum1”)*
* Each Json was stored as a class and written into a file as JSONL format

### Code Snippet: [link](https://github.com/siddhartha97/Twitter-Data-Analysis/blob/master/Pipeline/java/dataflow/src/main/java/edu/usfca/dataflow/Main.java)
```
 static public void processJson(String filename) throws FileNotFoundException, IOException {
    Gson gson = new GsonBuilder().setLenient().create();
    JsonObject object = (JsonObject) new JsonParser().parse(new FileReader(filename));
    String user_id = "", user_name = "", user_screen_name = "", user_location = "", user_url = "";
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
      }
   }
```
### Google Big-Query: 
* Current File Format: JSONL
* Since the size of the file was > 10Mb, we cannot directly upload the JSONL File.
* File was loaded to Google Cloud Storage and then written to Google Big Query 

### Code Snippet:
```
fill something
```

### Table Schema: 
<img src="https://github.com/siddhartha97/Twitter-Data-Analysis/blob/master/preprocess_1.png" alt="img1" class="inline"/>
