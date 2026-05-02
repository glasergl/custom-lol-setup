package de.glasergl.custom.lol.setup.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import de.glasergl.custom.lol.setup.model.entity.Champion;
import de.glasergl.custom.lol.setup.model.entity.Setup;
import de.glasergl.custom.lol.setup.model.entity.Setups;
import static de.glasergl.jlatex.LatexCommand.command;

import de.glasergl.jlatex.GeneratePdf;
import de.glasergl.jlatex.LatexDocument;

public class PdfExport {
    public PdfExport(final Setups setups) {
	final LatexDocument latexDocument = new LatexDocument("scrartcl");
	latexDocument.usePackage("inputenc", "utf8")
	.usePackage("fontenc", "T1")
	.usePackage("babel", "english")
	.usePackage("lmodern")
	.usePackage("graphicx")
	.usePackage("hyperref", "hidelinks")
	.line(command("renewcommand", "\\familydefault", "\\sfdefault"))
	.line(command("author", "thefire7\\#1893"))
	.line(command("date", "\\today"))
	.line(command("title", "League of Legends Setups"));
	
	latexDocument.beginDocument()
	.line(command("maketitle"))
	.line(command("newpage"))
	.line(command("tableofcontents"))
	.line(command("newpage"));
	
	final Map<Champion, Set<Setup>> championToSetupsMap = new HashMap<>();
	for (final Setup setup : setups.setups()) {
	    if (!championToSetupsMap.containsKey(setup.me())) {
		championToSetupsMap.put(setup.me(), new HashSet<>());
	    }
	    championToSetupsMap.get(setup.me()).add(setup);
	}

	final List<Champion> champions = new ArrayList<>(List.of(Champion.values()));
	champions.sort((c1, c2) -> c1.toString().compareTo(c2.toString()));
	for (final Champion champion : champions) {
	    if (!championToSetupsMap.containsKey(champion)) {
		continue;
	    }
	    latexDocument.line(command("section", champion.toString()));
	    if (setups.championSpecificNotes().containsKey(champion)) {
		latexDocument.plain(setups.championSpecificNotes().get(champion));
	    }
	    final List<Setup> setupsForThisChampion = new ArrayList<>(championToSetupsMap.get(champion));
	    setupsForThisChampion.sort((s1, s2) -> s1.enemy().toString().compareTo(s2.enemy().toString()));
	    for (final Setup setup : setupsForThisChampion) {
		latexDocument.format("\\subsection{vs. %}", setup.enemy())
		.beginEnvironment("center")
		.line(command("includegraphics", Optional.of("width=2cm"), "champions/" + champion.toString() + ".png"))
		.line(command("includegraphics", Optional.of("width=2cm"), "vs.png"))
		.line(command("includegraphics", Optional.of("width=2cm"), "champions/" + setup.enemy().toString() + ".png"))
		.emptyLine()
		.plain(setup.notes())
		.emptyLine();
		
		if(setup.first() != null && setup.second() != null) {
		    latexDocument.plain("Summoner Spells")
		    .line(command("includegraphics", Optional.of("width=2cm"), "summoner-spells/" + setup.first().toString() + ".png"))
		    .line(command("includegraphics", Optional.of("width=2cm"), "summoner-spells/" + setup.second().toString() + ".png"));
		}
		
		latexDocument.emptyLine();
		if(setup.startSpell() != null) {
		    latexDocument.format("\\textit{Start Spell}: %", setup.startSpell().toString());
		}
		
		latexDocument.emptyLine()
		.format("\\textit{Spell Max Order}: % > % > % > %", setup.spellMaxOrder().get(0), setup.spellMaxOrder().get(1), setup.spellMaxOrder().get(2), setup.spellMaxOrder().get(3))
		.endEnvironment("center")
		.line(command("subsubsection", "Runes"))
		.line(command("subsubsection", "Item Build"));
	    }
	    latexDocument.endDocument();
	    new GeneratePdf(latexDocument);
	}
    }
}
