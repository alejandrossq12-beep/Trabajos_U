import greenfoot.*;

public class Enemigo extends Actor
{
    private Estado estado = new Avanzar();

    private static final GreenfootImage IMAGEN =
        crearImagen();

    public Enemigo()
    {
        setImage(IMAGEN);
    }

    private static GreenfootImage crearImagen()
    {
        GreenfootImage img = new GreenfootImage(30, 30);

        img.setColor(new Color(220, 50, 70));
        img.fillOval(1, 1, 28, 28);

        img.setColor(new Color(120, 15, 35));
        img.fillOval(6, 6, 18, 18);

        img.setColor(Color.ORANGE);
        img.fillOval(10, 10, 10, 10);

        return img;
    }

    public void act()
    {
        Espacio mundo = (Espacio) getWorld();

        if (mundo == null || mundo.estaTerminado())
        {
            return;
        }

        estado = estado.actuar(this);

        if (getX() <= 12)
        {
            mundo.removeObject(this);
            mundo.perderVida();
        }
    }

    public void destruir()
    {
        Espacio mundo = (Espacio) getWorld();

        // Evita registrar una eliminación dos veces.
        if (mundo == null || mundo.estaTerminado())
        {
            return;
        }

        mundo.removeObject(this);

        GameManager.getInstancia().sumarPuntos(10);
    }
}