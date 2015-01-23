package org.logogenesis.wording.scripts;

import java.io.IOException;

import org.logogenesis.wording.WordFormRecognizer;
import org.logogenesis.wording.HolisticWordingSegmenter;
import org.logogenesis.wording.ReductionistWordingSegmenter;
import org.logogenesis.wording.WordingSegmenter;
import org.logogenesis.wording.io.WordingChart;
import org.logogenesis.wording.io.WordingChartFactory;
import org.logogenesis.wording.io.atag.AtagWordingChartFactory;
import org.logogenesis.wording.io.hal.HalWordingChartFactory;

/**
 * Segments a wording using a wording segmenter.
 * 
 * @author Daniel Couto-Vale
 */
public class UsageExample {

	public static void main(String[] args) throws IOException {
		String wording = "Unser Prozess ermöglicht es uns, Projekte schnell herzustellen. "
				+ "Wir stellen Projekte schnell her.";
		illustrateUsage(wording, new HalWordingChartFactory());
		illustrateUsage(wording, new AtagWordingChartFactory());
	}

	private static void illustrateUsage(String wording, WordingChartFactory factory) {
		// Segments the wording with a holistic theory
		WordFormRecognizer wordRecognizer = new WordFormRecognizer(factory);
		wordRecognizer.addWordType("Unser", 1);
		wordRecognizer.addWordType("Prozess", 2);
		wordRecognizer.addWordType("ermöglicht", 3);
		wordRecognizer.addWordType("ermöglicht", 4);
		wordRecognizer.addWordType("es", 5);
		wordRecognizer.addWordType("uns", 6);
		wordRecognizer.addWordType("Projekte", 7);
		wordRecognizer.addWordType("schnell", 8);
		wordRecognizer.addWordType("her", 9);
		wordRecognizer.addWordType("zu", 10);
		wordRecognizer.addWordType("stellen", 11);
		wordRecognizer.addWordType("stellen", 12);
		wordRecognizer.addWordType("stellen", 13);
		wordRecognizer.addWordType("stellen", 14);
		wordRecognizer.addWordType(" ", 15);
		wordRecognizer.addWordType(", ", 16);
		wordRecognizer.addWordType(".", 17);
		wordRecognizer.addWordType("Wir", 18);
		WordingSegmenter holisticSegmenter = new HolisticWordingSegmenter(wordRecognizer);
		WordingChart holisticChart = holisticSegmenter.segmentWording(wording);
		System.out.println("HOLISTIC CHART");
		System.out.println(holisticChart);

		// Segments the wording with a reductionist theory
		System.out.println("REDUCTIONIST CHART");
		WordingSegmenter reductionistSegmenter = new ReductionistWordingSegmenter(factory);
		WordingChart reductionistChart = reductionistSegmenter.segmentWording(wording);
		System.out.println(reductionistChart);
	}

}
