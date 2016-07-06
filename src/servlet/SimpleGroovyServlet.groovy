package servlet

import org.mortbay.jetty.servlet.*
import org.mortbay.jetty.Server
import groovy.servlet.*
import groovy.xml.MarkupBuilder
import javax.servlet.http.*
import javax.servlet.ServletConfig

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
		def f = new File(request.parameterMap.page[0]?:'page.html')
		def engine = new groovy.text.GStringTemplateEngine()
		Map binding = [request:request, session:request.session ]
		def template = engine.createTemplate(f).make(binding)
		response.writer.println template.toString()
	}
	
	static void main(String[] args) {
        def jetty = new Server(8080)
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

	private page2(MarkupBuilder xml) {
		xml.html {
				head {
					title "ciao"
				}
				body2 {
					h1 "My First Heading"
					form (action:"http://localhost:8080/",method:"POST"){
						input (type:"submit",value:"Save",name:"save")
						input (type:"submit",value:"Submit for Approval", name:"approve")
					}
	
				}
		}
	}
}
