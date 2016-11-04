package compare
//@Grab('com.xlson.groovycsv:groovycsv:0.2')
@Grab('com.xlson.groovycsv:groovycsv:1.1')
import com.xlson.groovycsv.CsvParser
import static com.xlson.groovycsv.CsvParser.parseCsv
//import static com.xlson.groovycsv.CsvParser.parseCsv


import java.nio.file.Paths


class CompareCsv {

	static void main(String[] args){
		
//		checkFiles(new File('/home/dev/pino/reports/testing/02.11.2015.csv'), new File('/home/dev/pino/reports/new/02.11.2015.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/02.11.2015.csv'), new File('/home/dev/pino/reports/testing/02.11.2015.csv'))
//		checkFiles(new File('/home/dev/pino/reports/testing/12.01.2016.csv'), new File('/home/dev/pino/reports/new/12.01.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/12.01.2016.csv'), new File('/home/dev/pino/reports/testing/12.01.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/03.05.2016.csv'), new File('/home/dev/pino/reports/testing/03.05.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/16.08.2016.csv'), new File('/home/dev/pino/reports/testing/16.08.2016.csv'))
		checkDirs(new File("/home/dev/pino/reports/testing"), "/home/dev/pino/reports/new")
	}

	private static String checkDirs(File rootFiles, String newDir) {
		rootFiles.eachFile(){ File oldFile ->
			if(oldFile.size() < 36551553 && oldFile.path.contains("csv")){
				def newFile = new File(newDir+"/"+oldFile.absolutePath.split("/").last())
				checkFiles(newFile, oldFile)
				checkFiles(oldFile,	newFile)
			}
		}
	}
	
	static void checkFiles(oldFile, newFile){
		println "************************* checking '"+oldFile.path
		println "************************* -against '"+newFile.path+"'"
		def oldText = oldFile.text
		def newText = newFile.text
		def oldList = parseCsv(fix(oldText)).sort{it.ORDERID}.toList()
		def newList = parseCsv(fix(newText)).sort{it.ORDERID}.toList()
		
		def oldOrderIdLine 	= oldList.groupBy{it.ORDERID}
		def oldPersonLine 	= oldList.groupBy{secondaryKey(it)}
		def newDateLine 	= newList.groupBy{it.CHECKDATE?.substring(0, 10)}
		if(newDateLine.keySet().size()>1)
			println "WARNING: file "+oldFile.path+" contains more dates: "+newDateLine.keySet()
		for(line in newList) {
			try {
				if(line.MERCHANTKEY == 'mobilewalletPaycard')
					continue;
				def oldRecords = oldOrderIdLine[line.ORDERID]
				if(!oldRecords){
					if(newDateLine[line.CHECKDATE.substring(0, 10)]?.size() != 1){
						println "-------------------------"
						println "ERROR: This line is available in file: "+newFile.path +" and not in "+oldFile.path
						println "==> '$line'"
					}//else ignore, because in the old line there is a wrong date that we know as wrong already.
				}else if(oldRecords.size() != 1){
					def oldPersonRecords = oldPersonLine[secondaryKey(line)]
					if(oldPersonRecords.size() != 1){
						println "-------------------------"
						println "ERROR: This line is available in file: "+newFile.path
						println "==> '$line'"
						println "....and $oldRecords.size() times in file '$oldFile.path': "+oldRecords
					}else{
						match2Lines(line, oldPersonRecords.first())
					}
				}else{
					match2Lines(line, oldRecords.first())
				}
			} catch (Exception e) {
				e.printStackTrace()
			}
		}
		println "done!"

	}

	private static String secondaryKey(it) {
		""+it.FIRSTNAME+it.LASTNAME+it.BIRTHDATE+it.CHECKDATE
	}

	private static match2Lines(line, old) {
		def fields = ["CUSTOMERID","ACCOUNTID","ORDERID","GENDER","LANGUAGE","FIRSTNAME","LASTNAME","STREET","ZIP","CITY","BIRTHDATE","CREDITCHECK","CUSTOMERSEARCH","REQUESTAMOUNT","MERCHANTKEY","MERCHANTLIMIT","AVAILABLECREDIT","CHECKDATE","ORDERDATE","MERCHANTID","MERCHANTNAME","ORDERVALUE","CFIRSTORDER","CLASTORDER","CNUMBERORDERS","CORDERSAMOUNT","COPENDEBIT","CMAXWARNINGLEVEL","CLASTWARNING","EXPORTDATE"]
		fields.each{
			def oldValue = prop(it, old)
			def newValue = prop(it, line)
			def orderId = prop("ORDERID", line)
//			if(it == 'ORDERDATE')
//				println oldValue
			if(oldValue!=newValue)
				println "ERROR: $orderId -> $it doesn't match: "+oldValue +" <> "+newValue
		}
	}
	
	private static def prop(name, line){
		try {
			return line[name]
		} catch (MissingPropertyException e) {
			return null
		}
	}
	
	private static def fix(text){
		def result
		def previous = ""
		def min = 100
		text.split("\n").each{
			def size = it.count(",")
			if(size > 10){
				result += previous+it+"\n"
				previous = "" 
			}else{
				println it
				previous = it
			}
		}
		result.replaceAll("\"","")
	}
}
