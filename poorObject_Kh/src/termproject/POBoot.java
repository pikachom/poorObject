package termproject;

public class POBoot {
	int id = 1;
	public void boot() {
		POthread poth = new POthread();		
		poth.threadID = id;
		id++;
		poth.start();
	}
}
