package seminar

class SeminarUI {
	def seminars = []
	
	public String getText() {
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
	
	def leftShift(seminar){
		seminars << seminar
	}	
	
	static void main(String[] args){
		def seminar = new Seminar(
			where: new Location(name:"Lugano",seats:10),
			course:new Course(name:"math", number:3, description:"Mathematics"),
				)
		seminar.enroll(new Student(name:"Alessandro", surname:"Misenta"))
		seminar.enroll(new Student(name:"Giuseppe", surname:"Di Pierri"))
		
		SeminarUI ui = new SeminarUI()
		ui << seminar
		ui << seminar
		
		println ui.text
	}


}
