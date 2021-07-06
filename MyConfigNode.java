import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MyConfigNode {
	
	private String key;
	private List<String> lines;
	private LinkedHashMap<String, MyConfigNode> children;
	
	public MyConfigNode(){
		lines = new ArrayList<String>();
		children = new LinkedHashMap<String, MyConfigNode>();
	}

	public void setKey(String key){ this.key = key; }
	public String getKey(){ return this.key;}
	
	public void setLines(List<String> lines){ this.lines = lines; }
	public List<String> getLines(){ return this.lines; }
	
	public void addChild(String key, MyConfigNode node){ this.children.put(key, node); }
	public HashMap<String, MyConfigNode> getChildren() { return this.children; }
	
	/**
	 * Merges the two nodes together. Preserves the order/comments/new values of the calling node, while overwriting any
	 * existing values already contained in the given node.
	 * @param node
	 */
	public void merge(MyConfigNode node){
		for(MyConfigNode child : node.getChildren().values()){
			children.put(child.getKey(), child);
		}
	}
	
	/**
	 * Gets a string list that its ready to be written to a config file.
	 * @return
	 */
	public List<String> toStringList(){
		List<String> list = new ArrayList<String>();
		for(MyConfigNode child : children.values()){
			list.addAll(child.getLines());
		}
		return list;
	}
}
