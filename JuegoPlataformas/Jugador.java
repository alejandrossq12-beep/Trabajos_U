import greenfoot.*;

public class Jugador extends Actor
{
    private enum Estado { QUIETO, CAMINANDO_DERECHA, CAMINANDO_IZQUIERDA, SALTANDO, CAYENDO }

    private Estado estado = Estado.QUIETO;
    private GreenfootImage imagenQuieto, imagenSalto, imagenCaida; 
    private GreenfootImage[] caminar;
    private int frameActual = 0, contadorAnimacion = 0, tiempoSalto = 0, yInicial;
    private boolean saltando = false;
    private double velocidadInicial = 12.0, gravedad = 1.0;
    
    // Variable para guardar el HUD y enviarle puntos
    private Marcador marcador; 

    // Ahora el Jugador pide el marcador al momento de crearse
    public Jugador(Marcador marcador)
    {
        this.marcador = marcador;
        cargarImagenes();
    }

    private void cargarImagenes()
    {
        imagenQuieto = new GreenfootImage("idle.png");
        imagenSalto = new GreenfootImage("jump.png"); 
        imagenCaida = new GreenfootImage("fall.png"); 
        
        caminar = new GreenfootImage[4];
        caminar[0] = new GreenfootImage("walk1.png");
        caminar[1] = new GreenfootImage("walk2.png");
        caminar[2] = new GreenfootImage("walk3.png");
        caminar[3] = new GreenfootImage("walk4.png");
        
        setImage(imagenQuieto);
    }

    public void act()
    {
        controlarMovimientoHorizontal();
        controlarSalto();
        actualizarSalto();
        actualizarAnimacion();
        comprobarMuertePorCaida();
        recolectarMonedas(); // Activamos el detector de monedas
    }

    private void recolectarMonedas()
    {
        Actor moneda = getOneIntersectingObject(Moneda.class);
        if (moneda != null)
        {
            // Borra la moneda de la pantalla y suma puntos al marcador
            getWorld().removeObject(moneda);
            marcador.sumarPunto();
        }
    }

    private void comprobarMuertePorCaida()
    {
        if (getY() > 490) 
        {
            Greenfoot.setWorld(new Mundo());
        }
    }

    private void controlarMovimientoHorizontal()
    {
        int velocidadCaminar = 4;
        
        if (Greenfoot.isKeyDown("right"))
        {
            if (getX() < 400) setLocation(getX() + velocidadCaminar, getY());
            else ((Mundo)getWorld()).desplazarEscenario(-velocidadCaminar);
            
            if (!saltando) estado = Estado.CAMINANDO_DERECHA;
        }
        else if (Greenfoot.isKeyDown("left"))
        {
            if (getX() > 200) setLocation(getX() - velocidadCaminar, getY());
            else ((Mundo)getWorld()).desplazarEscenario(velocidadCaminar);
            
            if (!saltando) estado = Estado.CAMINANDO_IZQUIERDA;
        }
        else
        {
            if (!saltando) estado = Estado.QUIETO;
        }
    }

    private void controlarSalto()
    {
        if (Greenfoot.isKeyDown("space") && !saltando)
        {
            saltando = true;
            tiempoSalto = 0;
            yInicial = getY();
            estado = Estado.SALTANDO;
        }
    }

    private void actualizarSalto()
    {
        if (saltando)
        {
            tiempoSalto++;
            double y = yInicial - velocidadInicial * tiempoSalto + 0.5 * gravedad * tiempoSalto * tiempoSalto;
            
            if (tiempoSalto < 12) estado = Estado.SALTANDO;
            else estado = Estado.CAYENDO;
            
            setLocation(getX(), (int)y);
            
            if (estado == Estado.CAYENDO)
            {
                Actor plataforma = getOneIntersectingObject(Plataforma.class);
                if (plataforma != null)
                {
                    saltando = false;
                    estado = Estado.QUIETO;
                    int nuevaY = plataforma.getY() - (plataforma.getImage().getHeight() / 2) - (getImage().getHeight() / 2) + 1;
                    setLocation(getX(), nuevaY);
                }
            }
        }
        else
        {
            Actor sueloAbajo = getOneObjectAtOffset(0, getImage().getHeight() / 2 + 5, Plataforma.class);
            if (sueloAbajo == null) 
            {
                saltando = true;
                tiempoSalto = 12; 
                yInicial = getY() + (int)(velocidadInicial * tiempoSalto) - (int)(0.5 * gravedad * tiempoSalto * tiempoSalto);
            }
        }
    }

    private void actualizarAnimacion()
    {
        if (estado == Estado.QUIETO) setImage(imagenQuieto);
        else if (estado == Estado.CAMINANDO_DERECHA) animarCaminata(false);
        else if (estado == Estado.CAMINANDO_IZQUIERDA) animarCaminata(true);
        else if (estado == Estado.SALTANDO) setImage(imagenSalto);
        else if (estado == Estado.CAYENDO) setImage(imagenCaida);
    }

    private void animarCaminata(boolean izquierda)
    {
        contadorAnimacion++;
        if (contadorAnimacion >= 6)
        {
            frameActual++;
            if (frameActual >= caminar.length) frameActual = 0;
            GreenfootImage imagen = new GreenfootImage(caminar[frameActual]);
            if (izquierda) imagen.mirrorHorizontally();
            setImage(imagen);
            contadorAnimacion = 0;
        }
    }
}