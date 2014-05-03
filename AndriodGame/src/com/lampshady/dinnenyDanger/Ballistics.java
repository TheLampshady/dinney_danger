package com.lampshady.dinnenyDanger;

import java.util.ArrayList;

import com.lampshady.framework.Graphics;

public class Ballistics {
	private int reloadSpeed, reloadTime;
	
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public Ballistics(){
		reloadSpeed = 15;
		reloadTime = 0;
	}
	
	public void shoot(int centerX, int centerY){
		if(reloadTime<=0){
			Projectile p = new Projectile(centerX + 50, centerY - 25);
			projectiles.add(p);
			reloadTime=reloadSpeed;
		}
	}
	
	public void update(){
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			if (p.isVisible() == true) p.update();
			else projectiles.remove(i);
		}
		
		if(reloadTime>0)reloadTime--;
	}
	
	public void draw(Graphics g){
		for(Projectile p : projectiles)
			p.draw(g);
	}
	
	
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	public void nullify() {
		for (Projectile p : projectiles){
			p.nullify();
			p=null;
		}
		
	}
}
