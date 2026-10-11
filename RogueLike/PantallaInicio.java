import greenfoot.*;

public class PantallaInicio extends World
{
    public PantallaInicio()
    {
        super(900, 600, 1);
        GreenfootImage fondo = new GreenfootImage(getWidth(), getHeight());
        fondo.setColor(Color.BLACK);
        fondo.fill();
        setBackground(fondo);
        
        showText("ROGUE-LIKE SUPERVIVENCIA", getWidth()/2, 200);
        showText("Usa W, A, S, D para moverte y ESPACIO para disparar", getWidth()/2, 300);
        showText("Presiona la tecla ENTER para comenzar", getWidth()/2, 400);
    }
    
    public void act()
    {
        if (Greenfoot.isKeyDown("enter"))
        {
            Greenfoot.setWorld(new JuegoWorld());
        }
    }
}