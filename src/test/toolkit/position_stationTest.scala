package toolkit

import org.scalatest.FunSuite

class position_stationTest extends FunSuite {
  private val placebo="{\"lat\":1.2,\"lng\":3.4}"
  private val real: String = position_station(1.2,3.4).getJson
  assert(real==placebo)
}
