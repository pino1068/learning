package refactoring;

public class Lines {

	private String result="";

	public static Lines _(String init){
		Lines lines = new Lines();
		lines.add(init);
		return lines;
	}

	public Lines add(String string) {
		result += string+"\n";
		return this;
	}
	
	@Override
	public String toString() {
		return result.substring(0, result.length()-1);
	}

	public Lines indent(String string) {
		return add("\t"+string);
	}
}
