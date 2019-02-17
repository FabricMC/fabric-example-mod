# Fabric Example Mod

## Setup

1. Edit gradle.properties with the information of your mod.
```
# Mod Properties
	mod_version = 1.0.0
	maven_group = net.fabricmc
	archives_base_name = fabric-example-mod
```

2. Run the following command to fetch Fabric dependencies:

```
./gradlew genSources
```

### Optional IDE Setup:

- IntelliJ IDEA: `./gradlew idea`
- Visual Studio Code: see [Setting up Visual Studio Code with Fabric](https://fabricmc.net/wiki/setup:vscode)
- Eclipse: see [Setting up Eclipse with Fabric](https://fabricmc.net/wiki/setup:eclipse)

## License

This template is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.
