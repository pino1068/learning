package servlet

import groovy.servlet.*
import groovy.xml.MarkupBuilder

import javax.servlet.ServletConfig
import javax.servlet.http.*

import org.mortbay.jetty.Server
import org.mortbay.jetty.servlet.*

import seminar.Course
import seminar.Location
import seminar.Seminar
import seminar.SeminarUI
import seminar.Student

@Grab(group='org.mortbay.jetty', module='jetty-embedded', version='6.1.14')
class SimpleGroovyServlet extends HttpServlet {
    def requestHandler
    def context
	
    void init(ServletConfig config) {
        super.init(config)
        context = config.servletContext
    }
	
	void doGet(HttpServletRequest request, HttpServletResponse response) {
		def session = request.session
		page(request, response)
//		page(new MarkupBuilder(response.writer))
	}

	def page(request, response) {
		if(request.requestURL.contains("seminar.txt")){
			def seminar = createSeminar()
			def ui = new SeminarUI()
			ui << seminar
			ui << seminar
			response.writer.println ui.text
		}
		else if(request.requestURL.contains("seminar.html")){
			def seminar = createSeminar()
					def ui = new SeminarUI()
			ui << seminar
			ui << seminar
			response.writer.println ui.html
		}
		else if(request.requestURL.contains("generated")){
			def writer = new StringWriter()
			def html = new MarkupBuilder(writer)
			page2(html)
			response.writer.println writer.toString()
		}
		else{
			def page = request?.parameterMap.page
			def f = new File(page?page[0]:'page.html')
			def engine = new groovy.text.GStringTemplateEngine()
			Map binding = [request:request, session:request.session ]
			def template = engine.createTemplate(f).make(binding)
			response.writer.println template.toString()
		}
	}
	
	static void main(String[] args) {
        def jetty = new Server(8888)
        def context = new Context(jetty, '/', Context.SESSIONS)
        context.addServlet SimpleGroovyServlet, '/*'
        jetty.start()
	}

	void doPost(HttpServletRequest request, HttpServletResponse response) {
		def html = new MarkupBuilder(response.writer)
		def session = request.session
		page(request, response)
//		page(new MarkupBuilder(response.writer))
	}

	private def page2(MarkupBuilder xml) {
		xml.html {
				head {
					title "ciao"
				}
				body2 {
					h1 "My First Heading"
					form (action:"http://localhost:8888/",method:"POST"){
						input (type:"submit",value:"Save",name:"save")
						input (type:"submit",value:"Submit for Approval", name:"approve")
					}
	
				}
		}
	}
	
	
	private Seminar createSeminar() {
		def seminar = new Seminar(
				where: new Location(name:"Lugano",seats:10),
				course:new Course(name:"math", number:3, description:"Mathematics"),
				)
		seminar.enroll(new Student(name:"Alessandro", surname:"Misenta"))
		seminar.enroll(new Student(name:"Giuseppe", surname:"Di Pierri"))
		return seminar
	}

}
