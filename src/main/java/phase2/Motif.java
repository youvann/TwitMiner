package phase2;
public class Motif {
	private float freq;
	private String val;
	private float support;
		
	public float getFreq() {
		return freq;
	}
	public void setFreq(float freq) {
		this.freq = freq;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public float getSupport() {
		return support;
	}
	public void setSupport(float support) {
		this.support = support;
	}
	public Motif() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Motif [freq=" + freq + ", val=" + val + ", support=" + support
				+ "]";
	}
	
	
}
