# Fabric Example Mod

## Setup

1. Edit gradle.properties with the information of your mod.
```
# Mod Properties
	mod_version = 1.0.0
	maven_group = com.myteam
	archives_base_name = mymod
```

2. Adjust the java classes structures and the resources resources json files to suit your needs:
- The "mixins" object can be removed from mod.json if you do not need to use mixins.
- Please replace all occurences of "modid" with your own mod ID - sometimes, a different string may also suffice. 

3. Run the following command to fetch Fabric dependencies:
```
./gradlew genSources
```

### Optional IDE Setup:

- IntelliJ IDEA: `./gradlew idea`
- Visual Studio Code: see [Setting up Visual Studio Code with Fabric](https://fabricmc.net/wiki/setup:vscode)
- Eclipse: see [Setting up Eclipse with Fabric](https://fabricmc.net/wiki/setup:eclipse)

## Running 

You can simply run the Fabric server with your custom developed mod executing `./gradlew runServer`. (The first time you run this command you will need to accept the eula.txt inside the `/run` folder under the project root)

Sometimes the server daemon will get stuck and your server start can fail, to fix that run `./gradlew --stop`.

## Deploying

You can deploy your mod by running `./gradlew build` - then copy your jar file from the `build/lib` folder to your fabric server `mods` folder.

## License

This template is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.
