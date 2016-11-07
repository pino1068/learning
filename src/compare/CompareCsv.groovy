package compare
//@Grab('com.xlson.groovycsv:groovycsv:0.2')
@Grab('com.xlson.groovycsv:groovycsv:1.1')
import com.xlson.groovycsv.CsvParser
import static com.xlson.groovycsv.CsvParser.parseCsv
//import static com.xlson.groovycsv.CsvParser.parseCsv


import java.nio.file.Paths
import java.text.DecimalFormat


class CompareCsv {

	static void main(String[] args){
//		checkFiles(new File('/home/dev/pino/reports/testing/02.11.2015.csv'), new File('/home/dev/pino/reports/new/02.11.2015.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/02.11.2015.csv'), new File('/home/dev/pino/reports/testing/02.11.2015.csv'))
//		checkFiles(new File('/home/dev/pino/reports/testing/12.01.2016.csv'), new File('/home/dev/pino/reports/new/12.01.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/12.01.2016.csv'), new File('/home/dev/pino/reports/testing/12.01.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/03.05.2016.csv'), new File('/home/dev/pino/reports/testing/03.05.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/03.06.2016.csv'), new File('/home/dev/pino/reports/testing/03.06.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/16.08.2016.csv'), new File('/home/dev/pino/reports/testing/16.08.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/07.06.2016.csv'), new File('/home/dev/pino/reports/testing/07.06.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/05.07.2016.csv'), new File('/home/dev/pino/reports/testing/05.07.2016.csv'))
//		checkFiles(new File('/home/dev/pino/reports/new/11.07.2016.csv'), new File('/home/dev/pino/reports/testing/11.07.2016.csv'))
		checkFiles(new File('/home/dev/pino/reports/new/14.12.2015.csv'), new File('/home/dev/pino/reports/testing/14.12.2015.csv'))
//		checkDirs(new File("/home/dev/pino/reports/testing"), "/home/dev/pino/reports/new")
	}

	def static report = new Report()
	
	private static String checkDirs(File rootFiles, String newDir) {
		report.info "*************************"
		report.info "Reading from Dir: "+rootFiles.path+" against dir: $newDir"
		report.info "*************************"
		def init = System.currentTimeMillis()
		int count = 0
		def files = rootFiles.listFiles().findAll{it.size() < 36551553 && it.path.endsWith(".csv")}.sort{it.name}
		int fileNumber = files.size()
		files.each{ File oldFile ->
			if(oldFile.size() < 36551553 && oldFile.path.endsWith(".csv")){
				def newFile = new File(newDir+"/"+oldFile.absolutePath.split("/").last())
				checkFiles(newFile, oldFile)
				count++
				def deltaNr = (System.currentTimeMillis()-init)/1000
				def delta 	= new DecimalFormat("#####").format(deltaNr)
				def mean 	= new DecimalFormat("#####").format(deltaNr/count)
				report.debug "$count files read over $fileNumber in total: $delta sec (time: $mean sec/file)"
			}
		}
		report.info "The End."
	}
	
	static class Report{
		def debugFile, infoFile
		
		Report(){
			def dir = new File("backup")
			if(!dir.exists()) dir.mkdirs()
			debugFile = new File(dir, System.currentTimeMillis()+"_report_debug.txt")
			infoFile = new File(dir, System.currentTimeMillis()+"_report_info.txt")
		}
		void info(message){
			println message
			infoFile << message +"\n"
			debug(message)
		}
		void debug(message){
			debugFile << message +"\n"
		}
	}
	
