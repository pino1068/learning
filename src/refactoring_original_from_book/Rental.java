package refactoring_original_from_book;

class Rental {

	public Rental(Tape tape, int daysRented) {
		_tape = tape;
		_daysRented = daysRented;
	}
	
	public int getDaysRented() {
		return _daysRented;
	}

	public Tape getTape() {
		return _tape;
	}

	private Tape _tape;

	private int _daysRented;
}
