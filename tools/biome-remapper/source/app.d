import std.stdio;
import std.json;
import std.file;
import std.file : fwrite = write;
import std.algorithm;

void main()
{
	string overworldJsonString = readText("overworld.json");
	JSONValue dimensionJson = parseJSON(overworldJsonString);

	string[] keepBiomes = [
		"minecraft:deep_frozen_ocean",
		"minecraft:frozen_ocean",
		"minecraft:deep_cold_ocean",
		"minecraft:cold_ocean",
		"minecraft:deep_ocean",
		"minecraft:ocean",
		"minecraft:deep_lukewarm_ocean",
		"minecraft:lukewarm_ocean",
		"minecraft:warm_ocean",
		];

	string defaultBiome = "rworld:plains";

	string[string] remap = [
		"minecraft:frozen_river": "minecraft:river",
		"minecraft:river": "rworld:river",
		"minecraft:beach": "rworld:beach",

		"minecraft:birch_forest": "rworld:forest",
		"minecraft:flower_forest": "rworld:forest",
		"minecraft:forest": "rworld:forest",
		
		"minecraft:desert": "rworld:red_desert",
	];

	dimensionJson["type"] = "rworld:rworld_type";
	dimensionJson["_generator"] = "Generated with RWorld biome remapper";
	foreach(JSONValue b; dimensionJson["generator"]["biome_source"]["biomes"].array)
	{
		string biome = b["biome"].str;
		if(keepBiomes.countUntil(biome) == -1)
		{
			if(biome in remap)
			{
				b["biome"] = remap[biome];
			}
			else b["biome"] = defaultBiome;
		}
	}
	
	fwrite("../../src/main/resources/data/rworld/dimension/rworld.json", dimensionJson.toString());
}
