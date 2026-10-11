import greenfoot.*;

public class EnemigoPesado extends SoldadoEnemigo
{
    @Override
    protected void morir()
    {
        World mundo = getWorld();
        if (mundo != null && mundo instanceof JuegoWorld) {
            ((JuegoWorld)mundo).sumarPuntaje(40); // Puntos extra por ser pesado
        }
        super.morir();
    }
    public EnemigoPesado(Jugador jugador)
    {
        super(jugador, 100, 1, 12);
        setImage(FabricaImagenes.soldadoEnemigoPesado());
            
    }
}