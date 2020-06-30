public class Planet {
	/** its current x position */
	public double xxPos;

	/** its current y position */
	public double yyPos;

	/** its current velocity in the x direction */
	public double xxVel;

	/** its current velocity in the y direction */
	public double yyVel;

	/** its mass */
	public double mass;

	/** The name of the file that corresponds to the image that depicts the planet */
	public String imgFileName;

	private static final double G = 6.67e-11;

	/** Planet constructor 1 */
	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/** Planet constructor 2: 
	it takes in a Planet object and initialize an identical Planet object (i.e. a copy) */
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	/** calculates the distance between two Planets 
	r^2 = dx^2 + dy^2
	*/
	public double calcDistance(Planet p) {
		double dx = p.xxPos - this.xxPos;
		double dy = p.yyPos - this.yyPos;
		double res = dx * dx + dy * dy;
		return Math.sqrt(res);
	}

	/** calcForceExertedBy:
	it takes in a planet, and returns a double describing the force exerted on this planet by the given planet
	F = G * m1 * m2 / r^2
	*/
	
	public double calcForceExertedBy(Planet p) {
		double distance = calcDistance(p); //this.calcDistance(p); also works
		double force = ((this.mass * p.mass) / (distance * distance)) * G;
		return force;
	}

	/**
	calcForceExertedByX and calcForceExertedByY:
	these two methods describe the force exerted in the X and Y directions, respectively.
	they also take in a planet parameter.
	Fx = F * dx / r
	Fy = F * dy / r
	Note: Do not use Math.abs to fix sign issues with these methods. This will cause issues later when drawing planets.
	*/

	public double calcForceExertedByX(Planet p) {
		double dx = p.xxPos - this.xxPos;
		double distance = calcDistance(p);
		double force = calcForceExertedBy(p);
		if(force <0) {
			force = 0 - force;
		}
		double Fx = force * dx / distance;
		return Fx;
	}

	public double calcForceExertedByY(Planet p) {
		double dy = p.yyPos - this.yyPos;
		double distance = calcDistance(p);
		double force = calcForceExertedBy(p);
		if(force <0) {
			force = 0 - force;
		}
		double Fy = force * dy / distance;
		return Fy;
	}

	/**
	calcNetForceExertedByX and calcNetForceExertedByY:
	each take in an array of Planets and calculate the net X and net Y force exerted by all planets in that array upon the current Planet.
	Fnetx/y = F1x/y + F2x/y + ... + Fnx/y;
	*/

	public double calcNetForceExertedByX(Planet[] planets) {
		double xNetForce = 0;
		for(Planet p : planets) {
			if(!this.equals(p)) {
				xNetForce += calcForceExertedByX(p);
			}
		}
		return xNetForce;
	}

	public double calcNetForceExertedByY(Planet[] planets) {
		double yNetForce = 0;
		for(Planet p : planets) {
			if(!this.equals(p)) {
				yNetForce += this.calcForceExertedByY(p);
			}
		}
		return yNetForce;
	}

	/**
	update:
	You must compute the movement of the Planet using the following steps:

	step1:
	Calculate the acceleration using the provided x- and y-forces.
	Acceleration: Newton’s second law of motion says that the accelerations in the x- and y-directions are given by:
	ax=Fx / m
	ay=Fy / m

	step2:
	Calculate the new velocity by using the acceleration and current velocity. Recall that acceleration describes the change in velocity per unit time, so the new velocity is (vx+dt⋅ax,vy+dt⋅ay).

	step3:
	Calculate the new position by using the velocity computed in step 2 and the current position. The new position is (px+dt⋅vx,py+dt⋅vy).
	*/

	public void update(double time, double xNetF, double yNetF) {
		// calculate the net acceleration
		double axNet = xNetF / this.mass;
		double ayNet = yNetF / this.mass;
		// // calculate new velocity of different directions
		// double VxNew = this.xxVel + time * axNet;
		// double VyNew = this.yyVel + time * ayNet;
		// // calculate new position
		// double PxNew = this.xxPos + time * VxNew;
		// double PyNew = this.yyPos + time * VyNew;
		// // update variables
		// xxVel = VxNew;
		// yyVel = VyNew;
		// xxPos = PxNew;
		// yyPos = PyNew;

		// update variables while calculating
		this.xxVel += time * axNet;
		this.yyVel += time * ayNet;
		this.xxPos += time * this.xxVel;
		this.yyPos += time * this.yyVel;
	}

	/* Drawing one planet: draw method 
	MyNote: should add "./images/" before imgFileName because the given $more planets.txt only lists image name such as "earth.gif" without clearifying its correct position. 
	*/
	public void draw() {
		StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName);
	}
}




/** 
Note: We have given parameter names which are different than the corresponding instance variable name. If you insist on making the parameter names the same as the instance variable names for aesthetic reasons, make sure to use the "this" keyword appropriately (mentioned only briefly in lecture).

Note: Your Planet class should NOT have a main method, because we’ll never run the Planet class directly (i.e. we will never do java Planet). Also, all methods should be non-static.

MyNote: Be careful when naming different methods and referencing them.
*/