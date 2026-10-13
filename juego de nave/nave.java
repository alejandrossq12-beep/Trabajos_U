import greenfoot.*;

public class nave extends Actor
{
    private final EstrategiaDisparo disparoSimple =
        new DisparoSimple();

    private final EstrategiaDisparo disparoTriple =
        new DisparoTriple();

    private EstrategiaDisparo estrategia = disparoSimple;

    private PoolDeBalas pool;

    private int recarga = 0;
    private int invulnerabilidad = 0;

    private GreenfootImage imagenNormal;
    private GreenfootImage imagenTransparente;

    public nave(PoolDeBalas pool)
    {
        this.pool = pool;

        crearImagen();

        setImage(imagenNormal);
    }

    public void act()
    {
        Espacio mundo = (Espacio) getWorld();

        if (mundo == null || mundo.estaTerminado())
        {
            return;
        }

        actualizarInvulnerabilidad();
        mover();
        cambiarArma();
        disparar();
        revisarColision();
    }

    private void crearImagen()
    {
        imagenNormal = new GreenfootImage(40, 28);

        // Cuerpo.
        imagenNormal.setColor(new Color(40, 220, 130));

        imagenNormal.fillPolygon(
            new int[] {3, 3, 39},
            new int[] {1, 27, 14},
            3
        );

        // Cabina.
        imagenNormal.setColor(new Color(100, 200, 255));
        imagenNormal.fillOval(14, 9, 13, 10);

        // Motor.
        imagenNormal.setColor(Color.ORANGE);
        imagenNormal.fillRect(0, 10, 7, 8);

        imagenTransparente = new GreenfootImage(imagenNormal);
        imagenTransparente.setTransparency(70);
    }

    private void mover()
    {
        int dx = 0;
        int dy = 0;

        if (Greenfoot.isKeyDown("w")
            || Greenfoot.isKeyDown("up"))
        {
            dy--;
        }

        if (Greenfoot.isKeyDown("s")
            || Greenfoot.isKeyDown("down"))
        {
            dy++;
        }

        if (Greenfoot.isKeyDown("a")
            || Greenfoot.isKeyDown("left"))
        {
            dx--;
        }

        if (Greenfoot.isKeyDown("d")
            || Greenfoot.isKeyDown("right"))
        {
            dx++;
        }

        // Evita una velocidad mayor al moverse en diagonal.
        int paso = (dx != 0 && dy != 0) ? 3 : 4;

        int nuevoX = Math.max(
            25,
            Math.min(getWorld().getWidth() - 45,
                     getX() + dx * paso)
        );

        int nuevoY = Math.max(
            55,
            Math.min(getWorld().getHeight() - 45,
                     getY() + dy * paso)
        );

        setLocation(nuevoX, nuevoY);
    }

    private void cambiarArma()
    {
        if (Greenfoot.isKeyDown("1"))
        {
            estrategia = disparoSimple;
        }

        if (Greenfoot.isKeyDown("2"))
        {
            estrategia = disparoTriple;
        }
    }

    private void disparar()
    {
        if (recarga > 0)
        {
            recarga--;
        }

        if (Greenfoot.isKeyDown("space") && recarga == 0)
        {
            estrategia.disparar(this, pool);

            // El disparo triple tiene una recarga mayor.
            recarga = (estrategia == disparoTriple) ? 18 : 10;
        }
    }

    private void revisarColision()
    {
        if (invulnerabilidad > 0)
        {
            return;
        }

        Enemigo enemigo = (Enemigo) getOneIntersectingObject(
            Enemigo.class
        );

        if (enemigo != null)
        {
            Espacio mundo = (Espacio) getWorld();

            // Chocar no entrega puntos.
            mundo.removeObject(enemigo);

            invulnerabilidad = 90;
            mundo.perderVida();
        }
    }

    private void actualizarInvulnerabilidad()
    {
        if (invulnerabilidad > 0)
        {
            invulnerabilidad--;

            if ((invulnerabilidad / 6) % 2 == 0)
            {
                setImage(imagenTransparente);
            }
            else
            {
                setImage(imagenNormal);
            }
        }
        else
        {
            setImage(imagenNormal);
        }
    }
}