package cn.edu.pku.ss.test.inject;

public interface IStumpManager {
	public StumpTable getStumps();
	public Stump create(Object node, Stump stump);
	//public String getStumpCode(Stump stump);
}
