package decorPckg;

import intPckg.DataSource;
// abtract klasa koja sluzi kao omotac, dekorator kako bi svaki iduća dekorator klasa koja ima iste metode mogla naslijediti, override metoda
public abstract class DataSourceDecorator implements DataSource {
	
	protected DataSource wrapper;
	
	public DataSourceDecorator(DataSource source) {
		this.wrapper = source;
	}

	@Override
	public String readData() {
		// TODO Auto-generated method stub
		return wrapper.readData();
	}

	@Override
	public void writeData(String data) {
		// TODO Auto-generated method stub
		wrapper.writeData(data);
	}
	
	

}