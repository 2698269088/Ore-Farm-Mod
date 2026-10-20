# generate_assets.ps1
# Copies & renames the reference mod textures (CamelCase -> lowercase) and generates
# all 1.12.2 blockstate / model / recipe JSON files for the Ore Farm mod.
# Run inside PowerShell:  powershell -ExecutionPolicy Bypass -File .\generate_assets.ps1

$ErrorActionPreference = "Stop"

$root   = "D:\Users\26982\Desktop\OreFarm"
$modid  = "ore_farm"
$assets = Join-Path $root "src\main\resources\assets\$modid"
$srcTex = Join-Path $root "Plants-To-Ores_V1.0\assets\plo\textures"

function Write-File($path, $text) {
    # UTF-8 WITHOUT BOM (BOM breaks Minecraft's JSON / lang parsers)
    [System.IO.File]::WriteAllText($path, $text, (New-Object System.Text.UTF8Encoding($false)))
}

# key(lowercase) -> CamelCase original name + center storage block (recipe 'B')
$ores = [ordered]@{
    lapis    = @{ Camel = "Lapis";    Center = "lapis_block"    }
    coal     = @{ Camel = "Coal";     Center = "coal_block"     }
    diamond  = @{ Camel = "Diamond";  Center = "diamond_block"  }
    emerald  = @{ Camel = "Emerald";  Center = "emerald_block"  }
    gold     = @{ Camel = "Gold";     Center = "gold_block"     }
    iron     = @{ Camel = "Iron";     Center = "iron_block"     }
    redstone = @{ Camel = "Redstone"; Center = "redstone_block" }
}

foreach ($sub in @("blockstates","models\block","models\item","recipes","textures\blocks","textures\items","lang")) {
    New-Item -ItemType Directory -Force -Path (Join-Path $assets $sub) | Out-Null
}

foreach ($k in $ores.Keys) {
    $camel  = $ores[$k].Camel
    $center = $ores[$k].Center

    # 1) textures: copy + rename to lowercase
    Copy-Item (Join-Path $srcTex "blocks\Block${camel}Disconnected.png") (Join-Path $assets "textures\blocks\${k}_stem_disconnected.png") -Force
    Copy-Item (Join-Path $srcTex "blocks\Block${camel}Connected.png")     (Join-Path $assets "textures\blocks\${k}_stem_connected.png")     -Force
    Copy-Item (Join-Path $srcTex "items\Block${camel}PlantSeeds.png")     (Join-Path $assets "textures\items\${k}_seeds.png")               -Force

    # 2) blockstate: age/facing variants (melon-like growth + horizontal fruit connection)
    #    age 0..6 facing=up -> stem_growth{age}; age 7 facing=up -> stem_growth7 (mature, no fruit)
    #    age 7 facing=<dir> -> stem_fruit rotated toward the adjacent ore block
    $growth = "${modid}:${k}_stem_growth"
    $fruit  = "${modid}:${k}_stem_fruit"
    $sb = New-Object System.Text.StringBuilder
    [void]$sb.AppendLine("{")
    [void]$sb.AppendLine('  "variants": {')
    for ($a = 0; $a -le 7; $a++) {
        [void]$sb.AppendLine("    ""age=$a,facing=up"": { ""model"": ""$growth$a"" },")
    }
    [void]$sb.AppendLine("    ""age=7,facing=west"": { ""model"": ""$fruit"" },")
    [void]$sb.AppendLine("    ""age=7,facing=east"": { ""model"": ""$fruit"", ""y"": 180 },")
    [void]$sb.AppendLine("    ""age=7,facing=north"": { ""model"": ""$fruit"", ""y"": 90 },")
    [void]$sb.AppendLine("    ""age=7,facing=south"": { ""model"": ""$fruit"", ""y"": 270 }")
    [void]$sb.AppendLine("  }")
    [void]$sb.AppendLine("}")
    Write-File (Join-Path $assets "blockstates\${k}_stem.json") $sb.ToString()

    # 3) block models: vanilla melon/pumpkin stem parents (stem_growth0..7 + stem_fruit)
    $stemTexD  = "${modid}:blocks/${k}_stem_disconnected"
    $stemTexC  = "${modid}:blocks/${k}_stem_connected"
    $itemTex   = "${modid}:items/${k}_seeds"

    for ($a = 0; $a -le 7; $a++) {
        Write-File (Join-Path $assets "models\block\${k}_stem_growth$a.json") `
            ('{ "parent": "block/stem_growth' + $a + '", "textures": { "stem": "' + $stemTexD + '" } }')
    }
    # stem_fruit: base vine uses the disconnected texture, the horizontal connector toward the
    # adjacent ore block uses the connected texture as 'upperstem'.
    Write-File (Join-Path $assets "models\block\${k}_stem_fruit.json") `
        ('{ "parent": "block/stem_fruit", "textures": { "stem": "' + $stemTexD + '", "upperstem": "' + $stemTexC + '" } }')

    # 4) seed item model
    Write-File (Join-Path $assets "models\item\${k}_seeds.json") `
        ('{ "parent": "item/generated", "textures": { "layer0": "' + $itemTex + '" } }')

    # 5) seed crafting recipe: 8 wheat seeds around 1 mineral block
    $seedItem  = "${modid}:${k}_seeds"
    $centerItem = "minecraft:$center"
    $rec = @"
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "SSS",
    "SBS",
    "SSS"
  ],
  "key": {
    "S": { "item": "minecraft:wheat_seeds" },
    "B": { "item": "$centerItem" }
  },
  "result": { "item": "$seedItem", "count": 1 }
}
"@
    Write-File (Join-Path $assets "recipes\${k}_seeds.json") $rec
}

Write-Host "Done. Generated resources under: $assets"
