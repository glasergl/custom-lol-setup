# Custom LoL Setup
Store match-up based setups where a setup consists of two summoner spells, a rune page, item set and some text notes.

## Example
<p align="center">
<img src="example.png" width="900"/>
</p>

## Migration Note
When items or runes get removed, this tool will ignore their values in the stored `.json` when deserializing and not add them again when serializing.
