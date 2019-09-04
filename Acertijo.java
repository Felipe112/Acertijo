/*
 * Un granjero va al mercado y compra un lobo, una oveja y una col.
* Para volver a su casa tiene que cruzar un rio, el granjero dispone de una barca para cruzar a la otra orilla, pero en la barca solo caben Ã©l y una de sud cosas.
* si el lobo se queda solo con la oveja, se la come, si la oveja se queda sola con la col, se la come.
* El reto del granjero es cruzar el rio con todas sus compras. Â¿CÃ³mo puede hacerlo?
 */
package acertijo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andres y Wendy
 */
public class Acertijo {

    public static void main(String[] args) {
        System.out.println("** Granjero, Lobo, Oveja y Col **");
        Intento_01();
    }

    private static void Intento_01() {
        Pasajero granjero = new Pasajero(TipoPasajero.GRANJERO);
        Pasajero lobo = new Pasajero(TipoPasajero.LOBO);
        Pasajero oveja = new Pasajero(TipoPasajero.OVEJA);
        Pasajero col = new Pasajero(TipoPasajero.COL);

        Rio rio = new Rio();
        rio.getNorte().agregar(granjero);
        rio.getNorte().agregar(lobo);
        rio.getNorte().agregar(oveja);
        rio.getNorte().agregar(col);

        System.out.println("NORTE: " + rio.toString());

        Boolean exito = false;

        exito = rio.Viajar(granjero, oveja, Direccion.SUR);
        if (!exito) {
            System.out.println(rio.toString());
        }

        exito = rio.Viajar(granjero, Direccion.NORTE);
        if (!exito) {
            System.out.println(rio.toString());
        }

        exito = rio.Viajar(granjero, lobo, Direccion.SUR);
        if (!exito) {
            System.out.println(rio.toString());
        }
        exito = rio.Viajar(granjero, oveja, Direccion.NORTE);
        if (!exito) {
            System.out.println(rio.toString());
        }
        exito = rio.Viajar(granjero, col, Direccion.SUR);
        if (!exito) {
            System.out.println(rio.toString());
        }
        exito = rio.Viajar(granjero, Direccion.NORTE);
        if (!exito) {
            System.out.println(rio.toString());
        }
        exito = rio.Viajar(granjero, oveja, Direccion.SUR);
        if (!exito) {
            System.out.println(rio.toString());
        }
    }
}

class Rio {

    private Orilla norte;
    private Orilla sur;

    public Rio() {
        norte = new Orilla(Direccion.NORTE);
        sur = new Orilla(Direccion.SUR);
    }

    public Orilla getNorte() {
        return norte;
    }

    public Orilla getSur() {
        return sur;
    }

    @Override
    public String toString() {

        return "\nRio {"
                + "\nnorte=" + norte
                + "\n, sur=" + sur
                + "\n}";
    }

    public Boolean Viajar(Pasajero primer, Pasajero segundo, Direccion destino) {
        if (destino.equals(Direccion.NORTE)) {
            sur.quitar(primer);
            sur.quitar(segundo);
            norte.agregar(primer);
            norte.agregar(segundo);
        } else {
            norte.quitar(primer);
            norte.quitar(segundo);
            sur.agregar(primer);
            sur.agregar(segundo);
        }

        boolean conflicto = HayConflicto(this.norte) || HayConflicto(this.sur);
        return !conflicto;
    }

    public Boolean Viajar(Pasajero primer, Direccion destino) {
        if (destino.equals(Direccion.NORTE)) {
            sur.quitar(primer);
            norte.agregar(primer);
        } else {
            norte.quitar(primer);
            sur.agregar(primer);
        }

        boolean conflicto = HayConflicto(this.norte) || HayConflicto(this.sur);
        return !conflicto;
    }

    public Boolean HayConflicto(Orilla costa) {
        Boolean conflicto = true;

        if (costa.getPasajeros().stream()
                .anyMatch(p -> p.getTipo().equals(TipoPasajero.GRANJERO))) {
            return false;
        }

        for (Pasajero pas : costa.getPasajeros()) {
            conflicto = costa.getPasajeros().stream()
                    .anyMatch(p -> p.getTipo().seCome(pas.getTipo()));
        }

        return conflicto;
    }
}

class Orilla {

    private List<Pasajero> pasajeros;
    private Direccion ubicacion;

    public Orilla(Direccion ubicacion) {
        this.pasajeros = new ArrayList<Pasajero>();
        this.ubicacion = ubicacion;
    }

    public void agregar(Pasajero alguien) {
        if (getPasajeros().stream().anyMatch(p -> p.equals(alguien))) {
            System.out.println(alguien.toString() + " Ya estoy en la orilla " + this.ubicacion);
        } else {
            this.pasajeros.add(alguien);
        }
    }

    public void quitar(Pasajero alguien) {
        if (getPasajeros().stream().anyMatch(p -> p.equals(alguien))) {
            this.pasajeros.remove(alguien);
        } else {
            System.out.println(alguien.toString() + " NO estoy en la orilla " + this.ubicacion);
        }
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    @Override
    public String toString() {
        return "{" + pasajeros + '}';
    }
}

class Pasajero {

    private TipoPasajero tipo;

    public Pasajero(TipoPasajero tipo) {
        this.tipo = tipo;
    }

    public TipoPasajero getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "\n\t" + tipo;
    }
}

/**
 * *
 * CONSTANTES
 */
enum TipoPasajero {
    GRANJERO,
    LOBO,
    OVEJA,
    COL;

    public boolean seCome(TipoPasajero otro) {
        if (this.equals(OVEJA) && otro.equals(LOBO)) {
            return true;
        } else if (this.equals(OVEJA) && otro.equals(COL)) {
            return true;
        }
        return false;
    }
}

enum Direccion {
    NORTE, SUR
}
