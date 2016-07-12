package refactoring;

class Rental {
	private Movie _movie;

	public int daysRented() {
		return _daysRented;
	}

	public Movie getMovie() {
		return _movie;
	}

	private int _daysRented;

	public Rental(Movie movie, int daysRented) {
		_movie = movie;
		_daysRented = daysRented;
	}

	public double getCharge() {
		double result = 0;
		switch (getMovie().priceCode()) {
		case Movie.REGULAR:
			result += 2;
			if (daysRented() > 2)
				result += (daysRented() - 2) * 1.5;
			break;
		case Movie.NEW_RELEASE:
			result += daysRented() * 3;
			break;
		case Movie.CHILDRENS:
			result += 1.5;
			if (daysRented() > 3)
				result += (daysRented() - 3) * 1.5;
			break;
		}
		return result;
	}

	public int getFrequentRenterPoints() {
		if ((_movie.priceCode() == Movie.NEW_RELEASE) && _daysRented > 1)
			return 2;
		else 
			return 1;
	}

}
