package toolkit

class stationTest extends FunSuite {
 private val plassebo: String = station(1, "a", "b", "c", position_station(1.2, 3.2), banking = true, bonus = false, 2, 3, 4, "open", 100).getJson
  private val real: String = "{\"number\":".concat(1.toString + ",")
    .concat("\"contract_name\":").concat("\"a\"" + ",")
    .concat("\"name\":").concat("\"b\"" + ",")
    .concat("\"address\":").concat("\"c\"" + ",")
    .concat("\"position\":").concat(position_station(1.2, 3.2).getJson + ",")
    .concat("\"banking\":").concat("true" + ",")
    .concat("\"bonus\":").concat("false" + ",")
    .concat("\"bike_stands\":").concat(2.toString + ",")
    .concat("\"available_bike_stands\":").concat(3.toString + ",")
    .concat("\"available_bikes\":").concat(4.toString + ",")
    .concat("\"status\":").concat("\"open\"" + ",")
    .concat("\"last_update\":").concat(100.toString)
    .concat("}")
  assert(plassebo == real)

}
