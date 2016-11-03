#!/bin/bash
//usr/bin/env groovy  -cp extra.jar:spring.jar:etc.jar -d -Dlog4j.configuration=file:/etc/myapp/log4j.xml "$0" $@; exit $?
package chooser

import com.sun.corba.se.spi.activation.Repository;

class Chooser {
	static def PEOPLE = "gm_people.txt"

	static void main(String[] args){
		def repo = new Repo()
		if(!repo.repository.exists()) repo.repository.write ""
		def command = args?args[0].trim():"who"
		switch(command){
			case "who":
				if(repo.empty){ println "...none...";break}
				def random = new Random().nextInt(repo.guys.size())
				println repo.guys[random]
				break
			case "+":
			case "-add":
			case "add":
				repo << args[1]
				println "=======> added: "+args[1]
				break
			case "list":
			case "-":
			case "all":
				repo.dump()
				break
			case "-":
			case "-del":
			case "del":
			case "-delete":
			case "-remove":
			case "remove":
				repo >> args[1]
				println "=======> removed: "+args[1]
				break
			default:
				println "sorry?"
		}
	}
	
	static class Repo{
		def repository = new File(PEOPLE)
		def guys = repository.readLines().sort{it}
		
		boolean isEmpty(){guys.empty}
		
		void leftShift(guy){
			guys << guy
			store()
		}
		
		void rightShift(guy){
			guys.remove guy
			store()
		}
	
		def store(){
			backup()
			repository.delete()
			repository << guys.unique().join("\n")
			dump()
		}
	
		def dump(){
			println "=========================> Poeple are:"
			println guys.join("\n")
		}
	
		def backup(){
			def dir = new File("backup")
			if(!dir.exists()) dir.mkdirs()
			new File(dir, System.currentTimeMillis()+"_gm.txt") << repository.text
		}
	}
	
	static store(guys, repository){
		backup(repository)
		repository.delete()
		repository << guys.unique().join("\n")
		dump(guys)
	}

	static dump(guys){
		println "=========================> Poeple are:"
		println guys.join("\n")
	}

	static backup(repository){
		def dir = new File("backup")
		if(!dir.exists()) dir.mkdirs()
		new File(dir, System.currentTimeMillis()+"_gm.txt") << repository.text
	}
}
