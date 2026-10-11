import greenfoot.*;

public class SoldadoEnemigo extends Soldado
{
    private Jugador objetivo;
    private int danio;
    private int cooldownAtaque = 0;

    public SoldadoEnemigo(Jugador objetivo, int vida, int velocidad, int danio)
    {
        super(vida, velocidad);
        this.objetivo = objetivo;
        this.danio = danio;
        setImage(FabricaImagenes.soldadoEnemigo());
    }

    public void act()
    {
        if (objetivo == null || objetivo.getWorld() == null) return;
        perseguir();
        if (cooldownAtaque > 0) cooldownAtaque--;
        atacar();
    }

    private void perseguir()
    {
        int dx = objetivo.getX() - getX();
        int dy = objetivo.getY() - getY();
        double distancia = Math.sqrt(dx*dx + dy*dy);
        
        if (distancia > 0)
        {
            int movX = (int) Math.round(velocidad * dx / distancia);
            int movY = (int) Math.round(velocidad * dy / distancia);
            setLocation(getX() + movX, getY() + movY);
        }
    }

    private void atacar()
    {
        if (isTouching(Jugador.class) && cooldownAtaque == 0)
        {
            objetivo.recibirDanio(danio);
            cooldownAtaque = 30;
        }
    }
    
 @Override
    protected void morir()
    {
        World mundo = getWorld();
        if (mundo != null)
        {
            if (mundo instanceof JuegoWorld) ((JuegoWorld)mundo).sumarPuntaje(10); // 10 Puntos
            
            GreenfootImage fondo = mundo.getBackground();
            fondo.setColor(new Color(139, 0, 0, 120)); 
            fondo.fillOval(getX() - 15 + Greenfoot.getRandomNumber(10), getY() - 15 + Greenfoot.getRandomNumber(10), 30, 30);

            int probabilidadDrop = Greenfoot.getRandomNumber(100);
            if (probabilidadDrop < 10) mundo.addObject(new Botiquin(), getX(), getY());
            else if (probabilidadDrop < 15) mundo.addObject(new BebidaEnergia(), getX(), getY()); 
        }
        super.morir();
    }
}