package toolkit
case class position_station private(lat: Double, lng: Double) extends imposeSomeMethods {
  override def getJson: String = s"{'lat':$lat,'lng':$lng}".replaceAll("'", "\"")
}

case class station(number: Int,
                   contract_name: String,
                   name: String,
                   address: String,
                   position: position_station,
                   banking: Boolean,
                   bonus: Boolean,
                   bike_stands: Int,
                   available_bike_stands: Int,
                   available_bikes: Int,
                   status: String,
                   last_update: Long) extends imposeSomeMethods {
  override def getJson: String = s"{'number':$number,'contract_name':'$contract_name','name':'$name','address':'$address','position':${position.getJson},'banking':$banking,'bonus':$bonus,'bike_stands':$bike_stands,'available_bike_stands':$available_bike_stands,'available_bikes':$available_bikes,'status':'$status','last_update':$last_update}".replaceAll("'","\"")

}
