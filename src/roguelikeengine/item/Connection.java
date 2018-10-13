package roguelikeengine.item;

import stat.StatContainer;

public class Connection {
	public Item origin;

	public Item destination;
	
	public StatContainer stats;
	

	public Connection(Item origin, Item destination) {
		this.origin = origin;
		this.destination = destination;
		this.stats = new StatContainer();
	}
	
	public Connection(Item origin, Item destination, StatContainer stats) {
		this(origin, destination);
		this.stats.addAllStats(stats);
	}
}
