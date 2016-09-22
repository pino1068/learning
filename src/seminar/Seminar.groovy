package seminar

class Seminar {
	Course course
	Location where
	Date when = new Date()
	
	def enrollments = []
	
	def getName(){
		"$course.name ($course.number)"
	}
	
	def getDay(){
		when.format("dd.MM.yyyy")
	}
	
	def getSeatsLeft(){
		where.seats - enrollments.size()	
	}
	
	def getDescription(){
		course.description
	}
	
	void enroll(Student student){
		enrollments << new Enrollment(student:student)
	}
	
	def getStudentList(){
		enrollments*.info
	}
	
	def printOn(printer){
		printer.printSeminar(this)
	}
}
