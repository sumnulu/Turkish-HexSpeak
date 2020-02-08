import scala.util.matching.Regex
object Main extends App {

  val url = "https://raw.githubusercontent.com/mertemin/turkish-word-list/master/words.txt"
  val turkishDic = scala.io.Source
    .fromURL(url)
    .getLines()
    .toList

  println(s"Number of DIC size ${turkishDic.size}")
  def toUpperCaseASCII(s: String): String =
    s.toUpperCase
      .replace('İ', 'I')
      .replace('Ü', 'U')
      .replace('Ğ', 'G')
      .replace('Ş', 'S')
      .replace('Ö', 'O')
      .replace('Ç', 'C')

  val hexMapping: Map[Char, Char] = Map(
    'A' -> 'A',
    'B' -> 'B',
    'C' -> 'C',
    'D' -> 'D',
    'E' -> 'E',
    'F' -> 'F',
    'O' -> '0',
    'L' -> '1',
    'S' -> '5',
    'I' -> '1',
    '1' -> '1',
    '2' -> '2',
    '3' -> '3',
    '4' -> '4',
    '5' -> '5',
    '6' -> '6',
    '7' -> '7',
    '8' -> '8',
    '9' -> '9',
    '0' -> '0',
    ' ' -> '_',
    '_' -> '_'
  )

  val genRegexString = hexMapping.keys.mkString("[", "", "]+")
  val canBeHexRegex = new Regex(genRegexString)

  def toHexSpeak(s: String): String = s.map(hexMapping)

  val result: List[(String, String)] = (turkishDic zip turkishDic.map(toUpperCaseASCII))
    .filter(x => canBeHexRegex.matches(x._2))
    .map(x => x._1 -> toHexSpeak(x._2))

  println(s"Total # turkish hex speak words: ${result.size}")

  val prettyString: List[String] = result
    .map(x => x._1.padTo(20, ' ') + " ->   " + x._2)

  println("Turkish Word".padTo(26, ' ') + "HEX SPeak")
  println(prettyString.mkString("\n"))

}
