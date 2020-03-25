package toolkit

class functionsTest extends FunSuite {

  test("testProcess_data_api") {
  }

    private val plassibo = Seq("lat", "lng")
//    private val real: immutable.Seq[String] = getArgsFromCaseClass[position_station]
    private val real= getArgsFromCaseClass[position_station]
  test("testGetArgsFromCaseClass") {
    assert(plassibo.min == real.min)
    assert(plassibo.max == real.max)
    assert(plassibo.sorted.reduce(_+_) == real.sorted.reduce(_ + _))
  }

  test("testConfigFormat_to_MapFormat") {
  }

}
