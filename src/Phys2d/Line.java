package Phys2d;

/**
 * Created by scottdavey on 06/03/2016.
 */
public class Line extends Shape {

    private double length;
    private int thickness;

    public Line(GameObject obj, double length, int thickness) {
        super(obj);
        this.length = length;
        this.thickness = thickness;
    }

    @Override
    public boolean overlaps(Shape shape) {
        if (shape instanceof Circle) {
            return overlapWith((Circle)shape);
        }
        return false;
    }

    private boolean overlapWith(Circle circle) {
        Vector2D start = startOfLine();
        Vector2D tangent = Vector2D.minus(endOfLine(), start);
        double length = tangent.mag();
        tangent.normalise();
        Vector2D normal = tangent.rotate90degreesAnticlockwise();

        Vector2D lineToC = Vector2D.minus(circle.object.getPosition(), start);
        double distNormal = lineToC.scalarProduct(normal);
        double distLine = lineToC.scalarProduct(tangent);
        return distNormal <= circle.getRadius() && distLine >= 0 && distLine <= length;
    }


    public Vector2D startOfLine() {
        double startX = object.getPosition().x - length/2;
        double startY = object.getPosition().y;
        Vector2D start = new Vector2D(startX, startY);
        Vector2D rotationVec = object.getRotation();
        double rotation = Math.atan2(rotationVec.y, rotationVec.x);
        if (rotation == 0) {
            return start;
        }
        Vector2D pos = new Vector2D(object.getPosition());
        pos.mult(-1);
        start.add(pos);
        start.rotate(rotation);
        pos.mult(-1);
        start.add(pos);
        return start;
    }

    public Vector2D endOfLine() {
        double endX = object.getPosition().x + length/2;
        double endY = object.getPosition().y;
        Vector2D end = new Vector2D(endX, endY);
        Vector2D rotationVec = object.getRotation();
        double rotation = Math.atan2(rotationVec.y, rotationVec.x);
        if (rotation == 0) {
            return end;
        }
        Vector2D pos = new Vector2D(object.getPosition());
        pos.mult(-1);
        end.add(pos);
        end.rotate(rotation);
        pos.mult(-1);
        end.add(pos);
        return end;
    }
}
