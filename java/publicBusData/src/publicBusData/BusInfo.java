package publicBusData;

public class BusInfo {
	private int nodeno; // pk가 될 값
	private double gpslati;
	private double gpslong;
	private String nodeid;
	private String nodenm;

	public BusInfo() {
		super();
	}

	public BusInfo(int nodeno, double gpslati, double gpslong, String nodeid, String nodenm) {
		super();
		this.nodeno = nodeno;
		this.gpslati = gpslati;
		this.gpslong = gpslong;
		this.nodeid = nodeid;
		this.nodenm = nodenm;
	}

	public int getNodeno() {
		return nodeno;
	}

	public void setNodeno(int nodeno) {
		this.nodeno = nodeno;
	}

	public double getGpslati() {
		return gpslati;
	}

	public void setGpslati(double gpslati) {
		this.gpslati = gpslati;
	}

	public double getGpslong() {
		return gpslong;
	}

	public void setGpslong(double gpslong) {
		this.gpslong = gpslong;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodenm() {
		return nodenm;
	}

	public void setNodenm(String nodenm) {
		this.nodenm = nodenm;
	}

	@Override
	public String toString() {
		return "BusInfo [nodeno=" + nodeno + ", gpslati=" + gpslati + ", gpslong=" + gpslong + ", nodeid=" + nodeid
				+ ", nodenm=" + nodenm + "]";
	}

}
