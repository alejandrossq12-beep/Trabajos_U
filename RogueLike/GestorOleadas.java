import greenfoot.*;

public class GestorOleadas
{
    private JuegoWorld mundo;
    private Jugador jugador;
    private int oleada = 1;
    private int generados = 0;
    private int objetivoOleada = 0;
    private int contadorSpawn = 0;
    private int esperaEntreOleadas = 0;
    private int varianteOleada = 0;

    public GestorOleadas(JuegoWorld mundo, Jugador jugador)
    {
        this.mundo = mundo;
        this.jugador = jugador;
        prepararOleada();
    }

    public void actualizar()
    {
        if (jugador.getWorld() == null) return;
        
        int activos = mundo.getObjects(SoldadoEnemigo.class).size();
        
        if (generados < objetivoOleada)
        {
            contadorSpawn--;
            if (contadorSpawn <= 0 && activos < maxSimultaneos())
            {
                crearEnemigo();
                generados++;
                contadorSpawn = intervaloSpawn();
            }
        }
        else if (activos == 0)
        {
            if (esperaEntreOleadas == 0) esperaEntreOleadas = 100;
            esperaEntreOleadas--;
            if (esperaEntreOleadas <= 0)
            {
                oleada++;
                prepararOleada();
            }
        }
    }

    private void prepararOleada()
    {
        generados = 0;
        esperaEntreOleadas = 0;
        contadorSpawn = 20;
        
        // Cada 5 oleadas es ronda de JEFE
        if (oleada % 5 == 0) {
            objetivoOleada = 1; // Solo aparece 1 enemigo (el Jefe)
            return;
        }
        
        varianteOleada = Greenfoot.getRandomNumber(3);
        
        if (varianteOleada == 0) objetivoOleada = Math.min(8 + oleada * 4, 80);
        else if (varianteOleada == 1) objetivoOleada = Math.min(3 + oleada * 2, 40);
        else objetivoOleada = Math.min(5 + oleada * 3, 60);
    }

    private int maxSimultaneos()
    {
        return Math.min(6 + oleada * 2, 35);
    }

    private int intervaloSpawn()
    {
        int base = Math.max(12, 50 - oleada * 3);
        if (varianteOleada == 2) return base / 2;
        return base;
    }

    private int vidaEnemigo() { return 20 + oleada * 4; }
    private int velocidadEnemigo() { return Math.min(1 + oleada / 4, 4); }
    private int danioEnemigo() { return Math.min(4 + oleada, 15); }

    private void crearEnemigo()
    {
        SoldadoEnemigo enemigo;
        
        if (oleada % 5 == 0) {
            enemigo = new Jefe(jugador, oleada); // Aparece el jefe
        }
        else if (varianteOleada == 1 || Greenfoot.getRandomNumber(100) < 15) {
            enemigo = new EnemigoPesado(jugador);
        } else {
            enemigo = new SoldadoEnemigo(jugador, vidaEnemigo(), velocidadEnemigo(), danioEnemigo());
        }
        
        int lado = Greenfoot.getRandomNumber(4);
        int x = (lado == 0 || lado == 1) ? (lado == 0 ? 10 : mundo.getWidth() - 10) : Greenfoot.getRandomNumber(mundo.getWidth());
        int y = (lado == 2 || lado == 3) ? (lado == 2 ? 10 : mundo.getHeight() - 10) : Greenfoot.getRandomNumber(mundo.getHeight());
        
        mundo.addObject(enemigo, x, y);
    }

    public int getOleada() { return oleada; }
}
 