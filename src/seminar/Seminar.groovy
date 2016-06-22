package seminar

class Seminar {
	Course course
	int seats = 0
	String location
	def enrollments = []
	
	def getName(){
		"$course.name ($course.number)"
	}
	
	def getSeatsLeft(){
		seats - enrollments.size()	
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
