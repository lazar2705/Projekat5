public class CircleCollider implements Collidable {

    private float poluprecnik;
    private float xCentra;
    private float yCentra;

    public CircleCollider(float poluprecnik, float xCentra, float yCentra) {
        if (poluprecnik <= 0) {
            throw new IllegalArgumentException("Poluprečnik mora biti veći od nule");
        }

        this.poluprecnik = poluprecnik;
        this.xCentra = xCentra;
        this.yCentra = yCentra;
    }

    public float getPoluprecnik() {return poluprecnik;}
    public void setPoluprecnik(float poluprecnik) {
        if (poluprecnik <= 0) {
            throw new IllegalArgumentException("Poluprečnik mora biti veći od nule");
        }
        this.poluprecnik = poluprecnik;
    }

    public float getxCentra() {return xCentra;}
    public void setxCentra(float xCentra) {
        this.xCentra = xCentra;
    }

    public float getyCentra() {return yCentra;}public void setyCentra(float yCentra) {
        this.yCentra = yCentra;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public boolean intersects(Collidable other) {

        if (other instanceof CircleCollider krug) {

            double dx = this.xCentra - krug.xCentra;
            double dy = this.yCentra - krug.yCentra;

            double razdaljina = Math.sqrt(dx * dx + dy * dy);

            return razdaljina <= this.poluprecnik + krug.poluprecnik;
        }

        else if (other instanceof RectangleCollider pravougaonik) {

            double closestX = clamp(this.xCentra,
                    pravougaonik.getX(),
                    pravougaonik.getX() + pravougaonik.getWidth());

            double closestY = clamp(this.yCentra,
                    pravougaonik.getY(),
                    pravougaonik.getY() + pravougaonik.getHeight());

            double dx = this.xCentra - closestX;
            double dy = this.yCentra - closestY;

            double distanceSquared = dx * dx + dy * dy;

            return distanceSquared <= this.poluprecnik * this.poluprecnik;
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("Circle[%.1f, %.1f R=%.1f]", xCentra, yCentra, poluprecnik);
    }
}
