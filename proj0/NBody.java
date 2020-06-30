public class NBody {
	/* this class will have NO constructor */

	/* readRadius:
	Given a file name, it should return a double corresponding to the radius of the universe in that file.

	MyNote: it should be a static method invoked by classname.
	*/

	public static double readRadius(String fileName) {
		In in = new In(fileName);
		int num = in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	/* readPlanets:
	Given a file name, it should return an array of Planets corresponding to the planets in the file.

	MyNote: an array of Planets instances(Planet p = new Planet(arguments));
			notice the return type: an array, of Planets
	*/

	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);
		int num = in.readInt();
		double radius = in.readDouble();

		// an array of Planet instances
		Planet[] allPlanets = new Planet[num];

		// read details
		for(int i = 0; i < num; i++) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			allPlanets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
		}
		return allPlanets;
	}

	/* Main method:
	->Collecting input<-
	step1:
	Store the 0th and 1st command line arguments as doubles named T and dt;
	step2:
	Store the 2nd command line argument as a String named filename;
	step3:
	Read in the planets and the universe radius from the file described by filename using your methods from earlier in this assignment.
	
	->Drawing background<-
	step1:
	set the scale so that it matches the radius of the universe;
	step2:
	draw the image starfield.jpg as the background;
	*/

	public static String imageToDraw = "./images/starfield.jpg";

	public static void main(String[] args) {
		// Collecting all needed input
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		
		double radius = readRadius(filename); //get input using readRadius(a given file)
		Planet[] allPlanets = readPlanets(filename); //get input using readPlanets(a given file)

		// Drawing the background
		StdDraw.setScale(radius, -radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, imageToDraw);
		StdDraw.show();

		// Drawing One Planet (method added in Planet.java)
		// Drawing all of the planets: use the draw method just defined
		for(Planet p : allPlanets) {
			p.draw();
		}


		// Enabling Double Buffering
		StdDraw.enableDoubleBuffering();

		// Creating an animation
		for(double time = 0; time <= T; time += dt) {
			// create arrays to store the NET x and y Forces
			double[] xForces = new double[allPlanets.length];
			double[] yForces = new double[allPlanets.length];

			// Calculate the NET x and y Forces for each planet, 
			// storing these in the xForces and yForces arrays respectively.
			for(int i = 0; i < allPlanets.length - 1; i++) {
				xForces[i] = allPlanets[i].calcNetForceExertedByX(allPlanets);
				yForces[i] = allPlanets[i].calcNetForceExertedByY(allPlanets);
			}

			// Call update on each of the planets. 
			// This will update each planet’s position, velocity, and acceleration.
			// Important: don’t call planets[0].update() until after the entire xForces and yForces arrays are done!
			// reference: public void update(double time, double xNetF, double yNetF){}
			for(int i = 0; i < allPlanets.length - 1; i++) {
				allPlanets[i].update(time, xForces[i], yForces[i]);
			}

			// Draw the background image.
			StdDraw.picture(0, 0, imageToDraw);

			// Draw all of the planets.
			for(Planet p : allPlanets) {
				p.draw();
			}

			// Show the offscreen buffer (see the show method of StdDraw).
			StdDraw.show();

			// Pause the animation for 10 milliseconds (see the pause method of StdDraw). 
			// You may need to tweak this on your computer.
			// MyNote: 10 is super fast, 100 works better to see the motion.
			StdDraw.pause(10);
		}

		StdOut.printf("%d\n", allPlanets.length);
		StdOut.printf("%.2e\n", radius);

		for (int i = 0; i < allPlanets.length; i++) {
    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
    		allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
    		allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);
    	}
	}
}



/* MyNote:
Double.parseDouble(text);

StdDraw methods:
	
StdDraw.line(x0, y0, x1, y1) draws a straight line segment connecting the point (x0, y0) with the point (x1, y1). 

StdDraw.point(x, y) draws a spot centered on the point (x, y).

The standard implementation displays the canvas in a window on your computer's screen, with black lines and points on a white background.

StdDraw.setXscale(x0, x1);
StdDraw.setYscale(y0, y1); 
sets the drawing coordinates to be within a bounding box whose lower-left corner is at (x0, y0) and whose upper-right corner is at (x1, y1).

*/