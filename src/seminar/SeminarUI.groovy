package seminar

import seminar.ui.HtmlSeminarUI
import seminar.ui.TextSeminarUI

class SeminarUI {
	def seminars = []
	
	public String getText() {
		new TextSeminarUI(seminars: seminars).toString()
	}
	
	public String getHtml() {
		new HtmlSeminarUI(seminars: seminars).toString()
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
