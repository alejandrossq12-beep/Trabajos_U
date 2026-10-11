import greenfoot.*;

public class Marcador extends Actor
{
    private int puntos = 0;
    private int tiempo = 100; // Empiezas con 100 segundos
    private int frames = 0;

    public Marcador()
    {
        actualizarPantalla();
    }

    public void act()
    {
        frames++;
        // Greenfoot corre a unos 60 frames por segundo
        if (frames >= 60) 
        {
            tiempo--;
            frames = 0;
            actualizarPantalla();
            
            // Si el tiempo llega a cero, el nivel se reinicia (mueres)
            if (tiempo <= 0) 
            {
                Greenfoot.setWorld(new Mundo());
            }
        }
    }

    public void sumarPunto()
    {
        puntos += 10; // Cada moneda suma 10 puntos
        actualizarPantalla();
    }

    private void actualizarPantalla()
    {
        GreenfootImage fondoTexto = new GreenfootImage("Tiempo: " + tiempo + " | Puntos: " + puntos, 24, Color.WHITE, new Color(0, 0, 0, 150));
        setImage(fondoTexto);
    }
}