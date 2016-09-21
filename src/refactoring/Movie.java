package refactoring;

public class Movie {
    public static final int  CHILDRENS = 2;
    public static final int  REGULAR = 0;
    public static final int  NEW_RELEASE = 1;

    private String _name;
	private int _priceCode;

	public Movie(String name, int priceCode) {
		_name = name;
		_priceCode = priceCode;
	}

	public int priceCode() {
		return _priceCode;
	}

	public String name() {
		return _name;
	}
}