	static void checkFiles(oldFile, newFile){
		def init = System.currentTimeMillis()
		def oldPath = oldFile.path
		def newPath = newFile.path
		def oldText = oldFile.text
		def newText = newFile.text
		if(oldText.trim() == ''){
			report.debug "empty file: "+oldPath
			return
		}
		if(newText.trim() == ''){
			report.debug "empty file: "+newPath
			return
		}
		def oldList = parseCsv(fix(oldText)).sort{it.ORDERID}.toList()
		def newList = parseCsv(fix(newText)).sort{it.ORDERID}.toList()
		def kilobytes = oldFile.length()/1000
		report.info "************************* checking file: "+oldPath.split("/").last()+" ($kilobytes KB)"
		check(oldList, newList, oldPath, newPath)
		check(newList, oldList, newPath, oldPath)//inverted
		def delta = (System.currentTimeMillis()-init)
		report.debug "done in "+delta+" msec"
	}

	private static void check(List oldList, List newList, String oldPath, String newPath) {
		def oldOrderIdLine 	= oldList.groupBy{orderIdKey(it)}
		def newOrderIdLine 	= newList.groupBy{orderIdKey(it)}
		def newDateLine 	= newList.groupBy{thirdKey(it)}
		def oldPersonLine 	= oldList.groupBy{personKey(it)}
		def newPersonLine 	= newList.groupBy{personKey(it)}
		if(newDateLine.keySet().size()>1){
			report.info "WARNING: file "+newPath+" contains more dates: "+newDateLine.keySet()
		}
		for(line in newList) {
			try {
				if(line.MERCHANTKEY == 'mobilewalletPaycard')
					continue;
				def oldRecords = oldOrderIdLine[orderIdKey(line)]
				def newRecords = newOrderIdLine[orderIdKey(line)]
				if(!oldRecords){
					newDateLine 	= newList.groupBy{line.CHECKDATE}
					if(newDateLine[line.CHECKDATE]?.size() != 1){
						report.info "ERROR: This line is available in file: "+newPath +" and not in "+oldPath
						report.info "==> '$line'"
					}//else ignore, because in the old line there is a wrong date that we know as wrong already.
				}else if(oldRecords.size() != newRecords.size()){
					def oldPersonRecords = oldPersonLine[personKey(line)]
					def newPersonRecords = newPersonLine[personKey(line)]
					if(oldPersonRecords.size() != newPersonRecords.size()){
						report.info "ERROR: This line is available in file: "+newPath
						report.info "==> '$line'"
						report.info "==>  and $oldPersonRecords.size() times in file '$oldPath': "+oldPersonRecords
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
//		report.debug "done!"
	}
	private static orderIdKey(it) {
		it.ORDERID.trim()
	}

	private static personKey(it) {
		""+it.FIRSTNAME+it.LASTNAME+it.BIRTHDATE+it.CHECKDATE
	}
	
	private static thirdKey(it) {
		it.CHECKDATE?.substring(0, 10)
	}


	private static match2Lines(line, old) {
		def fields = ["CUSTOMERID","ACCOUNTID","ORDERID","GENDER","LANGUAGE","FIRSTNAME","LASTNAME","STREET","ZIP","CITY","BIRTHDATE","CREDITCHECK","CUSTOMERSEARCH","REQUESTAMOUNT","MERCHANTKEY","MERCHANTLIMIT","AVAILABLECREDIT","CHECKDATE","ORDERDATE","MERCHANTID","MERCHANTNAME","ORDERVALUE","CFIRSTORDER","CLASTORDER","CNUMBERORDERS","CORDERSAMOUNT","COPENDEBIT","CMAXWARNINGLEVEL","CLASTWARNING","EXPORTDATE"]
		fields.each{
			def oldValue = prop(it, old)
			def newValue = prop(it, line)
			def orderId = prop("ORDERID", line)
			if(oldValue!=newValue)
				report.debug "ERROR: $orderId -> $it doesn't match: "+oldValue +" <> "+newValue
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
		text.split("\n").each{
			def size = it.count(",")
			if(size > 10){
				result += previous+it+"\n"
				previous = "" 
			}else{
				report.debug it
				previous = it
			}
		}
		result.replaceAll("\"","")
	}
}
