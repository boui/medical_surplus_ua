package models

import java.io._
import scala.xml._

object HtmlGeneration {

  
   
     def rest(parsedCSV: Iterator[Array[String]], ownerId: String): Unit =
     {
       // TODO: set date.
       val outputFname = Settings.outputDir+"/"+ownerId+"/last.html"
       val file = new File(outputFname)
       val output = new OutputStreamWriter(new FileOutputStream(file), "UTF8")
       // TODO: use template.
       output.write(
          <html>
           <head>
             <meta charset="utf8" />
             <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />
           </head>
           <body>
             <div class="container">
              Перечень медпрепаратов на складе Физраствор по состоянию на XXX
             <div>
             <table class="table">
               {for(it <- parsedCSV) yield mkTableLine(it)}
             </table>
             </div>
             </div>
           </body>
          </html>.toString
       )
       output.flush()
       output.close()
     }

     def mkTableLine(line: Array[String]): NodeSeq =
     { System.out.println("received:"+line.toList)
       <tr border="1">
         {if (line.length > 2) {
           <td>{line(0)}</td>
           <td>{line(line.length-2)}</td>
          } else {
           <td>---</td>
           <td></td>
          }
         }
       </tr>
     }
     
         
}
