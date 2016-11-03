package compare
@Grab('com.xlson.groovycsv:groovycsv:0.2')
import com.xlson.groovycsv.CsvParser


import java.nio.file.Paths


class CompareCsv {

	static void main(String[] args){
		def newData = new CsvParser().parse(new File('csv/gm_scorecards_2015_2016.csv').text)
		println "read new data: "+newData.size()
		def oldData = new CsvParser().parse(new File('csv/2015_2016.csv').text)
		println "read old data: "+oldData.size()
		
		def oldLine = oldData.sort{it.ORDERID}.toList().groupBy{it.ORDERID}
		def list 	= newData.sort{it.ORDERID}.toList()//.subList(0,5)
		for(line in list) {
			def oldRecords = oldLine[line.ORDERID]
			if(oldRecords.size() != 1){
				println "-------------------------"
				println "for $line"
				println "....found: "+oldRecords
			}else{
				def old = oldRecords[0]
				def fields = ["CUSTOMERID","ACCOUNTID","ORDERID","GENDER","LANGUAGE","FIRSTNAME","LASTNAME","STREET","ZIP","CITY","BIRTHDATE","CREDITCHECK","CUSTOMERSEARCH","REQUESTAMOUNT","MERCHANTKEY","MERCHANTLIMIT","AVAILABLECREDIT","CHECKDATE","ORDERDATE","MERCHANTID","MERCHANTNAME","ORDERVALUE","AFIRSTORDER","ALASTORDER","ANUMBERORDERS","AORDERSAMOUNT","AOPENDEBIT","AMAXWARNINGLEVEL","ALASTWARNING","CFIRSTORDER","CLASTORDER","CNUMBERORDERS","CORDERSAMOUNT","COPENDEBIT","CMAXWARNINGLEVEL","CLASTWARNING","EXPORTDATE"]
				fields.each{
//					println "--------------------------------"
//					println "**$it"
					def orderId = line.ORDERID
					def oldValue = old[it]
					def newValue = line[it]
//					println "$oldValue == $newValue?"
//					println (oldValue!=newValue)
					if(oldValue!=newValue)
						println "$orderId -> $it doesn't match: "+oldValue +" <> "+newValue
				}
			}
////			println oldRecords.size()
//			if(oldRecords.size() == 0)
//				println "not found: "+line
////			println matching(oldRecords, line)
////			println "$line.CUSTOMERID $line.ACCOUNTID"
		}
		println "done!"
	}
}
