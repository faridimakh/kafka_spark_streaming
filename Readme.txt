# obtenir une clé d'accée à l'API, il faut s'inscrire sur le site par avoir l'accé à l'api
# https://developer.jcdecaux.com/#/signup
# tu obtiens takeyjdc
API_KEY = "2a5d13ea313bf8dc325f8783f888de4eb96******"
# l adress ou soliciter l API
url = "https://api.jcdecaux.com/vls/v1/stations?apiKey={}".format(API_KEY)


# demarer une instance zookeeper:
#  bin/zookeeper-server-start.sh config/zookeeper.properties

# demarer un broker kafka
#  bin/kafka-server-start.sh config/server.properties

# creation d'un topic dans le brocker
#  bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic testLogs
# creation d'un topic à 2 replicats et deux 10 partission sur le luster à 2 brockers(serveurs)
#  bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic velib-stations

# verification que le topic "velib-stations" est bien créé
#  bin/kafka-topics.sh --list --bootstrap-server localhost:9092

# supprimer un topic existant:
#  bin/kafka-topics.sh --delete --zookeeper localhost:2181 --topic velib-stations

# demarer le producer
#  bin/kafka-console-producer.sh --broker-list localhost:9092 --topic velib-stations# tu peux ecrire ici >saut kafka

# demarer le consumer
#  bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic velib-stations --from-beginning # tu va voir ici: salut kafka

# infos sur les groupes de consummers:
#  bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

# infos sur n groupe précis:
#  bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group velib-monitor-stations
# plus de detail sur un topic (brockers et partition)
# bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic velib-stations

# kafka manager: manager kafka en mode graphique:
#  git clone https://github.com/yahoo/kafka-manager.git
#  ./sbt clean dist
#for building project : sbt clean package