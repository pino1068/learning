package seminar.ui

import seminar.Seminar

class TextSeminarUI {
	def seminars
	
	String toString(){
		if(seminars.empty)
			return "no seminar";
		seminars*.printOn(this).join("\n")
	}
	
	String printSeminar(seminar) {
		def students = seminar.studentList.join("\n\t\t")
		"""
	------------ $seminar.name ------------
	$seminar.description in $seminar.where
	seats left: $seminar.seatsLeft
	Students:
		$students"""
	}

}
