import greenfoot.*;

public class JuegoWorld extends World
{
    private Jugador jugador;
    private GestorOleadas gestor;
    private HUD hud;
    private int puntaje = 0; // Nuevo: Sistema de puntos

    public JuegoWorld()
    {
        super(900, 600, 1);
        jugador = new Jugador();
        addObject(jugador, getWidth()/2, getHeight()/2);
        gestor = new GestorOleadas(this, jugador);
        hud = new HUD(this, jugador, gestor);
        addObject(hud, 220, 25);
    }

    public void act()
    {
        gestor.actualizar();
    }

    // Métodos para manejar los puntos
    public void sumarPuntaje(int puntos) {
        puntaje += puntos;
    }
    
    public int getPuntaje() {
        return puntaje;
    }

    public void gameOver()
    {
        GreenfootImage fondo = getBackground();
        fondo.setColor(new Color(0, 0, 0, 180)); 
        fondo.fillRect(0, 0, getWidth(), getHeight());
        
        showText("HAS MUERTO", getWidth()/2, getHeight()/2 - 40);
        showText("Sobreviviste hasta la oleada: " + gestor.getOleada(), getWidth()/2, getHeight()/2);
        showText("PUNTAJE FINAL: " + puntaje, getWidth()/2, getHeight()/2 + 40);
        
        Greenfoot.stop();
    }
}