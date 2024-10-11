import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class Player {
    protected String name;
    protected int health;
    protected int damage;

    public Player(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public abstract void takeDamage(int damage);

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }
}

class Medic extends Player {
    private int healingAmount;

    public Medic(String name, int health, int damage, int healingAmount) {
        super(name, health, damage);
        this.healingAmount = healingAmount;
    }

    public void heal(List<Player> team) {
        for (Player player : team) {
            if (player.isAlive() && player.getHealth() < 100) {
                player.takeDamage(-healingAmount);
                System.out.println(name + " healed " + player.getName() + " for " + healingAmount + " health.");
                break; // Heal only one player per round
            }
        }
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " took " + damage + " damage. Remaining health: " + health);
    }
}

class Golem extends Player {
    public Golem(String name, int health, int damage) {
        super(name, health, damage);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage / 5; // Takes 1/5 of the damage dealt to others
        System.out.println(name + " absorbed damage. Remaining health: " + health);
    }
}

class Lucky extends Player {
    private Random random = new Random();

    public Lucky(String name, int health, int damage) {
        super(name, health, damage);
    }

    @Override
    public void takeDamage(int damage) {
        if (random.nextBoolean()) {
            System.out.println(name + " dodged the attack!");
        } else {
            health -= damage;
            System.out.println(name + " took " + damage + " damage. Remaining health: " + health);
        }
    }
}

class Witcher extends Player {
    public Witcher(String name, int health, int damage) {
        super(name, health, damage);
    }

    public void revive(Player player) {
        if (!player.isAlive()) {
            player.health = this.health; // Revive with current health
            this.health = 0; // Witcher dies
            System.out.println(name + " revived " + player.getName() + " and died in the process.");
        }
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " took " + damage + " damage. Remaining health: " + health);
    }
}

class Thor extends Player {
    public Thor(String name, int health, int damage) {
        super(name, health, damage);
    }

    public boolean stunBoss() {
        Random random = new Random();
        return random.nextBoolean();
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " took " + damage + " damage. Remaining health: " + health);
    }
}

public class Main {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Medic("Medic", 100, 0, 20));
        players.add(new Golem("Golem", 200, 5));
        players.add(new Lucky("Lucky", 100, 10));
        players.add(new Witcher("Witcher", 80, 0));
        players.add(new Thor("Thor", 90, 15));

        for (Player player : players) {
            player.takeDamage(20);
        }

        Medic medic = (Medic) players.get(0);
        medic.heal(players);

        Thor thor = (Thor) players.get(4);
        if (thor.stunBoss()) {
            System.out.println("Boss is stunned!");
        } else {
            System.out.println("Boss is not stunned.");
        }


        Witcher witcher = (Witcher) players.get(3);
        Player toRevive = players.get(1);
        witcher.revive(toRevive);
    }
}
