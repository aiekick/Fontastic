import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import fontastic.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class LabyrinthFont extends PApplet {

 /**
 * Fontastic
 * A font file writer for Processing.
 * http://code.andreaskoller.com/libraries/fontastic
 *
 * Example: LabyrinthFont
 *
 * How to create characters of random lines in a 3x3 grid that make up a labyrinth.
 * - Press SPACE to randomize labyrinth and create a new version of the font
 *
 * 
 * @author      Andreas Koller http://andreaskoller.com
 */
 



Fontastic f;

int version = 0;


public void setup() {

  size(600, 400);
  fill(0);

  createFont();
}


public void draw() {

  background(255);

  PFont myFont = createFont(f.getTTFfilename(), 80);
  PFont defaultFont = createFont("Helvetica", 8);

  textFont(myFont);
  textAlign(CENTER, BASELINE);
  text(Fontastic.alphabet, 0, Fontastic.alphabet.length/2, width/2, height/5);
  text(Fontastic.alphabet, Fontastic.alphabet.length/2, Fontastic.alphabet.length, width/2, height/5*2);

  text(Fontastic.alphabetLc, 0, Fontastic.alphabet.length/2, width/2, height/5*3);
  text(Fontastic.alphabetLc, Fontastic.alphabet.length/2, Fontastic.alphabet.length, width/2, height/5*4);

  noLoop();
}

public void createFont() {

  version++;

  f = new Fontastic(this, "Labyrinth" + nf(version,4));
  f.setAdvanceWidth(400);

  int i = 0;
  char[] allChars = concat(Fontastic.alphabet, Fontastic.alphabetLc);

  for (char c : allChars) {

    f.addGlyph(c);

    float thickness = 10;
    float charWidth = 400;
    float quadWidth = (charWidth - thickness) / 3f;

    for (float x=0; x<charWidth - thickness; x+=quadWidth) {
      for (float y=0; y<charWidth - thickness; y+=quadWidth) {

        if (random(1)<0.5f) {

          // vertical line
          PVector[] points = new PVector[4];
          points[0] = new PVector(x, y);
          points[1] = new PVector(x, y+quadWidth+thickness);
          points[2] = new PVector(x+thickness, y+quadWidth+thickness);
          points[3] = new PVector(x+thickness, y);

          f.getGlyph(c).addContour(points);
        }

        if (random(1)<0.5f) {

          // horizontal line
          PVector[] points = new PVector[4];
          points[0] = new PVector(x, y);
          points[1] = new PVector(x, y+thickness);
          points[2] = new PVector(x+quadWidth+thickness, y+thickness);
          points[3] = new PVector(x+quadWidth+thickness, y);

          f.getGlyph(c).addContour(points);
        }
      }
    }

    i++;
  }

  f.buildFont();

  f.cleanup();
  
}

public void keyPressed() {

  if (key == ' ') {
    createFont();
    loop();
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "LabyrinthFont" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
