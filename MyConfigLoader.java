import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Charsets;

public class MyConfigLoader {
	
	private File file;
	
	public MyConfigLoader(File file){
		if(file.exists()){
			this.file = file;
		}
	}
	
	public void save(MyConfigNode node){
		writeFile(node.toStringList());
	}

	public MyConfigNode load(){
		MyConfigNode rootNode = new MyConfigNode();
		List<String> lines = readFile(file);
		while(!lines.isEmpty()){
			MyConfigNode node = popNextNode(lines);
			if(node!=null){
				rootNode.addChild(node.getKey(), node);
			}
		}
		return rootNode;
	}
	
	private MyConfigNode popNextNode(List<String> list){
		MyConfigNode node = null;
		List<String> lines = new ArrayList<String>();
		int nestedLevel = 0;
		boolean sectionEnd = false;
		while(!sectionEnd && !list.isEmpty()){
			String line = list.get(0).trim();
			lines.add(list.get(0));
			list.remove(0);
			if(line.isEmpty() || line.startsWith("#")){
				continue;
			}
			if(node==null){
				node = new MyConfigNode();
				String keyName = null;
				boolean isQuoted = false;
				int charIndex = -1;
				for(char c : line.toCharArray()){
					charIndex++;
					if(c=='"'){
						isQuoted = !isQuoted; //Toggles whether the characters are still between quotation marks.
					}
					if(isQuoted){
						continue; // Character is within quotes; Do Nothing
					}
					if((c == ' ' || c == '=' || c == ':' || c == '{') && keyName == null){
						keyName = line.substring(0, charIndex).replace("\"", ""); //Remove quotes from name if any
						node.setKey(keyName);
						break;
					}
				}
			}
			boolean isQuoted = false;
			for(char c : line.toCharArray()){
				if(c=='"'){
					isQuoted = !isQuoted; //Toggles whether the characters are still between quotation marks.
				}
				if(isQuoted){
					continue; // Character is within quotes; Do Nothing
				}
				if(c == '[' || c == '{'){
					nestedLevel++; //Descending into nested section
				}
				if(c == ']' || c == '}'){
					nestedLevel--; //Exiting a nested section
				}
			}
			if(nestedLevel==0){ //If reached the end of the first section in list
				sectionEnd=true;
				node.setLines(lines);
			}
		}
		return node;
	}

	
	private List<String> readFile(File file) {
		try {
			return Files.readAllLines(file.toPath());
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	private void writeFile(List<String> toWrite) {
		try {
			Files.newBufferedWriter(file.toPath() , StandardOpenOption.TRUNCATE_EXISTING); // Clear old contents
			Files.write(file.toPath(), toWrite, Charsets.ISO_8859_1); // Write new contents
		} catch (Exception e) {}
	}
	
}
