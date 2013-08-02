using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Input.Touch;
using Microsoft.Xna.Framework.Media;

namespace LightRunner_wp7
{
    public class Magnet : Sprite2{

	float pull;
	Vector2 velocity;

    public Magnet(Vector2 Position, int newW, int newH, String newAsset, float pullStrength) :
        base(Position, newW, newH, newAsset)
    {
        pull = pullStrength;
        velocity = new Vector2(-.5f, 0);
    }
	
	public void update(){
        position.X += velocity.X;
		position.Y += velocity.Y;
	}
	
	public Vector2 getPull(Vector2 objectPosition){
		//float distance = objectPosition.dst(getCenter());
		return new Vector2(((position.X - objectPosition.X ) * pull), ((position.Y - objectPosition.Y) * pull));
	}
}

}
