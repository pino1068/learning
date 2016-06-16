package learning

class Seminar {
	Course course
	int seats = 0
	String location
	def enrollments = []
	
	String getName(){
		"$course.name ($course.number)"
	}
	
	String getDescription(){
		course.description
	}
	
	int getSeatsLeft(){
		seats - enrollments.size()
	}
	
	def getStudentList(){
		enrollments*.info //same as: enrollments.collect{it.info}
	}
	
	void enroll(student){
		enrollments << new Enrollment(student:student)
	}
}
