package seminar

class SeminarUI {
	def seminars = []
	
	public String getText() {
		if(seminars.empty)
			return "no seminar";
		printSeminars()
	}

	private def printSeminars() {
		seminars*.printOn(this).join("\n")
	}

	String printSeminar(seminar) {
		def students = seminar.studentList.join("\n\t\t")
		def result = """
	------------ $seminar.name ------------
	$seminar.description in $seminar.location
	seats left: $seminar.seatsLeft
	Students:
		$students"""
		return result
	}
	
	def leftShift(seminar){
		seminars << seminar
	}	

}
