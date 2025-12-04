public abstract class GameObject {

    private double x;
    private double y;
    private Collidable collider;

    public GameObject(double x, double y, Collidable collider) {
        if (collider == null) {
            throw new IllegalArgumentException("Collider ne moze biti null");
        }
        this.x = x;
        this.y = y;
        this.collider = collider;
    }

    public int getEffectiveDamage() {
		return 0;
	}

	public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public Collidable getCollider() { return collider; }
    public void setCollider(Collidable collider) {
        if (collider == null) {
            throw new IllegalArgumentException("Collider ne moze biti null");
        }
        this.collider = collider;
    }

    public boolean intersects(GameObject other) {
        if (other == null) return false;
        return this.collider.intersects(other.collider);
    }

    public abstract String getDisplayName();

    @Override
    public String toString() {
        return String.format("GameObject @ (%.1f, %.1f) with %s", x, y, collider);
    }
}

class Player extends GameObject {

    private String name;
    private int health;

    public Player(double x, double y, String name, int health, Collidable collider) {
        super(x, y, collider);
        setName(name);
        setHealth(health);
    }

    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Ime igraca ne smije biti prazno");
            this.name = "Nepoznato";
            return;
        }
        this.name = formatName(name);
    }

    private String formatName(String ime) {
        ime = ime.trim().replaceAll("\\s+", " ");
        String[] words = ime.split(" ");

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            formatted.append(Character.toUpperCase(w.charAt(0)));
            if (w.length() > 1) {
                formatted.append(w.substring(1).toLowerCase());
            }
            if (i < words.length - 1) formatted.append(" ");
        }
        return formatted.toString();
    }

    public int getHealth() { return health; }

    public void setHealth(int health) {
        if (health < 0) {
            System.out.println("Health ne moze biti manji od 0");
            this.health = 0;
        } else if (health > 100) {
            System.out.println("Health ne moze biti veci od 100");
            this.health = 100;
        } else {
            this.health = health;
        }
    }

    @Override
    public String getDisplayName() {
        return "Player: " + name;
    }

    @Override
    public String toString() {
        return "Player[" + name + "] @ (" + getX() + "," + getY() + ") HP=" + health;
    }
}

class Enemy extends GameObject implements AttackerI {

    private String type;
    private int damage;
    private int health;

    public Enemy(double x, double y, Collidable collider,
                 String type, int damage, int health) {
        super(x, y, collider);
        setType(type);
        setDamage(damage);
        setHealth(health);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Tip neprijatelja ne moze biti null");
        }
        String trimmed = type.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Tip neprijatelja ne moze biti prazan");
        }
        this.type = trimmed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        if (damage < 0 || damage > 100) {
            throw new IllegalArgumentException("Damage mora biti izmedju 0 i 100");
        }
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException("Health mora biti izmedju 0 i 100");
        }
        this.health = health;
    }

    @Override
    public int getEffectiveDamage() {
        return damage;
    }

    @Override
    public String getDisplayName() {
        return type;
    }

    @Override
    public String toString() {
        String colliderInfo = "";
        if (getCollider() instanceof RectangleCollider rect) {
            colliderInfo = String.format("%.0fx%.0f", rect.getWidth(), rect.getHeight());
        } else if (getCollider() instanceof CircleCollider circle) {
            colliderInfo = String.format("R=%.0f", circle.getPoluprecnik());
        }
        return String.format(
                "Enemy[%s] @ (%.0f, %.0f) %s DMG=%d HP=%d",
                type, getX(), getY(), colliderInfo, damage, health
        );
    }
}
