import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.spongepowered.api.Sponge;

import com.carnagechaos420.pixelmonaddonsc.Main;
import com.carnagechaos420.pixelmonaddonsc.Reference;

public class ConfigUpdater {
	
	private Main plugin; //Sponge Plugin Instance
	
	public ConfigUpdater(Main plugin){
		this.plugin = plugin;
	}
	
	private File newConfig;
	private File oldConfig;

	public void checkUpdate(Path configPath) {
		String path = configPath.getParent().toString();
		oldConfig = new File(path+File.separator+Reference.MOD_ID+".old.conf");
        newConfig = new File(path+File.separator+Reference.MOD_ID+".conf");
        if(newConfig.exists()){
        	newConfig.renameTo(oldConfig); // Config already exists; Change name to old.
        }
		try {
			// Load in the latest default Config. Edit to taste if you want to load in config file a different way.
			Sponge.getAssetManager().getAsset(plugin, "default.conf").get().copyToFile(newConfig.toPath(), false, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
        if(oldConfig.exists()){
        	// If there was a pre-existing Config file, copy over the old settings from it.
			updateConfig();
        }
		
	}
	
	public void updateConfig(){
		MyConfigLoader oldConfigLoader = new MyConfigLoader(oldConfig);
		MyConfigLoader newConfigLoader = new MyConfigLoader(newConfig);
		MyConfigNode oldConfigNode = oldConfigLoader.load();
		MyConfigNode newConfigNode = newConfigLoader.load();
		newConfigNode.merge(oldConfigNode);
		newConfigLoader.save(newConfigNode);
		oldConfig.delete(); // Now that we've copied over old config values we no longer need file.
	}

}
