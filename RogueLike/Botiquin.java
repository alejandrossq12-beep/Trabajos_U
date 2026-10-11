import greenfoot.*;

public class Botiquin extends Actor
{
    private int tiempoVida = 250; 

    public Botiquin()
    {
        GreenfootImage img = new GreenfootImage(24, 24);
        img.setColor(Color.WHITE);
        img.fillRect(0, 0, 24, 24);
        
        img.setColor(Color.RED);
        img.fillRect(10, 4, 4, 16); 
        img.fillRect(4, 10, 16, 4); 
        
        setImage(img);
    }

    public void act()
    {
        tiempoVida--;
        
        if (tiempoVida < 50 && tiempoVida % 10 == 0) {
            getImage().setTransparency(getImage().getTransparency() == 255 ? 50 : 255);
        }
        
        if (tiempoVida <= 0)
        {
            getWorld().removeObject(this);
        }
    }
}