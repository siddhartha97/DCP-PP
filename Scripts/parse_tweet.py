import time
import twitter_credential
from tweepy import Stream
from tweepy import OAuthHandler
from tweepy.streaming import StreamListener



class StdOutListener(StreamListener):

	global ct
	ct = 0

	def on_data(self, data):
   		# print(data)
   		ts = time.time()
   		global ct 
   		ct = ct + 1
   		print("Fetching Tweet #:", ct)
   		with open("Files/" + "tweet_" + str(ts) + ".json", "w") as tf:
   			tf.write(data)
   		return True


	def on_error(self, status):
		print(status)



if __name__ == '__main__':
	listener = StdOutListener()
	auth = OAuthHandler(twitter_credential.CONSUMER_KEY, twitter_credential.CONSUMER_SECRET)
	auth.set_access_token(twitter_credential.ACCESS_TOKEN, twitter_credential.ACCESS_TOKEN_SECRET)

	stream = Stream(auth, listener)

	stream.filter(track=["news"])
