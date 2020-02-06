import json
import time
import urllib.request
from kafka import KafkaProducer

API_KEY = "2a5d13ea313bf8dc325f8783f888de4eb96a8c14"
# l adress ou soliciter l API
url = "https://api.jcdecaux.com/vls/v1/stations?apiKey={}".format(API_KEY)

producer = KafkaProducer(bootstrap_servers="localhost:9092")

while True:
    response = urllib.request.urlopen(url)
    stations = json.loads(response.read().decode())
    for station in stations:
        producer.send("velib-stations", json.dumps(station).encode())
    print("{} Produced {} station records".format(time.time(), len(stations)))
    time.sleep(0.5)
