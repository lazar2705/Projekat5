import java.util.ArrayList;

public class Game {

    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<String> podaci;

    public Game(Player player) {
        this.player = player;
        this.enemies = new ArrayList<>();
        this.podaci = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<String> getPodaci() {
        return podaci;
    }

    public void addEnemy(Enemy e) {
        if (e == null) return;
        enemies.add(e);
        podaci.add("Dodat neprijatelj: " + e.getDisplayName());
    }

    public boolean checkCollision(Player p, Enemy e) {
        return p.intersects(e);
    }

    public void decreaseHealth(Player p, Enemy e) {
        int dmg = e.getEffectiveDamage();
        int newHealth = Math.max(0, p.getHealth() - dmg);

        podaci.add("Igrač je primio " + dmg + " štete od neprijatelja: " + e.getDisplayName());
        p.setHealth(newHealth);
    }
    
    
    public ArrayList<Enemy> findByType(String trazi) {
        ArrayList<Enemy> rezultat = new ArrayList<>();
        if (trazi == null) return rezultat;

        String q = trazi.toLowerCase();

        for (Enemy e : enemies) {
            if (e.getType().toLowerCase().contains(q)) {
                rezultat.add(e);
            }
        }
        return rezultat;
    }

    public ArrayList<Enemy> collidingWithPlayer() {
        ArrayList<Enemy> lista = new ArrayList<>();

        for (Enemy e : enemies) {
            if (checkCollision(player, e)) {
                lista.add(e);
            }
        }
        return lista;
    }

    public void resolveCollisions() {
        for (Enemy e : enemies) {
            if (checkCollision(player, e)) {
                podaci.add("Kolizija: Igrač <-> " + e.getDisplayName());
                decreaseHealth(player, e);
            }
        }
    }

// iz csv fajla
    
    public static ArrayList<Enemy> loadEnemiesFromCSV(String filePath) {

        ArrayList<Enemy> lista = new ArrayList<>();

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {

            String line = br.readLine(); 

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");

                if (parts.length < 10) {
                    throw new IllegalArgumentException("Neispravan CSV format!");
                }

                String type = parts[0].trim();
                String classType = parts[1].trim();
                int damage = Integer.parseInt(parts[2].trim());
                int health = Integer.parseInt(parts[3].trim());

                double x = Double.parseDouble(parts[4].trim());
                double y = Double.parseDouble(parts[5].trim());

                String shape = parts[6].trim();

                Collidable collider;

                if (shape.equalsIgnoreCase("rectangle")) {
                    double w = Double.parseDouble(parts[7].trim());
                    double h = Double.parseDouble(parts[8].trim());
                    collider = new RectangleCollider(x, y, w, h);

                } else if (shape.equalsIgnoreCase("circle")) {
                    double r = Double.parseDouble(parts[9].trim());
                    collider = new CircleCollider((float) r, (float) x, (float) y);

                } else {
                    throw new IllegalArgumentException("Nepoznat oblik: " + shape);
                }

                Enemy enemy;

                if (classType.equalsIgnoreCase("melee")) {
                    enemy = new MeleeEnemy(x, y, collider, type, damage, health);

                } else if (classType.equalsIgnoreCase("boss")) {
                    enemy = new BossEnemy(x, y, collider, type, damage, health);

                } else {
                    throw new IllegalArgumentException("Nepoznata klasa neprijatelja: " + classType);
                }

                lista.add(enemy);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Greška pri čitanju CSV fajla: " + e.getMessage());
        }

        return lista;
    }
}
