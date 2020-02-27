import sys
import json
import time
import urllib.request
from kafka import KafkaProducer
producer = KafkaProducer(bootstrap_servers=sys.argv[3])

while True:
    response = urllib.request.urlopen(sys.argv[2])
    stations = json.loads(response.read().decode())
    for station in stations:
        producer.send(sys.argv[1], json.dumps(station).encode())
    time.sleep(1)