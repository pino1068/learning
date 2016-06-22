package seminar

class SeminarUI {
	Seminar seminar
	
	@Override
	public String toString() {
		if(seminar)
			return printSeminar()
		"no seminar";
	}

	private def printSeminar() {
		def students = seminar.studentList.join("\n\t\t")
		"""
	------------ $seminar.name ------------
	$seminar.description in $seminar.location
	seats left: $seminar.seatsLeft
	Students:
		$students
"""
	}
	

}
