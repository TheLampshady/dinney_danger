package com.lampshady.dinnenyDanger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import com.lampshady.framework.Graphics;

public class GameMap {

	private ArrayList<Tile> tilearray;
	ArrayList<ArrayList<String>> mapSections = new ArrayList<ArrayList<String>>();
	
	private int length=400, height=12,sectionLen=20;
	private int masterBuilder;
	
	public GameMap(){
		tilearray = new ArrayList<Tile>();
		
		mapSections = new ArrayList<ArrayList<String>>();
		
		mapSections.add(new ArrayList<String>(Arrays.asList(
				"22222222222222222222",
				"", "", "", "", "", "", "", "", "", "",
				"88888888888888888888"
				)));
		
		mapSections.add(new ArrayList<String>(Arrays.asList(
				"22222222222222222222",
				"", "", "", "", "", "", "", "", "",
				"   7889      7889", 
				"88855558888885555888"
				)));
		
		mapSections.add(new ArrayList<String>(Arrays.asList(
				"22222222222222222222",
				"", "", "", "", "", "", "", "", "", "",
				"88888    88888888888"
				)));
		
		mapSections.add(new ArrayList<String>(Arrays.asList(
				"22222222222222222222",
				"", "", "", "", "", "", "", "", "", "",
				"888    888888    888"
				)));
		
		mapSections.add(new ArrayList<String>(Arrays.asList(
				"22222222222222222222",
				"", "", "", "", "", "", "", "", 
				"       78889", 
				"   7888555558889",
				"88855555555555558888"
				)));
		
		mapSections.add(new ArrayList<String>(Arrays.asList(
				"22222222222222222222",
				"", "", "", "", "", "", "", "", 
				"      79    79", 
				"   78855    55889",
				"88855555    55555888"
				)));
		
		mapSections.add(new ArrayList<String>(Arrays.asList(
				"22222222222222222222",
				"", "", "", "", "", "", "", "", "", "",
				"    88   88  88   88"
				)));
		
		mapSections.add(new ArrayList<String>(Arrays.asList(
				"55555555555555555555",
				"55555555555555555555", 
				"22222222222222222222", 
				"", "", "", 
				"     7888888889", 
				"  788555555555589",
				"  455555555555555889", 
				"78555555555555555556", 
				"45555555555555555556",
				"55555555555555555555"
				)));
		
		mapSections.add(new ArrayList<String>(Arrays.asList(
				"22222222222222222222",
				"", "", "", "", 
				"              79", 
				"            7855", 
				"          785555",
				"        78555555", 
				"      7855555555", 
				"    785555555555",
				"88885555555555558888"
				)));
	}

	public void loadMap(String map) {
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;

		Scanner scanner = new Scanner(map);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			//End of File
			if (line == null) {
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		length = width;
		scanner.close();

		inputTiles(lines, 0);
	}
	
	private void inputTiles(ArrayList<String> lines, int startPoint){
		for (int h = 0; h < height; h++) {
			String line = (String) lines.get(h);
			for (int l = 0; l < sectionLen; l++) {

				if (l < line.length()) {
					char ch = line.charAt(l);
					Tile t = new Tile(l+startPoint, h, Character.getNumericValue(ch));
					tilearray.add(t);
				}

			}
		}
	}
	
	public void loadMap() {
		Random rand = new Random();
		int section;
		
		inputTiles(mapSections.get(0), masterBuilder);
		masterBuilder=sectionLen;
		while(masterBuilder<length){
			section = rand.nextInt(mapSections.size());
			
			inputTiles(mapSections.get(section), masterBuilder);
			masterBuilder+=sectionLen;
		}
		
			
	}

	public void update() {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}
	}
	
	public void paint(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			if (t.getType() != 0) {
				g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY());
			}
		}
	}
}

