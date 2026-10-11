import greenfoot.*;

public class Jugador extends Soldado
{
    private int cooldownDisparo = 0;
    private int dirX = 1;
    private int dirY = 0;
    private int tiempoEnergia = 0;
    private int tiempoInvulnerable = 0; // Nuevo

    public Jugador()
    {
        super(100, 4);
        setImage(FabricaImagenes.soldadoJugador());
    }

    public void act()
    {
        disparar();
        mover();
        revisarItems();
        
        if (cooldownDisparo > 0) cooldownDisparo--;
        
        if (tiempoEnergia > 0) {
            tiempoEnergia--;
            if (tiempoEnergia == 0) velocidad = 4;
        }
        
        // Lógica de parpadeo por invulnerabilidad
        if (tiempoInvulnerable > 0) {
            tiempoInvulnerable--;
            if (tiempoInvulnerable % 5 == 0) {
                getImage().setTransparency(getImage().getTransparency() == 255 ? 100 : 255);
            }
            if (tiempoInvulnerable == 0) getImage().setTransparency(255);
        }
    }
    
    // Sobrescribimos el daño para usar los I-frames
    @Override
    public void recibirDanio(int cantidad)
    {
        if (tiempoInvulnerable > 0) return; // Si es invulnerable, no recibe daño
        
        super.recibirDanio(cantidad);
        tiempoInvulnerable = 40; // Medio segundo de invulnerabilidad
    }

    private void mover()
    {
        int dx = 0;
        int dy = 0;

        if (Greenfoot.isKeyDown("w")) { dy = -velocidad; dirX = 0; dirY = -1; }
        if (Greenfoot.isKeyDown("s")) { dy = velocidad; dirX = 0; dirY = 1; }
        if (Greenfoot.isKeyDown("a")) { dx = -velocidad; dirX = -1; dirY = 0; }
        if (Greenfoot.isKeyDown("d")) { dx = velocidad; dirX = 1; dirY = 0; }

        int nuevoX = Math.max(15, Math.min(getWorld().getWidth()-15, getX()+dx));
        int nuevoY = Math.max(15, Math.min(getWorld().getHeight()-15, getY()+dy));
        setLocation(nuevoX, nuevoY);
    }

    private void disparar()
    {
        if (Greenfoot.isKeyDown("space") && cooldownDisparo == 0)
        {
            int cantidadBalas = getWorld().getObjects(Bala.class).size();
            if (cantidadBalas < 60)
            {
                if (tiempoEnergia > 0) 
                {
                    getWorld().addObject(new Bala(dirX, dirY), getX(), getY());
                    if (dirX != 0) {
                        getWorld().addObject(new Bala(dirX, dirY), getX(), getY() - 15);
                        getWorld().addObject(new Bala(dirX, dirY), getX(), getY() + 15);
                    } else {
                        getWorld().addObject(new Bala(dirX, dirY), getX() - 15, getY());
                        getWorld().addObject(new Bala(dirX, dirY), getX() + 15, getY());
                    }
                    cooldownDisparo = 4; 
                } 
                else 
                {
                    getWorld().addObject(new Bala(dirX, dirY), getX(), getY());
                    cooldownDisparo = 8;
                }
            }
        }
    }
    
    private void revisarItems()
    {
        Botiquin botiquin = (Botiquin) getOneIntersectingObject(Botiquin.class);
        if (botiquin != null)
        {
            vida += 30;
            if (vida > 100) vida = 100;
            getWorld().removeObject(botiquin);
            if (getWorld() instanceof JuegoWorld) ((JuegoWorld)getWorld()).sumarPuntaje(50); // Puntos por curarse
        }
        
        BebidaEnergia bebida = (BebidaEnergia) getOneIntersectingObject(BebidaEnergia.class);
        if (bebida != null)
        {
            tiempoEnergia = 400;
            velocidad = 6; 
            getWorld().removeObject(bebida);
            if (getWorld() instanceof JuegoWorld) ((JuegoWorld)getWorld()).sumarPuntaje(100);
        }
    }

    protected void morir()
    {
        World mundo = getWorld();
        if (mundo instanceof JuegoWorld)
        {
            ((JuegoWorld) mundo).gameOver();
        }
        if (mundo != null) mundo.removeObject(this);
    }
}