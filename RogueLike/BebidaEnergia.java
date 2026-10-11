import greenfoot.*;

public class BebidaEnergia extends Actor
{
    private int tiempoVida = 300;

    public BebidaEnergia()
    {
        GreenfootImage img = new GreenfootImage(14, 24);
        img.setColor(Color.BLACK);
        img.fillRect(0, 0, 14, 24); // Fondo lata
        img.setColor(Color.GREEN);
        img.fillRect(2, 4, 10, 16); // Detalle verde estilo Monster Original
        setImage(img);
    }

    public void act()
    {
        tiempoVida--;
        if (tiempoVida < 50 && tiempoVida % 10 == 0) {
            getImage().setTransparency(getImage().getTransparency() == 255 ? 50 : 255);
        }
        if (tiempoVida <= 0) getWorld().removeObject(this);
    }
}
