# Config-Updater-for-Sponge-Preserves-Format-Order-
While creating a mod using Sponge, I tried using HoconConfigurationLoader and its ConfigurationNodes to update any existing config files with the new configs data, but the problem I had was that when saving the ConfigurationNode back to file, all order is lost and the entire thing becomes sorted alphabetically. I asked and was told that there is no way to change the sorting method, so I decided to create my own versions just for the purpose of updating the config while preserving the ordering of the newest config. You can use this as an alternative for updating the config, then go back to using the originals for all your normal purposes. :)
