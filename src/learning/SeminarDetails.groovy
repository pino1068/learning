package learning

class SeminarDetails {
	Seminar seminar
	
	static void main(String[] args) {
		Seminar seminar = new Seminar(
			course:math(),
			seats:10,
			location:"Lugano"
			)
		seminar.enroll stefano()
		seminar.enroll pino()
		println new SeminarDetails(seminar:seminar).description
	}

	private static Course math() {
		new Course(name:"math", number:"1", description:"Math fundamentals")
	}

	private static Student pino() {
		new Student(name:"Giuseppe",lastname:"Di Pierri")
	}

	private static Student stefano() {
		new Student(name:"Stefano",lastname:"Coluccia")
	}

	String getDescription(){
		def enrollments = seminar.studentList.join(" - ")
"""		----- Seminar: $seminar.name -----
		********
		course: $seminar.description 
		********
		where: $seminar.location  
		seats left: $seminar.seatsLeft
		----------
		Enrolled students:
		$enrollments
"""
	}
}
