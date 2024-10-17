package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import supportGUI.Circle;

public class DefaultTeam {

  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) { 
	  //ArrayList<Point> r = new ArrayList<Point>();
	  //Circle c = minidesk(points, r);
	  //return c;         //Décommenter pour l'algo Welzl
	  return algoNaif(points); 
  }
  
  @SuppressWarnings("unchecked")
  private Circle algoNaif(ArrayList<Point> inputPoints) {									//correction TME1
		ArrayList<Point> points = (ArrayList<Point>) inputPoints.clone();
		if (points.size() < 1)
			return null;
		double cX, cY, cRadiusSquared;
		for (Point p : points) {
			for (Point q : points) {
				cX = .5 * (p.x + q.x);
				cY = .5 * (p.y + q.y);
				cRadiusSquared = 0.25 * ((p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y));
				boolean allHit = true;
				for (Point s : points)
					if ((s.x - cX) * (s.x - cX) + (s.y - cY) * (s.y - cY) > cRadiusSquared) {
						allHit = false;
						break;
					}
				if (allHit)
					return new Circle(new Point((int) cX, (int) cY), (int) Math.sqrt(cRadiusSquared));
			}
		}
		double resX = 0;
		double resY = 0;
		double resRadiusSquared = Double.MAX_VALUE;
		for (int i = 0; i < points.size(); i++) {
			for (int j = i + 1; j < points.size(); j++) {
				for (int k = j + 1; k < points.size(); k++) {
					Point p = points.get(i);
					Point q = points.get(j);
					Point r = points.get(k);
					// si les trois sont colineaires, on passe
					if ((q.x - p.x) * (r.y - p.y) - (q.y - p.y) * (r.x - p.x) == 0)
						continue;
					// si p et q sont sur la meme ligne, ou p et r sont sur la meme ligne, on les
					// echange
					if ((p.y == q.y) || (p.y == r.y)) {
						if (p.y == q.y) {
							p = points.get(k); // ici on est certain que p n'est sur la meme ligne de ni q ni r
							r = points.get(i); // parce que les trois points sont non-colineaires
						} else {
							p = points.get(j); // ici on est certain que p n'est sur la meme ligne de ni q ni r
							q = points.get(i); // parce que les trois points sont non-colineaires
						}
					}
					// on cherche les coordonnees du cercle circonscrit du triangle pqr
					// soit m=(p+q)/2 et n=(p+r)/2
					double mX = .5 * (p.x + q.x);
					double mY = .5 * (p.y + q.y);
					double nX = .5 * (p.x + r.x);
					double nY = .5 * (p.y + r.y);
					// soit y=alpha1*x+beta1 l'equation de la droite passant par m et
					// perpendiculaire a la droite (pq)
					// soit y=alpha2*x+beta2 l'equation de la droite passant par n et
					// perpendiculaire a la droite (pr)
					double alpha1 = (q.x - p.x) / (double) (p.y - q.y);
					double beta1 = mY - alpha1 * mX;
					double alpha2 = (r.x - p.x) / (double) (p.y - r.y);
					double beta2 = nY - alpha2 * nX;
					// le centre c du cercle est alors le point d'intersection des deux droites
					// ci-dessus
					cX = (beta2 - beta1) / (double) (alpha1 - alpha2);
					cY = alpha1 * cX + beta1;
					cRadiusSquared = (p.x - cX) * (p.x - cX) + (p.y - cY) * (p.y - cY);
					if (cRadiusSquared >= resRadiusSquared)
						continue;
					boolean allHit = true;
					for (Point s : points)
						if ((s.x - cX) * (s.x - cX) + (s.y - cY) * (s.y - cY) > cRadiusSquared) {
							allHit = false;
							break;
						}
					if (allHit) {
						System.out.println("Found r=" + Math.sqrt(cRadiusSquared));
						resX = cX;
						resY = cY;
						resRadiusSquared = cRadiusSquared;
					}
				}
			}
		}
		return new Circle(new Point((int) resX, (int) resY), (int) Math.sqrt(resRadiusSquared));
	}

  private double crossProduct(Point p, Point q, Point s, Point t) {
		return ((q.x - p.x) * (t.y - s.y) - (q.y - p.y) * (t.x - s.x));
	}

  public Circle trivial(ArrayList<Point> pointsR) {
		Circle circle = new Circle(new Point(0, 0), 0);
		//circle de deux points
		if (pointsR.size() == 2) {
			Point p1 = pointsR.get(0);
			Point p2 = pointsR.get(1);
			double center_x = (p1.getX() + p2.getX()) / 2;
			double center_y = (p1.getY() + p2.getY()) / 2;
			Point center = new Point((int) center_x, (int) center_y);
			circle = new Circle(center, (int) center.distance(p1));
		}
		else if (pointsR.size() == 3) {
			Point p1 = pointsR.get(0);
			Point p2 = pointsR.get(1);
			Point p3 = pointsR.get(2);
			if (crossProduct(p1, p2, p1, p3) != 0) {
				double a = p1.getX() - p2.getX();
				double b = p1.getY() - p2.getY();
				double c = p1.getX() - p3.getX();
				double d = p1.getY() - p3.getY();
				double e = ((p1.getX() * p1.getX() - p2.getX() * p2.getX())
						- (p2.getY() * p2.getY() - p1.getY() * p1.getY())) / 2;
				double f = ((p1.getX() * p1.getX() - p3.getX() * p3.getX())
						- (p3.getY() * p3.getY() - p1.getY() * p1.getY())) / 2;

				double x = (e * d - b * f) / (a * d - b * c);
				double y = (a * f - e * c) / (a * d - b * c);
				Point center = new Point((int) x, (int) y);
				circle = new Circle(center, (int) center.distance(p1));
			}
		}
		
		return circle;
	}

  public Circle minidesk(ArrayList<Point> points, ArrayList<Point> pointsR) {
		ArrayList<Point> cp = new ArrayList<Point>(points);
		ArrayList<Point> cr = new ArrayList<Point>(pointsR);
		Circle c = new Circle(new Point(0, 0), 0);
		
		if (cp.isEmpty() || cr.size() == 3) {
			c = trivial(cr);
		} else {
			int index = (new Random()).nextInt(cp.size());
			Point p = cp.remove(index);
			c = minidesk(cp, cr);
			if (!(c.getCenter().distance(p) <= c.getRadius())) {
				cr.add(p);
				c = minidesk(cp, cr);
			}
		}
		return c;
	}

}
