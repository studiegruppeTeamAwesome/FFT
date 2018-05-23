package data;

public class SqlResult {

	private boolean succes;
	private int autoKey;

	public SqlResult() {
		this.succes = false;
		this.autoKey = -1;
	}

	public SqlResult(boolean succes, int autoKey) {
		this.succes = succes;
		this.autoKey = autoKey;
	}

	public boolean isSucces() {
		return succes;
	}

	public void setSucces(boolean succes) {
		this.succes = succes;
	}

	public int getAutoKey() {
		return autoKey;
	}

	public void setAutoKey(int autoKey) {
		this.autoKey = autoKey;
	}
}
