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

	string defaultBiome = "ataletoldthroughtime:plains";

	string[string] remap = [
		"minecraft:frozen_river": "minecraft:river",
		"minecraft:river": "ataletoldthroughtime:river",
		"minecraft:beach": "ataletoldthroughtime:beach",

		"minecraft:birch_forest": "ataletoldthroughtime:forest",
		"minecraft:flower_forest": "ataletoldthroughtime:forest",
		"minecraft:forest": "ataletoldthroughtime:forest",
		
		"minecraft:desert": "ataletoldthroughtime:red_desert",
	];

	dimensionJson["type"] = "ataletoldthroughtime:overworld_type";
	dimensionJson["generator"]["settings"] = "ataletoldthroughtime:overworld";
	dimensionJson["_generator"] = "Generated with ATTT biome remapper";
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
	
	fwrite("../../src/main/resources/data/ataletoldthroughtime/dimension/overworld.json", dimensionJson.toString());
}
