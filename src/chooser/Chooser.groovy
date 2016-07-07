#!/bin/bash
//usr/bin/env groovy  -cp extra.jar:spring.jar:etc.jar -d -Dlog4j.configuration=file:/etc/myapp/log4j.xml "$0" $@; exit $?
package chooser

import com.sun.corba.se.spi.activation.Repository;

class Chooser {
	static def PEOPLE = "gm_people.txt"

	static void main(String[] args){
		def repository = new File(PEOPLE)
		if(!repository.exists()) repository.write ""
		def guys = repository.readLines().sort()
		def command = args?args[0]:"who"
		switch(command){
			case "who":
				if(guys.empty){ println "...none...";break}
				def random = new Random().nextInt(guys.size())
				println guys[random]
				break
			case "+":
			case "-add":
			case "add":
				guys << args[1]
				println "=======> added: "+args[1]
				store(guys, repository)
				break
			case "list":
			case "-":
			case "all":
				dump(repository)
				break
			case "-":
			case "-del":
			case "del":
			case "-delete":
			case "-remove":
			case "remove":
				guys.remove args[1]
				println "=======> removed: "+args[1]
				store(guys, repository)
				break
			default:
				println "sorry?"
		}
	}
	
	static store(guys, repository){
		backup(repository)
		repository.delete()
		repository << guys.unique().join("\n")
		dump(repository)
	}

	static dump(repository){
		println "=======> Poeple are:"
		println repository.text
	}

	static backup(repository){
		def dir = new File("backup")
		if(!dir.exists()) dir.mkdirs()
		new File(dir, System.currentTimeMillis()+"_gm.txt") << repository.text
	}
}
