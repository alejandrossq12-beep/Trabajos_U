import greenfoot.*;

public class Jefe extends SoldadoEnemigo
{
    public Jefe(Jugador jugador, int oleada)
    {
        // Muchísima vida, lento, pero hace mucho daño
        super(jugador, 300 + (oleada * 50), 1, 30);
        setImage(FabricaImagenes.soldadoJefe());
    }
    
    @Override
    protected void morir()
    {
        World mundo = getWorld();
        if (mundo != null) {
            if (mundo instanceof JuegoWorld) ((JuegoWorld)mundo).sumarPuntaje(500); // Gran premio
            
            // Garantiza drops al morir
            mundo.addObject(new Botiquin(), getX() - 30, getY());
            mundo.addObject(new BebidaEnergia(), getX() + 30, getY());
        }
        super.morir();
    }
}