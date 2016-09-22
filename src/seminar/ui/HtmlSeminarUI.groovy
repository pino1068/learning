package seminar.ui

import groovy.xml.MarkupBuilder
import seminar.Seminar

class HtmlSeminarUI {
	def seminars
	
	String toString(){
		def writer = new StringWriter()
		def html = new MarkupBuilder(writer)
		page(html)
		writer.toString()
	}
	
	String page(xml) {
		xml.html {
				head {
					title "Your Title Here"
				}
				body (BGCOLOR:"FFFFFF") {
					h1 "Seminars"
					seminars.each{ Seminar seminar ->
						h2 "$seminar.name in $seminar.where"
						p "$seminar.seatsLeft seats left"
						p "Starting date: $seminar.day"
						p{ b "Students are:"}
						ul{
							seminar.studentList.each{
								li it 
							}
						}
					}
				}
		}
	}
}
