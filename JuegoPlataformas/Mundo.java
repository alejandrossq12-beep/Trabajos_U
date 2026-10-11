import greenfoot.*;

public class Mundo extends World
{
    public Mundo()
    {
        super(800, 500, 1);
        
        GreenfootImage fondo = new GreenfootImage("fondo.png"); 
        fondo.scale(800, 500); 
        setBackground(fondo);
        
        prepararMundo();
    }

    private void prepararMundo()
    {
        // 1. Crear y fijar el Marcador en la esquina superior izquierda
        Marcador marcador = new Marcador();
        addObject(marcador, 130, 30); 
        
        // 2. Crear al Jugador pasándole la referencia del marcador
        Jugador jugador = new Jugador(marcador);
        addObject(jugador, 100, 400); 
        
        // 3. Generar el suelo con agujeros y monedas aleatorias
        for(int i = 0; i < 100; i++) 
        {
            if (i != 10 && i != 11 && i != 20 && i != 21 && i != 22) 
            {
                Plataforma suelo = new Plataforma();
                addObject(suelo, i * 50, 480); 
                
                // 15% de probabilidad de que aparezca una moneda en el piso
                if (Greenfoot.getRandomNumber(100) < 15) 
                {
                    addObject(new Moneda(), i * 50, 440);
                }
            }
        }
        
        // 4. Plataformas aéreas con monedas aseguradas encima
        Plataforma flotante1 = new Plataforma();
        addObject(flotante1, 300, 350);
        addObject(new Moneda(), 300, 310);
        
        Plataforma flotante2 = new Plataforma();
        addObject(flotante2, 600, 280);
        addObject(new Moneda(), 600, 240);
        
        Plataforma flotante3 = new Plataforma();
        addObject(flotante3, 900, 200); 
        addObject(new Moneda(), 900, 160);
    }
    
    public void desplazarEscenario(int desplazamiento)
    {
        // Mueve las plataformas
        for (Plataforma p : getObjects(Plataforma.class)) 
        {
            p.setLocation(p.getX() + desplazamiento, p.getY());
        }
        
        // Mueve las monedas junto con el mapa
        for (Moneda m : getObjects(Moneda.class)) 
        {
            m.setLocation(m.getX() + desplazamiento, m.getY());
        }
    }
}