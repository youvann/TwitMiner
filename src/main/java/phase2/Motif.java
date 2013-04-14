package phase2;

import java.util.List;

public class Motif {
	private int freq;
	private String val;
	private int support;
		
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public int getSupport() {
		return support;
	}
	public void setSupport(int support) {
		this.support = support;
	}
	public Motif(int freq) {
		this.freq = freq;
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
