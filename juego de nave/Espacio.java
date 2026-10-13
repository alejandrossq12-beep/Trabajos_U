import greenfoot.*;

public class Espacio extends World
{
    private PoolDeBalas pool;

    private int contadorAparicion = 0;
    private int ciclos = 0;
    private int nivel = 1;
    private int vidas = 3;

    private boolean terminado = false;

    public Espacio()
    {
        super(800, 500, 1);

        crearFondo();

        GameManager.getInstancia().reiniciar();

        pool = new PoolDeBalas(40);

        addObject(new nave(pool), 70, getHeight() / 2);
        addObject(new Marcador(), 110, 25);

        setPaintOrder(Marcador.class, nave.class, Bala.class,
                      Enemigo.class);

        actualizarHUD();

        showText(
            "Mover: WASD / Flechas | Disparar: ESPACIO | Armas: 1 y 2",
            getWidth() / 2,
            getHeight() - 18
        );
    }

    public void act()
    {
        if (terminado)
        {
            if (Greenfoot.isKeyDown("r"))
            {
                Greenfoot.setWorld(new Espacio());
            }

            return;
        }

        ciclos++;

        // La dificultad aumenta cada 900 ciclos.
        if (ciclos % 900 == 0)
        {
            nivel++;
            actualizarHUD();
        }

        contadorAparicion++;

        int intervalo = Math.max(20, 60 - (nivel - 1) * 5);

        if (contadorAparicion >= intervalo)
        {
            contadorAparicion = 0;

            // Presupuesto máximo de enemigos activos.
            if (getObjects(Enemigo.class).size() < 25)
            {
                crearEnemigo();
            }
        }
    }

    private void crearEnemigo()
    {
        int y = 60 + Greenfoot.getRandomNumber(
            getHeight() - 110
        );

        addObject(
            new Enemigo(),
            getWidth() - 25,
            y
        );
    }

    public void perderVida()
    {
        if (terminado)
        {
            return;
        }

        vidas--;
        actualizarHUD();

        if (vidas <= 0)
        {
            terminarPartida();
        }
    }

    private void actualizarHUD()
    {
        showText(
            "Vidas: " + vidas + " | Nivel: " + nivel,
            getWidth() - 140,
            25
        );
    }

    private void terminarPartida()
    {
        terminado = true;

        // Retira las balas, devolviéndolas al pool.
        for (Bala bala : getObjects(Bala.class))
        {
            bala.desactivar();
        }

        removeObjects(getObjects(Enemigo.class));

        showText(
            "GAME OVER",
            getWidth() / 2,
            getHeight() / 2 - 35
        );

        showText(
            "Puntos: " + GameManager.getInstancia().getPuntos(),
            getWidth() / 2,
            getHeight() / 2
        );

        showText(
            "Presiona R para reiniciar",
            getWidth() / 2,
            getHeight() / 2 + 35
        );
    }

    public boolean estaTerminado()
    {
        return terminado;
    }

    private void crearFondo()
    {
        GreenfootImage fondo = new GreenfootImage(
            getWidth(),
            getHeight()
        );

        fondo.setColor(new Color(8, 12, 30));
        fondo.fill();

        // Las estrellas se dibujan una sola vez.
        for (int i = 0; i < 110; i++)
        {
            int x = Greenfoot.getRandomNumber(getWidth());
            int y = Greenfoot.getRandomNumber(getHeight());
            int tamanio = 1 + Greenfoot.getRandomNumber(3);

            int brillo = 130 + Greenfoot.getRandomNumber(126);

            fondo.setColor(new Color(brillo, brillo, brillo));
            fondo.fillOval(x, y, tamanio, tamanio);
        }

        setBackground(fondo);
    }
}